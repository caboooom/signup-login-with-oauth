package com.caboooom.userservice.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import com.caboooom.userservice.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OidcUser {

    private User user;
    private Map<String, Object> attributes;
    private Map<String, Object> claims;
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;

    /**
     * 일반 로그인 시 호출되는 생성자입니다.
     * @param user
     */
    public PrincipalDetails(User user) {
        this.user = user;
    }

    /**
     * 구글 OAuth 로그인 시 호출되는 생성자입니다.
     * @param user
     * @param attributes
     */
    public PrincipalDetails(User user, Map<String, Object> attributes, Map<String, Object> claims, OidcUserInfo userInfo, OidcIdToken idToken) {
        this.user = user;
        this.attributes = attributes;
        this.claims = claims;
        this.userInfo = userInfo;
        this.idToken = idToken;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> user.getAuth());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return claims;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }
}
