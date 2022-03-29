package site.mato.batch;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
public class HospDownloadBatchTest {

	@Test
	public void start(){
		try {
			system.out.println("안녕!");
			//1. 공공데이터 다운로드 
			RestTemplate rt = new RestTemplate();
			//테스트 할때는 %3D 사용하지말기 (URL 인코딩 안해도됨)
			String url = "https://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=Mt7MrViBQCjLGQzZy9ikD0C%2FC1w1fKW7vbNbDCWLjLPJIwyeZbKx%2FXZox47%2FQNxKUXtWiZvbHRWLARrpCA3QJQ==&pageNo=1&numOfRows=10&_type=json";
			ResponseDto dto = rt.getForObject(url, ResponseDto.class);
			System.out.println(dto);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
