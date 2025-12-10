package com.example.OIL.global.security;

import com.example.OIL.domain.user.domain.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class OILUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;

    public OILUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재는 권한 시스템 없이 USER 고정
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email; // username = email
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
