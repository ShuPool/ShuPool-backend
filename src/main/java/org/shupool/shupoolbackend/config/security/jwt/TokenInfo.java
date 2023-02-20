package org.shupool.shupoolbackend.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfo {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
}
