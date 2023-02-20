package org.shupool.shupoolbackend.service.user;

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
import org.shupool.shupoolbackend.config.auth.dto.KakaoInfo;
import org.shupool.shupoolbackend.config.jwt.JwtProvider;
import org.shupool.shupoolbackend.config.jwt.TokenInfo;
import org.shupool.shupoolbackend.domain.member.Role;
import org.shupool.shupoolbackend.domain.member.Member;
import org.shupool.shupoolbackend.domain.member.MemberRepository;
import org.shupool.shupoolbackend.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final JwtProvider jwtProvider;

    @Transactional
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

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();

            KakaoInfo kakaoInfo = KakaoInfo.convert(element, properties);

            Member member = findUserOrCreate(kakaoInfo);
            TokenInfo tokenInfo = jwtProvider.createToken(member.getSocialId());

            br.close();
            return tokenInfo;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public Member findUserOrCreate(KakaoInfo kakaoInfo) {
        Member member = null;
        if (!memberRepository.existsMember(kakaoInfo.getNickname(), kakaoInfo.getSocialId()).isPresent()) {
            member = Member.builder()
                .nickname(kakaoInfo.getNickname()).email(kakaoInfo.getEmail())
                .socialId(kakaoInfo.getSocialId())
                .password(PasswordUtil.generateRandomPassword())
                .imageUrl(kakaoInfo.getProfileImage())
                .roles(Collections.singletonList(Role.USER.getKey()))
                .build();
            memberRepository.save(member);
            return member;
        }
        return memberRepository.findBySocialId(kakaoInfo.getSocialId()).get();
    }

}
