package com.grepp.synapse4.app.controller.web.user;

import com.grepp.synapse4.app.model.user.dto.request.UserSignUpRequest;
import com.grepp.synapse4.app.model.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signin")
    public String signInForm() {
        return "user/signin";
    }

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("userSignUpRequest", new UserSignUpRequest());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@Valid @ModelAttribute("userSignUpRequest") UserSignUpRequest request,
        BindingResult bindingResult) {

        // 1차: @Valid 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        // 2차: 중복 필드 검사
        userService.validateDuplicateUser(request, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        userService.signup(request);
        return "redirect:/user/signin";
    }

}
