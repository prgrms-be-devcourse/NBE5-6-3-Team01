package com.grepp.synapse4.app.model.user.repository;

import com.grepp.synapse4.app.model.user.dto.AdminUserSearchDto;
import com.grepp.synapse4.app.model.user.entity.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
    Optional<User> findByNameAndEmail(String name, String email);
    Optional<User> findByUserAccountAndNameAndEmail(String userAccount, String name, String email);

    boolean existsByUserAccount(String userAccount);

    List<AdminUserSearchDto> findByUserAccountContaining(String userAccount);

}
