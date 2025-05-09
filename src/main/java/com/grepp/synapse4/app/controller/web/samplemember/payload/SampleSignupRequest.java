package com.grepp.synapse4.app.controller.web.samplemember.payload;

import com.grepp.synapse4.app.model.samplemember.dto.SampleMemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SampleSignupRequest {
    
    @NotBlank
    private String userId;
    @NotBlank
    @Size(min = 4, max = 10)
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 14)
    private String tel;
    
    public SampleMemberDto toDto(){
        SampleMemberDto sampleMemberDto = new SampleMemberDto();
        sampleMemberDto.setUserId(userId);
        sampleMemberDto.setPassword(password);
        sampleMemberDto.setEmail(email);
        sampleMemberDto.setTel(tel);
        return sampleMemberDto;
    }
}
