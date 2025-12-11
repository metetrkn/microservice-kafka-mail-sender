package com.mete.eshop.services;

import com.mete.eshop.security.CustomOidcUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmartOidcUserService extends OidcUserService {

    private final ShopUserDetailsService userDetailsService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // This line does everything: finds or creates user, updates profile, returns CustomOidcUser
        return userDetailsService.processSocialUser(
                provider,
                oidcUser.getEmail(),
                oidcUser.getSubject(),        // sub = providerId (LinkedIn/Google ID)
                oidcUser.getFullName(),
                oidcUser.getPicture(),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo()
        );
    }
}