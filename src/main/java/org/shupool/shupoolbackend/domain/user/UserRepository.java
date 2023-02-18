package org.shupool.shupoolbackend.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.nickname = :nickname and u.socialId = :socialId")
    Optional<User> existsUser(@Param("nickname") String nickname, @Param("socialId") String socialId);
}
