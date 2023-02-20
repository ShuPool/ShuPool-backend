package org.shupool.shupoolbackend.domain.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findBySocialId(String socialId);

    @Query("select u from Member u where u.nickname = :nickname and u.socialId = :socialId")
    Optional<Member> existsMember(@Param("nickname") String nickname, @Param("socialId") String socialId);
}
