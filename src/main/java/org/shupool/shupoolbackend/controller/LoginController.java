package org.shupool.shupoolbackend.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.config.auth.UserService;
import org.shupool.shupoolbackend.config.jwt.TokenInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
@RestController
public class LoginController {

    private final UserService userService;

    @GetMapping(value = "/login")
    public TokenInfo login(@RequestBody Map<String, String> map) {
        String accessToken = map.get("code");
        return userService.loginKakaoUser(accessToken);
    }

    @ResponseBody
    @GetMapping("/member/test")
    public String kakaoCallback() {
        return "success";
    }
}
