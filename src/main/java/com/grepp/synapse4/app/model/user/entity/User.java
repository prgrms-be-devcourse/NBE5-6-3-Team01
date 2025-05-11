package com.grepp.synapse4.app.model.user.entity;

import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert      //del이 null일 때 insert되지 않도록
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userAccount;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isSurvey = false;


    private LocalDateTime deletedAt;
    @ColumnDefault("true")
    private Boolean activated = true;


    //추가 Role
    @Enumerated(EnumType.STRING)
    private Role role;



}