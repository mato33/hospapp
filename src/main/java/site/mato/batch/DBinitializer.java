package site.mato.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import site.mato.domain.Hospital;
import site.mato.domain.HospitalRepository;

@Configuration
public class DBinitializer {
	
	//스프링부트 시작시에 한번만 실행
	@Bean
	public CommandLineRunner initDB(HospitalRepository hRepository) {
		return (args) -> {
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
			hRepository.deleteAll();
			
			//배치 진행후 DB에 insert 예정 
			hRepository.saveAll(hospitals);
		};
	}
}
