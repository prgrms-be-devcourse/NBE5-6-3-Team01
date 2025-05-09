package com.grepp.synapse4.app.controller.web.samplemember;

import com.grepp.synapse4.app.controller.web.samplemember.payload.SampleSigninRequest;
import com.grepp.synapse4.app.controller.web.samplemember.payload.SampleSignupRequest;
import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.app.model.samplemember.SampleMemberService;
import com.grepp.synapse4.app.model.samplemember.dto.SampleMemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("member")
public class SampleMemberController {
    
    private final SampleMemberService sampleMemberService;

    @GetMapping("signup")
    public String signup(SampleSignupRequest form){
        return "member/signup";
    }
    
    @PostMapping("signup")
    public String signup(
        @Valid SampleSignupRequest form,
        BindingResult bindingResult,
        Model model){
        
        if(bindingResult.hasErrors()){
            return "member/signup";
        }
        
        sampleMemberService.signup(form.toDto(), Role.ROLE_USER);
        return "redirect:/";
    }
    
    @GetMapping("signin")
    public String signin(SampleSigninRequest form){
        return "member/signin";
    }
    
    
    @GetMapping("mypage")
    public String mypage(Authentication authentication, Model model){
        log.info("authentication : {}", authentication);
        String userId = authentication.getName();
        SampleMemberDto sampleMemberDto = sampleMemberService.findById(userId);
        model.addAttribute("member", sampleMemberDto);
        return "member/mypage";
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or authentication.name == #id")
    @GetMapping("{id}")
    public String get(@PathVariable String id, Model model){
        SampleMemberDto sampleMemberDto = sampleMemberService.findById(id);
        model.addAttribute("member", sampleMemberDto);
        return "member/mypage";
    }
}
