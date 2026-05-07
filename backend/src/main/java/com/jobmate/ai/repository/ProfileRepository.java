package com.jobmate.ai.repository;

import com.jobmate.ai.entity.Profile;
import com.jobmate.ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(User user);

    Optional<Profile> findByUserId(Long userId);
}
