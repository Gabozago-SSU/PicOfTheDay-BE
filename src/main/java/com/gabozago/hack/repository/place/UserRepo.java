package com.gabozago.hack.repository.place;

import com.gabozago.hack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByName(String nickname);
    Optional<User> findByEmail(String email);
}
