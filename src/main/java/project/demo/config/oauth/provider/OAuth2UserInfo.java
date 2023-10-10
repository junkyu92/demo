package project.demo.config.oauth.provider;

import project.demo.constant.Provider;

public interface OAuth2UserInfo {
    Provider getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
