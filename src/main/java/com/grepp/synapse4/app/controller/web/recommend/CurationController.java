package com.grepp.synapse4.app.controller.web.recommend;

import com.grepp.synapse4.app.model.llm.CurationService;
import com.grepp.synapse4.app.model.llm.dto.UserCurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CurationController {

    private final CurationService curationService;

    @GetMapping("/curation")
    public String curationList(Model model) {
        UserCurationDto curation =
                curationService.getLatestCurationRestaurants();

        // DB 에 적절한 데이터가 없을 때 띄어주는 화면이 있으면 여러 컨트롤러에서
        // 걸리는 같은 이슈를 묶어 담을 수 있을 듯
        // 일단은 뷰에서 분기 처리..
//        if(curation == null){
//            model.addAttribute("message", "큐레이션 결과가 없습니다.");
//            return "noList";
//        }

        model.addAttribute("curation", curation);

        return "recommend/user-curation";
    }
}
