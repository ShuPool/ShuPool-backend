package org.shupool.shupoolbackend.config.auth.dto;


public interface Oauth2UserInfo {

    String getProviderId();
    String getProvider();
    String getEmail();
    String getNickName();
    String getImageUrl();
}
