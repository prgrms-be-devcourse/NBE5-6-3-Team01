package com.grepp.synapse4.app.controller.web.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CurationController {

    @GetMapping("/curation")
    public String curationList() {
        return "recommend/userCuration";
    }
}
