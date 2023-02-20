package org.shupool.shupoolbackend.controller;

import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.service.user.UserService;
import org.shupool.shupoolbackend.config.auth.dto.AuthCode;
import org.shupool.shupoolbackend.config.jwt.TokenInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
