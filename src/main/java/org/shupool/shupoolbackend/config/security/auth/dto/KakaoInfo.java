package org.shupool.shupoolbackend.config.security.auth.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder @Getter
public class KakaoInfo {
    private final String socialId;
    private final String email;
    private final String nickname;
    private final String profileImage;

    public static KakaoInfo convert(JsonElement element, JsonObject properties) {
        return KakaoInfo.builder()
                .socialId(element.getAsJsonObject().get("id").getAsString())
                .email(parseEmail(element))
                .nickname(properties.getAsJsonObject().get("nickname").getAsString())
                .profileImage(properties.getAsJsonObject().get("profile_image").getAsString())
                .build();
    }

    private static String parseEmail(JsonElement element) {
        String email = null;
        boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email")
            .getAsBoolean();

        if (hasEmail) {
            email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
        }
        return email;
    }
}
