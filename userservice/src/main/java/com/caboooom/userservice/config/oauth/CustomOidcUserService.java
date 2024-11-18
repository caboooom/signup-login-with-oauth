package com.caboooom.userservice.config.oauth;

import com.caboooom.userservice.config.auth.PrincipalDetails;
import com.caboooom.userservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import com.caboooom.userservice.repository.UserRepository;

@Service
@Slf4j
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final String PASSWORD_PREFIX = "asdf";
    private final String PASSWORD_SUFFIX = "qwer";

    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("userRequest: {}", userRequest);
        log.debug("accessToken: {}", userRequest.getAccessToken());
        log.debug("clientRegistration: {}", userRequest.getClientRegistration());

        OidcUser oidcUser = super.loadUser(userRequest);
        log.debug("oidcUser: {}", oidcUser.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        if (!provider.equals("google")) {
            log.error("provider={}", provider);
            throw new OAuth2AuthenticationException("OAuth 로그인은 google만 제공함");
        }

        String userId = "google_" + oidcUser.getAttribute("sub");

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) { // 가입되지 않은 회원이라면 가입시키기
            String name = oidcUser.getAttribute("name");
            String email = oidcUser.getAttribute("email");

            String password = PASSWORD_PREFIX + oidcUser.getAttribute("sub") + PASSWORD_SUFFIX;

            user = new User(userId, name, email, password, "google");
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oidcUser.getAttributes(), oidcUser.getClaims(),
                oidcUser.getUserInfo(), oidcUser.getIdToken());

    }

}
