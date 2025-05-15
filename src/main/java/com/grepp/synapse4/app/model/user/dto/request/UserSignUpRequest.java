package com.grepp.synapse4.app.model.user.dto.request;

import com.grepp.synapse4.app.model.auth.code.Role;
import com.grepp.synapse4.app.model.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[^\\s]+$", message = "아이디는 공백을 포함할 수 없습니다.")
    private String userAccount;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[^\\s]+$", message = "비밀번호는 공백을 포함할 수 없습니다.")
    @Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,20}$", message = "닉네임은 특수문자를 제외한 2~10자여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    public UserSignUpRequest() {

    }

    @Builder
    public UserSignUpRequest(String userAccount, String password, String nickname, String email) {
        this.userAccount = userAccount;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public User toEntity(String encodedPassword, Role role) {
        return User.builder()
            .userAccount(this.userAccount)
            .password(encodedPassword)
            .nickname(this.nickname)
            .email(this.email)
            .isSurvey(false)
            .activated(true)
            .role(role)
            .build();
    }
}
