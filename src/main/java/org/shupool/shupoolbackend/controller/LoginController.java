package org.shupool.shupoolbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.config.security.auth.dto.AuthCode;
import org.shupool.shupoolbackend.config.security.jwt.TokenInfo;
import org.shupool.shupoolbackend.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
@RestController
public class LoginController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public TokenInfo login(@RequestBody AuthCode authCode) {
        String accessToken = authCode.getCode();
        return userService.loginKakaoUser(accessToken);
    }

    @ResponseBody
    @GetMapping("/member/test")
    public String kakaoCallback() {
        return "success";
    }
}
