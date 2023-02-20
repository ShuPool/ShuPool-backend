package org.shupool.shupoolbackend.config.security.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.shupool.shupoolbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void test() {
        assertEquals("92e5a046fc8d1a2e135a70ce356db359", userService.getKakaoClientId());
    }
}