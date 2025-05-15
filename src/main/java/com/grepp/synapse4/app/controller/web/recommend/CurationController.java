package com.grepp.synapse4.app.controller.web.recommend;

import com.grepp.synapse4.app.model.llm.CurationService;
import com.grepp.synapse4.app.model.llm.dto.CurationDto;
import com.grepp.synapse4.app.model.llm.dto.CurationRestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CurationController {

    private final CurationService curationService;

    @GetMapping("/curation")
    public String curationList(Model model) {
        CurationDto curation =
                curationService.getLatestCurationRestaurants();
        model.addAttribute("curation", curation);

        return "recommend/userCuration";
    }
}
