package org.shupool.shupoolbackend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class MemberDto {

    private String nickname;

    public static MemberDto entityToDto(String nickname) {
        return MemberDto.builder()
            .nickname(nickname)
            .build();
    }
}
