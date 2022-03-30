package site.mato.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Hospital {
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id; //auto_increment
    
	private String addr; //병원주소 
	private Integer mgtStaDd; //운영시작일자 
	private String pcrPsblYn; //PCR 검사 여부 
	private String ratPsblYn; //호흡기 전담클리닉 여부 
	private Integer recuClCd;//요양종별코드 (종합병원 11 병원 21 의원 31)
	private Integer rnum; 
	private String rprtWorpClicFndtTgtYn;
	private String sgguCdNm; //시군구 
	private String sidoCdNm; //시도명 
	private String telno; //전화번호 
	private String xPosWgs84; //위도 
	private String yPosWgs84; //경도 
	private String yadmNm; //요양기관명 
	private String ykihoEnc; //암호화된 요양기호 
}
