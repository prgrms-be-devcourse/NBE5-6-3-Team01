package com.grepp.synapse4.app.controller.web.admin;

import com.grepp.synapse4.app.controller.web.samplemember.payload.SampleSignupRequest;
import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.app.model.samplemember.SampleMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final SampleMemberService sampleMemberService;
    
    @GetMapping
    public String dashboard(){
        return "admin/dashboard";
    }
    
    @GetMapping("signup")
    public String signup(SampleSignupRequest form){
        return "admin/signup";
    }
    
    @PostMapping("signup")
    public String signup(@Valid SampleSignupRequest form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/signup";
        }
        
        sampleMemberService.signup(form.toDto(), Role.ROLE_ADMIN);
        return "redirect:/";
    }
}
