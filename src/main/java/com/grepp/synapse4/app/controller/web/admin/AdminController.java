package com.grepp.synapse4.app.controller.web.admin;

import com.grepp.synapse4.app.model.llm.CurationResultService;
import com.grepp.synapse4.app.model.llm.CurationService;
import com.grepp.synapse4.app.model.llm.code.*;
import com.grepp.synapse4.app.model.llm.dto.CurationDto;
import com.grepp.synapse4.app.model.llm.dto.CurationResultDto;
import com.grepp.synapse4.app.model.meeting.MeetingService;
import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.user.UserService;
import com.grepp.synapse4.app.model.user.dto.request.UserSignUpRequest;
import com.grepp.synapse4.app.model.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final MeetingService meetingService;
    private final CurationService curationService;
    private final CurationResultService curationResultService;

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/meetings")
    public String meetings(Model model) {
        List<Meeting> meetings = meetingService.findAll();
        model.addAttribute("meetings", meetings);
        return "admin/meetings";
    }

    @GetMapping("/curation/register")
    public String curationRegister(Model model) {

        model.addAttribute("form", new CurationDto());
        model.addAttribute("companylocation", CompanyLocation.values());
        model.addAttribute("purpose", Purpose.values());
        model.addAttribute("compainons", Companion.values());
        model.addAttribute("favoritecategory", FavoriteCategory.values());
        model.addAttribute("preferredmood", PreferredMood.values());

        return "admin/curationRegister";
    }

    @PostMapping("/curation/register")
    public String curationRegister(@ModelAttribute("form") CurationDto curationDto) {
        curationService.setCuration(curationDto);
        return "redirect:admin/curation/list";
    }

    @GetMapping("/curation/list")
    public String curationList(Model model) {
        List<CurationResultDto> curationResults = curationResultService.getResultsByCurationId();
        model.addAttribute("curationResults", curationResults);
        return "admin/curationList";
    }

    @PostMapping("/curation/toggle/{id}")
    public String toggleActive(@PathVariable Long id) {
        curationResultService.toggleActive(id);
        return "redirect:/admin/curation/list";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        return "/admin/signin";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userSignUpRequest", new UserSignUpRequest());
        return "admin/signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@Valid @ModelAttribute("userSignUpRequest") UserSignUpRequest request,
                                BindingResult bindingResult) {

        // 1차: @Valid 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "admin/signup";
        }

        // 2차: 중복 필드 검사
        userService.validateDuplicateUser(request, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/signup";
        }

        userService.signupAdmin(request);
        return "redirect:admin/signin";
    }

}
