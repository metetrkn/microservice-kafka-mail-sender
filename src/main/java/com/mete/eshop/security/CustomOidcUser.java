package com.mete.eshop.security;

import com.mete.eshop.model.UserAccount;  // ← THIS IS YOUR ENTITY
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Collection;

public class CustomOidcUser extends org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser {

    private final UserAccount appUser;  // ← Changed from User to UserAccount

    public CustomOidcUser(Collection<? extends GrantedAuthority> authorities,
                          OidcIdToken idToken,
                          OidcUserInfo userInfo,
                          String nameAttributeKey,
                          UserAccount appUser) {  // ← Changed parameter type
        super(authorities, idToken, userInfo, nameAttributeKey);
        this.appUser = appUser;
    }

    public UserAccount getAppUser() {
        return appUser;
    }

    @Override
    public String getName() {
        return appUser.getName();
    }

    @Override
    public String getEmail() {
        return appUser.getEmail();
    }
}