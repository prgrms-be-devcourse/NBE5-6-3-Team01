package com.grepp.synapse4.app.model.user.entity;

import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import java.time.LocalDateTime;

//@ToString
//@DynamicInsert      //del이 null일 때 insert되지 않도록
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userAccount;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isSurvey = false;

    @ColumnDefault("true")
    private Boolean activated = true;

    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String userAccount, String password, String nickname, String email,
        Boolean isSurvey,
        Boolean activated, LocalDateTime deletedAt, Role role) {
        this.userAccount = userAccount;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.isSurvey = isSurvey;
        this.activated = activated;
        this.deletedAt = deletedAt;
        this.role = role;
    }
}
