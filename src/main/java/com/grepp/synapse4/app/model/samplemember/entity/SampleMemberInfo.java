package com.grepp.synapse4.app.model.samplemember.entity;

import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class SampleMemberInfo extends BaseEntity {
    
    @Id
    private String userId;
    private LocalDateTime loginDate;
    private LocalDateTime modifyDate;
    private LocalDateTime leaveDate;
    private LocalDateTime rentableDate = LocalDateTime.now();
}
