package org.shupool.shupoolbackend.config.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.config.jwt.JwtTokenProvider;
import org.shupool.shupoolbackend.config.jwt.TokenInfo;
import org.shupool.shupoolbackend.domain.user.Role;
import org.shupool.shupoolbackend.domain.user.Member;
import org.shupool.shupoolbackend.domain.user.MemberRepository;
import org.shupool.shupoolbackend.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    @Value("${kakao-client_id}")
    private String kakaoClientId;

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenInfo loginKakaoUser(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = "";

            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email")
                .getAsBoolean();

            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            Member member = findUserOrCreate(nickname, id, email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getSocialId(), member.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            br.close();
            return tokenInfo;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public Member findUserOrCreate(String nickname, String socialId, String email) {
        Member member = null;
        if (!memberRepository.existsMember(nickname, socialId).isPresent()) {
            member = Member.builder()
                .nickname(nickname).email(email)
                .socialId(socialId).role(Role.USER)
                .password(PasswordUtil.generateRandomPassword())
                .roles(Collections.singletonList(Role.USER.name()))
                .build();
            memberRepository.save(member);
            return member;
        }
        return memberRepository.findBySocialId(socialId).get();
    }

}
