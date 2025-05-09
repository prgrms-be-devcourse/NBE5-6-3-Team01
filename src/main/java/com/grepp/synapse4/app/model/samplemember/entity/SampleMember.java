package com.grepp.synapse4.app.model.samplemember.entity;

import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class SampleMember extends BaseEntity {
    
    @Id
    private String userId;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String tel;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId")
    private SampleMemberInfo info;
    
    public void updateLoginedAt(LocalDateTime time){
        info.setLoginDate(time);
    }
}
