package site.mato.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import site.mato.domain.Hospital;
import site.mato.domain.HospitalRepository;

//1주일에 한번씩 다운로드해서 db에 업데이트
//추가될수 있기때문에 주기적으로
//4개의 병원이 있다면 4개의 DB에 insert환다.
//다음날에는 5개병원이 되었다면 한개 추가하는 로직이 복잡할꺼같아서
//전부지우고 새로입력한다.
//공공데이터를 바로바로 서비스해주는 방식은 하루에 트래픽이 1000이라서 서비스하기 힘들거같다.

@AllArgsConstructor
@Component
public class HospDownloadBatch {
	
	private final HospitalRepository hospitalRepository;
	
	//초 분 시 일 월 주 
	@Scheduled(cron = "0 54 * * * *",zone = "Asia/Seoul")
	public void startBatch() {
		System.out.println("1분 마다 실행됨");
	
		/*//1. 공공데이터 다운로드 
		RestTemplate rt = new RestTemplate();
		//테스트 할때는 %3D 사용하지말기 (URL 인코딩 안해도됨)
		String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=Mt7MrViBQCjLGQzZy9ikD0C/C1w1fKW7vbNbDCWLjLPJIwyeZbKx/XZox47/QNxKUXtWiZvbHRWLARrpCA3QJQ==&pageNo=1&numOfRows=10&_type=json";
		ResponseDto dto = rt.getForObject(url, ResponseDto.class);
		//String dto = rt.getForObject(url, String.class);
		//System.out.println(dto.getResponse().getBody().getItems());
		List<Item> hospitals = dto.getResponse().getBody().getItems().getItem();
		for(Item item:hospitals) {
			System.out.println(item.getYadmNm());
			System.out.println("PCR 여부:" + item.getPcrPsblYn());
		}*/
		
		//1.담을그릇 준비 
		List<Hospital> hospitals = new ArrayList<>();
		//2.api 한번 호출해서 totalcount 확인 
		RestTemplate rt = new RestTemplate();
		
		//1로 하면 item이 컬렉션이 아니라서 파싱이 안됨...
		int totalCount = 2;
		
		String totalCounturl = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=Mt7MrViBQCjLGQzZy9ikD0C/C1w1fKW7vbNbDCWLjLPJIwyeZbKx/XZox47/QNxKUXtWiZvbHRWLARrpCA3QJQ==&pageNo=1&numOfRows="
				+ totalCount + "&_type=json";
		
		ResponseDto totalCountDto = rt.getForObject(totalCounturl, ResponseDto.class);
		totalCount = totalCountDto.getResponse().getBody().getTotalCount();
		
		//3. totalcount 만큼 한번에 가져오기 
		
		String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=Mt7MrViBQCjLGQzZy9ikD0C/C1w1fKW7vbNbDCWLjLPJIwyeZbKx/XZox47/QNxKUXtWiZvbHRWLARrpCA3QJQ==&pageNo=1&numOfRows="
				+ totalCount + "&_type=json";
		ResponseDto responseDto = rt.getForObject(url, ResponseDto.class);
		
		List<Item> items = responseDto.getResponse().getBody().getItems().getItem();
		System.out.println("가져온 데이터 사이즈 : " + items.size());
		
		//컬렉션 복사 
		hospitals = items.stream().map(
				(e) -> {
					return Hospital.builder()
							.addr(e.getAddr())
							.mgtStaDd(e.getMgtStaDd())
							.pcrPsblYn(e.getPcrPsblYn())
							.ratPsblYn(e.getRatPsblYn())
							.recuClCd(e.getRecuClCd())
							.rnum(e.getRnum())
							.rprtWorpClicFndtTgtYn(e.getRprtWorpClicFndtTgtYn())
							.sgguCdNm(e.getSgguCdNm())
							.sidoCdNm(e.getSidoCdNm())
							.telno(e.getTelno())
							.xPosWgs84(e.getXPosWgs84())
							.yPosWgs84(e.getYPosWgs84())
							.yadmNm(e.getYadmNm())
							.ykihoEnc(e.getYkihoEnc())
							.build();
				}
			).collect(Collectors.toList());
		
		//기존 데이터 전부 삭제 
		hospitalRepository.deleteAll();
		
		//배치 진행후 DB에 insert 예정 
		hospitalRepository.saveAll(hospitals);
	}	
}