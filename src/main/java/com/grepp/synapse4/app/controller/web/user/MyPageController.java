package com.grepp.synapse4.app.controller.web.user;

import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import com.grepp.synapse4.app.model.user.dto.request.EditInfoRequest;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.app.model.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;

    @GetMapping
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "user/mypage";
    }

    @GetMapping("/edit-info")
    public String editInfoForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        EditInfoRequest dto = new EditInfoRequest();
        dto.setUserAccount(user.getUserAccount());
        dto.setNickname(user.getNickname());
        dto.setEmail(user.getEmail());
        model.addAttribute("editRequest", dto);
        return "user/edit-info";
    }

    @PostMapping("/edit-info")
    public String updateInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @ModelAttribute("editRequest") EditInfoRequest request,
        BindingResult bindingResult) {

        User user = userDetails.getUser();

        userService.validateEditUser(request, user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/edit-info";
        }

        userService.updateUserInfo(request, user);
        return "redirect:/mypage";
    }


}
