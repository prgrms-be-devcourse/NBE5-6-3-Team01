package com.grepp.synapse4.app.model.user;

import static com.grepp.synapse4.app.model.auth.code.Role.ROLE_USER;

import com.grepp.synapse4.app.model.user.dto.request.EditInfoRequest;
import com.grepp.synapse4.app.model.user.dto.request.UserSignUpRequest;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.app.model.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignUpRequest request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntity(encodedPassword, ROLE_USER);
        userRepository.save(user);
    }

    public void validateDuplicateUser(UserSignUpRequest request, BindingResult bindingResult) {
        if (userRepository.existsByUserAccount(request.getUserAccount())) {
            bindingResult.rejectValue("userAccount", "duplicate", "이미 등록된 아이디입니다.");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            bindingResult.rejectValue("nickname", "duplicate", "이미 등록된 닉네임입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
        }
    }

    public void validateEditUser(EditInfoRequest request, User user, BindingResult bindingResult) {
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            bindingResult.rejectValue("currentPassword", "invalid", "기존 비밀번호가 일치하지 않습니다.");
        }

        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
                bindingResult.rejectValue("newPassword", "sameAsOld", "기존 비밀번호와 동일합니다.");
            }
        }

        Optional<User> userByNickname = userRepository.findByNickname(request.getNickname());
        if (userByNickname.isPresent() && !userByNickname.get().getId().equals(user.getId())) {
            bindingResult.rejectValue("nickname", "duplicate", "이미 사용 중인 닉네임입니다.");
        }

        Optional<User> userByEmail = userRepository.findByEmail(request.getEmail());
        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(user.getId())) {
            bindingResult.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
        }
    }

    public void updateUserInfo(EditInfoRequest request, User user) {
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());

        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
    }

}
