package org.shupool.shupoolbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.config.security.auth.dto.AuthCode;
import org.shupool.shupoolbackend.config.security.jwt.JwtProvider;
import org.shupool.shupoolbackend.config.security.jwt.TokenInfo;
import org.shupool.shupoolbackend.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
@RestController
public class LoginController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/login")
    public TokenInfo login(@RequestBody AuthCode authCode) {
        String accessToken = authCode.getCode();
        return userService.loginKakaoUser(accessToken);
    }

    @ResponseBody
    @GetMapping("/member/test")
    public String kakaoCallback(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request).substring(7);
        return jwtProvider.getSocialId(token);
    }
}
