package com.mete.eshop.services;

import com.mete.eshop.configuration.RandomString;
import com.mete.eshop.model.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mete.eshop.model.UserAccount;
import com.mete.eshop.model.UserAccountRepository;
import com.mete.eshop.security.CustomOidcUser;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                authorities
        );
    }

    @Transactional
    public OidcUser processSocialUser(String provider,
                                      String email,
                                      String providerId,
                                      String name,
                                      String picture,
                                      OidcIdToken idToken,
                                      OidcUserInfo userInfo) {

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException("Email not provided by OAuth2 provider: " + provider);
        }

        UserAccount user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(email, provider, providerId, name, picture));

        // Update profile on every social login
        user.setName(name);
        user.setImageUrl(picture);
        userRepository.save(user);

        return new CustomOidcUser(
                AuthorityUtils.createAuthorityList("ROLE_USER"),
                idToken,
                userInfo,
                "email",
                user
        );
    }

    private UserAccount createNewUser(String email, String provider, String providerId, String name, String picture) {
        UserAccount user = new UserAccount();
        user.setEmail(email);
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        user.setProviderId(providerId);
        user.setName(name != null && !name.isBlank() ? name : email.split("@")[0]);
        user.setImageUrl(picture);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(RandomString.make(64)));

        user.getRoles().add("ROLE_USER");

        return userRepository.save(user);
    }
}