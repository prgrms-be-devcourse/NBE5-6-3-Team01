package com.grepp.synapse4.app.controller.web.meeting;

import com.grepp.synapse4.app.controller.web.meeting.payload.MeetingInviteRequest;
import com.grepp.synapse4.app.controller.web.meeting.payload.MeetingRegistRequest;
import com.grepp.synapse4.app.model.meeting.MeetingService;
import com.grepp.synapse4.app.model.meeting.code.Purpose;
import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.dto.MeetingDto;
import com.grepp.synapse4.app.model.meeting.dto.MeetingMemberDto;
import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.meeting.entity.MeetingMember;
import com.grepp.synapse4.app.model.user.CustomUserDetailsService;
import com.grepp.synapse4.app.model.user.entity.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("meetings")
@RequiredArgsConstructor
public class MeetingController {

  private final MeetingService meetingService;
  private final CustomUserDetailsService customUserDetailsService;

  @GetMapping
  public String meeting(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = customUserDetailsService.loadUserIdByAccount(authentication.getName());

    List<Meeting> meetingList = meetingService.findMeetingsByUserId(userId);
    model.addAttribute("meetingList", meetingList);

    return "meetings/meetings";
  }

  @GetMapping("regist")
  public String regist(Model model) {
    model.addAttribute("meetingRegistRequest", new MeetingRegistRequest());
    model.addAttribute("purpose", Purpose.values());
    return "meetings/meeting-regist";
  }

  @PostMapping("regist")
  public String regist(
      @Valid MeetingRegistRequest form,
      BindingResult bindingResult,
      Model model
  ){
    if(bindingResult.hasErrors()){
      model.addAttribute("purpose", Purpose.values());
      return "meetings/meeting-regist";
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = customUserDetailsService.loadUserIdByAccount(authentication.getName());

    MeetingDto dto = form.toDto(userId);
    meetingService.registMeeting(dto);

    return "redirect:/meetings";
  }

  @GetMapping("detail")
  public String detail(
      @RequestParam Long id,
      Model model
  ){
    Meeting meeting = meetingService.findMeetingsById(id);
    model.addAttribute("meeting", meeting);
    Integer count = meetingService.countMemberByMeeting(id);
    model.addAttribute("count", count);

    return "meetings/meeting-detail";
  }

  @PostMapping("detail")
  public String detail(
      @RequestParam Long id
  ){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = customUserDetailsService.loadUserIdByAccount(authentication.getName());

    meetingService.leaveMeeting(id, userId);

    return "redirect:/meetings";
  }

  @GetMapping("/modal/alarm-invite.html")
  @PreAuthorize("isAuthenticated()")
  public String invitePopup(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = customUserDetailsService.loadUserIdByAccount(authentication.getName());

    List<MeetingMember> invitedList = meetingService.findInviteByUserId(userId);
    model.addAttribute("invitedList", invitedList);
    log.info("invitedList: {}", invitedList);

    return "meetings/modal/alarm-invite";
  }

  @PostMapping("/modal/alarm-invite.html")
  @PreAuthorize("isAuthenticated()")
  public String invitePopup(
      @RequestParam Long id,
      @RequestParam String state
  ){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = customUserDetailsService.loadUserIdByAccount(authentication.getName());

    Boolean result = meetingService.updateInvitedState(id, userId, state);

    return "redirect:/meetings/modal/alarm-invite.html";
  }

  @GetMapping("/modal/alarm-vote.html")
  @PreAuthorize("isAuthenticated()")
  public String votePopup(Model model) {
    return "meetings/modal/alarm-vote";
  }

  @PostMapping("/modal/alarm-vote.html")
  @PreAuthorize("isAuthenticated()")
  public String votePopup() {
    return "redirect:/meetings/modal/alarm-vote";
  }

  @GetMapping("/modal/meeting-member-list.html")
  @PreAuthorize("isAuthenticated()")
  public String meetingMemberListPopup(
      @RequestParam Long id,
      Model model
  ) {
    Meeting meeting = meetingService.findMeetingsById(id);
    model.addAttribute("meeting", meeting);

    List<User> userList = meetingService.findMemberListByMeetingId(id, State.ACCEPT);
    model.addAttribute("userList", userList);

    return "meetings/modal/meeting-member-list";
  }

  @GetMapping("/modal/meeting-invite.html")
  @PreAuthorize("isAuthenticated()")
  public String meetingInvite(
      @RequestParam Long id,
      Model model
  ){
    meetingService.setInviteModel(model, id, null);

    return "meetings/modal/meeting-invite";
  }

  @PostMapping("/modal/meeting-invite.html")
  @PreAuthorize("isAuthenticated()")
  public String meetingInvite(
      @Valid MeetingInviteRequest invite,
      @RequestParam Long id,
      @RequestParam String account,
      Model model
  ){
    Boolean existByUser = customUserDetailsService.findUserByAccount(account);
    if(!existByUser){
      meetingService.setInviteModel(model, id, "존재하지 않는 유저입니다.");

      return "meetings/modal/meeting-invite";
    }

    Long userId = customUserDetailsService.loadUserIdByAccount(account);
    Boolean existByMeetingMember = meetingService.findMemberByMeetingIdAndUserId(id, userId);
    if(existByMeetingMember){
      meetingService.setInviteModel(model, id, "이미 초대된 유저입니다.");
      return "meetings/modal/meeting-invite";
    }

    MeetingMemberDto dto = invite.toDto(id, userId);
    meetingService.inviteUser(dto);

    return "redirect:/meetings/modal/meeting-invite.html?id="+id;
  }
}
