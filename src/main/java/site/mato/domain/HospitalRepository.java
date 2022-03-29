package site.mato.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//순서 주의!!!
//@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Integer>{
	// name query 사용시에 어노테이션 필요
    @Query(value = "SELECT * FROM hospital WHERE SIDO_CD_NM = :sidoCdNm AND SGGU_CD_NM = :sgguCdNm AND pcrPsblYn = 'Y'", nativeQuery = true)
    public List<Hospital> mFindHospital(@Param("sidoCdNm") String sidoCdNm, @Param("sgguCdNm") String sgguCdNm);
	
	// String도 됐었낭? 됨!! 저장하면 안됨..데이터 다시 받아야함 저장은 하지말장 - order by 추가
	@Query(value = "SELECT distinct SIDO_CD_NM FROM hospital order by SIDO_CD_NM", nativeQuery = true)
    public List<String> mFindSidoCdNm();

    // 저장함 ㅠ 다시 배치 돌려야 할듯 // 대문자 안해서 오류났었네
    // 콘솔 색깔좀 나오게 해야 겠당
    // 대소문자좀 통일함. 해깔려서
    @Query(value = "SELECT distinct SGGU_CD_NM FROM HOSPITAL WHERE SIDO_CD_NM = :sidoCdNm order by SGGU_CD_NM", nativeQuery = true)
    public List<String> mFindSggucdnm(@Param("sidoCdNm") String sidoCdNm);
}
