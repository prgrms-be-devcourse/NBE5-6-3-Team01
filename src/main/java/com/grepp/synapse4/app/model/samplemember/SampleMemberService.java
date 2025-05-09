package com.grepp.synapse4.app.model.samplemember;

import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.app.model.samplemember.dto.SampleMemberDto;
import com.grepp.synapse4.app.model.samplemember.entity.SampleMember;
import com.grepp.synapse4.app.model.samplemember.entity.SampleMemberInfo;
import com.grepp.synapse4.infra.error.exceptions.CommonException;
import com.grepp.synapse4.infra.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleMemberService {
    
    private final PasswordEncoder passwordEncoder;
    private final SampleMemberRepository sampleMemberRepository;
    private final ModelMapper mapper;
    
    @Transactional
    public void signup(SampleMemberDto dto, Role role) {
        if(sampleMemberRepository.existsById(dto.getUserId()))
            throw new CommonException(ResponseCode.BAD_REQUEST);
        
        SampleMember sampleMember = mapper.map(dto, SampleMember.class);
        
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        sampleMember.setPassword(encodedPassword);
        sampleMember.setRole(role);
        
        SampleMemberInfo sampleMemberInfo = new SampleMemberInfo();
        sampleMemberInfo.setUserId(dto.getUserId());
        sampleMember.setInfo(sampleMemberInfo);
        sampleMemberRepository.save(sampleMember);
    }
    
    public Boolean isDuplicatedId(String id) {
        return sampleMemberRepository.existsById(id);
    }
    
    public SampleMemberDto findById(String userId) {
        SampleMember sampleMember = sampleMemberRepository.findById(userId)
                            .orElseThrow(() -> new CommonException(ResponseCode.BAD_REQUEST));
        return mapper.map(sampleMember, SampleMemberDto.class);
    }
}
