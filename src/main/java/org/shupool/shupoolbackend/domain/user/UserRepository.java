package org.shupool.shupoolbackend.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(String kakaoId);

    boolean existsByKakaoId(String kakaoId);
}