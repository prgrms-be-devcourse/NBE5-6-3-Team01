package com.grepp.synapse4.app.controller.web;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.entity.RestaurantMenu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
