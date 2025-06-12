package com.grepp.synapse4.app.controller.web.meeting;

import com.grepp.synapse4.app.controller.web.meeting.payload.meeting.MeetingInviteRequest;
import com.grepp.synapse4.app.controller.web.meeting.payload.meeting.MeetingRegistRequest;
import com.grepp.synapse4.app.model.meeting.MeetingService;
import com.grepp.synapse4.app.model.meeting.VoteService;
import com.grepp.synapse4.app.model.meeting.code.Purpose;
import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.dto.MeetingDto;
import com.grepp.synapse4.app.model.meeting.dto.MeetingMemberDto;
import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.meeting.entity.MeetingMember;
import com.grepp.synapse4.app.model.meeting.entity.vote.Vote;
import com.grepp.synapse4.app.model.user.CustomUserDetailsService;
import com.grepp.synapse4.app.model.user.entity.User;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final VoteService voteService;

  @GetMapping
  public String meeting(Model model){
    Long userId = customUserDetailsService.loadUserIdByAccount();

    List<Meeting> meetingList = meetingService.findMeetingsByUserId(userId);
    model.addAttribute("meetingList", meetingList);

    return "meetings/meetings";
  }

  // 모임 생성 화면
  @GetMapping("regist")
  public String regist(Model model) {
    model.addAttribute("meetingRegistRequest", new MeetingRegistRequest());
    model.addAttribute("purpose", Purpose.values());
    return "meetings/meeting-regist";
  }

  // 모임 생성하기
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
    Long userId = customUserDetailsService.loadUserIdByAccount();

    MeetingDto dto = form.toDto(userId);
    meetingService.registMeeting(dto);

    return "redirect:/meetings";
  }

  // 모임 상세정보 (멤버, 투표)
  @GetMapping("/detail/{id}")
  public String detail(
      @PathVariable Long id,
      Model model
  ){
    Long userId = customUserDetailsService.loadUserIdByAccount();

    Meeting meeting = meetingService.findMeetingsById(id);
    model.addAttribute("meeting", meeting);
    Integer count = meetingService.countMemberByMeeting(id);
    model.addAttribute("count", count);
    List<Vote> voteList = voteService.findVoteListByMeetingId(id);
    model.addAttribute("voteList", voteList);

    if(!voteList.isEmpty()){ // 모임의 투표에 대한 정보
      Map<Long, Boolean> isVotedMap = voteService.isVotedByUser(voteList, userId);
      model.addAttribute("isVotedMap", isVotedMap);
    }

    return "meetings/meeting-detail";
  }

  // 모임 탈퇴하기
  @PostMapping("/detail/{id}")
  public String detail(@PathVariable Long id){
    Long userId = customUserDetailsService.loadUserIdByAccount();
    meetingService.leaveMeeting(id, userId);

    return "redirect:/meetings";
  }

  @GetMapping("/modal/alarm-invite.html")
  @PreAuthorize("isAuthenticated()")
  public String invitePopup(Model model) {
    Long userId = customUserDetailsService.loadUserIdByAccount();

    List<MeetingMember> invitedList = meetingService.findInviteByUserId(userId);
    model.addAttribute("invitedList", invitedList);

    return "meetings/modal/alarm-invite";
  }

  @GetMapping("/modal/meeting-members/{id}.html")
  @PreAuthorize("isAuthenticated()")
  public String meetingMemberListPopup(
      @PathVariable Long id,
      Model model
  ) {
    Meeting meeting = meetingService.findMeetingsById(id);
    model.addAttribute("meeting", meeting);

    List<User> userList = meetingService.findMemberListByMeetingId(id, State.ACCEPT);
    model.addAttribute("userList", userList);

    return "meetings/modal/meeting-members";
  }

  @GetMapping("/modal/meeting-invite/{id}.html")
  @PreAuthorize("isAuthenticated()")
  public String meetingInvite(
      @PathVariable Long id,
      Model model
  ){
    meetingService.setInviteModel(model, id, null);

    return "meetings/modal/meeting-invite";
  }

}