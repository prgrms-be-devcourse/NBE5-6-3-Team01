package com.grepp.synapse4.app.model.user;

import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.app.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        User user = userRepository.findByUserAccount(userAccount)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new CustomUserDetails(user);
    }

    public Long loadUserIdByAccount(String account) throws UsernameNotFoundException{
        User user = userRepository.findByUserAccount(account)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getId();
    }

    public Boolean findUserByAccount(String account) throws UsernameNotFoundException{
        return userRepository.existsByUserAccount(account);
    }
}
