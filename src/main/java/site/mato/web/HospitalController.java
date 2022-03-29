package site.mato.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.mato.domain.Hospital;
import site.mato.domain.HospitalRepository;

@RequiredArgsConstructor
@Controller
public class HospitalController {
	private final HospitalRepository hRepository;
	private static final String TAG = "HospitalController : ";
		
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("sidoCdNms", hRepository.mFindSidoCdNm()); // 여러개니까 끝에 복수 적어주자 s
        model.addAttribute("sgguCdNms", hRepository.mFindSggucdnm("서울")); // 여러개니까 끝에 복수 적어주자 s

        // 이제 index 페이지로 요청하면 데이터 안가져옴.
        return "index"; // templates/index.mustache 찾음
	}
	
	@GetMapping("/api/hospital")
	public @ResponseBody List<Hospital> hospitals(String sidoCdNm, String sgguCdNm) {
        System.out.println(TAG + hRepository.mFindSidoCdNm().size());
        return hRepository.mFindHospital(sidoCdNm, sgguCdNm);
    }
}
