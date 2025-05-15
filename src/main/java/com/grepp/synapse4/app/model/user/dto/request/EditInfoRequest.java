package com.grepp.synapse4.app.model.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditInfoRequest {

    private String userAccount;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String currentPassword;

    @Size(min = 4, max = 12, message = "비밀번호는 4~12자여야 합니다.")
    private String newPassword;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

}
