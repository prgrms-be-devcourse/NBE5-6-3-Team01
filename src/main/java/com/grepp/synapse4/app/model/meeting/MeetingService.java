package com.grepp.synapse4.app.model.meeting;

import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.dto.*;
import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.meeting.entity.MeetingMember;
import com.grepp.synapse4.app.model.meeting.repository.MeetingMemberRepository;
import com.grepp.synapse4.app.model.meeting.repository.MeetingRepository;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.app.model.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingService {

  private final MeetingRepository meetingRepository;
  private final ModelMapper mapper;
  private final UserRepository userRepository;
  private final MeetingMemberRepository meetingMemberRepository;


  // 관리자 모든 모임 불러오기
  @Transactional(readOnly = true)
  public List<AdminMeetingDto> findAllForAdmin() {
    return meetingRepository.findAllWithCreatorAccount();
  }

  // 관리자페이지 모임멤버보기
  @Transactional(readOnly = true)
  public List<AdminMeetingMemberDto> findAdminMeetingByUserNickname(Long meetingId) {
    return meetingMemberRepository.findUserNicknamesByMeetingId(meetingId);
  }

  // 모임 등록하기
  @Transactional
  public void registMeeting(MeetingDto dto){
    Meeting meeting = mapper.map(dto, Meeting.class);
    User user = userRepository.findById(dto.getCreatorId())
        .orElseThrow(() -> new RuntimeException("유저를 찾지 못했습니다."));
    meeting.setUser(user);
    meetingRepository.save(meeting);

    MeetingMember meetingMember = new MeetingMember();  // -> builder
    meetingMember.setState(State.ACCEPT);
    meetingMember.setMeeting(meeting);
    meetingMember.setUser(user);

    meetingMemberRepository.save(meetingMember);
  }

  // 본인의 모임 리스트 불러오기
  @Transactional(readOnly = true)
  public List<Meeting> findMeetingsByUserId(Long userId){
    List<MeetingMember> meetingMemberList =  meetingMemberRepository.findAllByUserIdAndStateAndDeletedAtIsNull(userId, State.ACCEPT);

    return meetingMemberList.stream()
        .map(MeetingMember::getMeeting)
        .sorted(Comparator.comparing(Meeting::getCreatedAt).reversed())
        .collect(Collectors.toList());
  }

  // 모임 상세정보 불러오기
  @Transactional(readOnly = true)
  public Meeting findMeetingsById(Long id) {
    return meetingRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("모임을 찾지 못했습니다."));
  }

  // 모임의 멤버 수 불러오기
  @Transactional(readOnly = true)
  public Integer countMemberByMeeting(Long id) {
    return meetingMemberRepository.countByMeetingIdAndState(id, State.ACCEPT);
  }

  // 모임 탈퇴하기
  @Transactional
  public void leaveMeeting(Long id, Long userId) {
    MeetingMember member = meetingMemberRepository.findByMeetingIdAndUserId(id, userId)
        .orElseThrow(() -> new RuntimeException("해당 멤버가 없습니다."));

    meetingMemberRepository.delete(member);

    // 멤버가 없을 시 모임 제거
    int memberCount = meetingMemberRepository.countByMeetingId(id);
    if(memberCount == 0){
      Meeting meeting = meetingRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("모임을 찾지 못했습니다"));
      meetingRepository.delete(meeting);
    }
  }

  // 모임 초대하기
  @Transactional
  public void inviteUser(MeetingMemberDto dto) {
    MeetingMember member = new MeetingMember();
    Meeting meeting = meetingRepository.findById(dto.getMeetingId())
        .orElseThrow(() -> new RuntimeException("모임을 찾지 못했습니다."));
    User user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("유저를 찾지 못했습니다."));

    member.setState(State.WAIT);
    member.setMeeting(meeting);
    member.setUser(user);

    meetingMemberRepository.save(member);
  }

  // 모임 초대 수락, 거절
  @Transactional
  public void updateInvitedState(Long meetingId, Long userId, State state) {
    MeetingMember member = meetingMemberRepository.findByMeetingIdAndUserId(meetingId, userId)
        .orElseThrow(() -> new EntityNotFoundException("데이터를 찾지 못했습니다"));

    if (state.equals(State.REJECT)) {
      meetingMemberRepository.delete(member);
    } else if (state.equals(State.ACCEPT)) {
      member.setState(State.ACCEPT);
      meetingMemberRepository.save(member);
    }
  }

  // 모임 초대 리스트 불러오기
  @Transactional(readOnly = true)
  public List<MeetingMember> findInviteByUserId(Long userId) {
    return meetingMemberRepository.findAllByUserIdAndStateAndDeletedAtIsNull(userId, State.WAIT);
  }

  // 모임에 유저 포함 여부 확인
  @Transactional(readOnly = true)
  public Boolean findMemberByMeetingIdAndUserId(Long meetingId, Long userId) {
    return meetingMemberRepository.existsAllByMeetingIdAndUserId(meetingId, userId);
  }

  // 모임의 멤버 리스트 불러오기
  @Transactional(readOnly = true)
  public List<User> findMemberListByMeetingId(Long meetingId, State state) {
    List<MeetingMember> memberList = meetingMemberRepository.findAllByMeetingIdAndStateAndDeletedAtIsNull(meetingId, state);

    return memberList.stream()
            .map(MeetingMember::getUser)
            .collect(Collectors.toList());
  }


  // 멤버 리스트 관련 설정
  public void setInviteModel(Model model, Long meetingId, String errorMessage) {
    List<User> invitedList = this.findMemberListByMeetingId(meetingId, State.WAIT);
    model.addAttribute("invitedList", invitedList);
    model.addAttribute("meetingId", meetingId);
    if (errorMessage != null) {
      model.addAttribute("error", errorMessage);
    }
  }

  // 관리자페이지 모임검색
  @Transactional(readOnly = true)
  public List<AdminMeetingSearchDto> findAdminMeetingByTitle(String title) {
    return meetingRepository.findByTitleContaining(title);
  }

  @Transactional
  public void deleteAdminMeeting(Long meetingId) {
    List<MeetingMember> members = meetingMemberRepository.findAllByMeetingId(meetingId);
    meetingMemberRepository.deleteAll(members);
    meetingRepository.deleteById(meetingId);
  }
}