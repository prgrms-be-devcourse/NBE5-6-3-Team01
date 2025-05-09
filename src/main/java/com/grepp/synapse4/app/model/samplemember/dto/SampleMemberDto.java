package com.grepp.synapse4.app.model.samplemember.dto;

import com.grepp.synapse4.app.model.auth.code.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SampleMemberDto {
    private String userId;
    private String password;
    private String email;
    private Role role;
    private String tel;
    private MemberInfoDto info;
}
