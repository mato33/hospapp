package site.mato.batch;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//1.임포트 하기 (alt + shift + o) 인텔리J 키맵사용
//2.private -> private로 변경하기 
//3.Lombok 사용하기 

@Data
class Body {
	private Items items;
	private Integer numOfRows;
	private Integer pageNo;
	private Integer totalCount;
}

@Data
class Header {
	private String resultCode;
	private String resultMsg;
}

@Data
class Item {
	private String addr;
	private Integer mgtStaDd;
	private String pcrPsblYn;
	private String ratPsblYn;
	private Integer recuClCd;
	private Integer rnum;
	private String rprtWorpClicFndtTgtYn;
	private String sgguCdNm;
	private String sidoCdNm;
	private String telno;
	private String xPosWgs84;
	private String yPosWgs84;
	private String yadmNm;
	private String ykihoEnc;
}

@Data
class Items {
	private List<Item> item = null;
}

@Data
class Response {
	private Header header;
	private Body body;
}

@Getter
@Setter
public class ResponseDto {
	private Response response;
}
