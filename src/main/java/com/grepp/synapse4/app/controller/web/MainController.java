package com.grepp.synapse4.app.controller.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @Value("${kakao.maps.api-key}")
    private String kakaoMapApiKey;

    @GetMapping("/")
    public String main(Model model) {

        model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
        return "main";
    }
    
}
