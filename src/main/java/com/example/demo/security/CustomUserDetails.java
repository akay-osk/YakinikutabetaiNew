package com.example.demo.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.User;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails {

    private final Integer userId;
    private Integer roomId;
    private final String username;
    private final String password;

    public CustomUserDetails(User user) {
        this.userId = user.getUser_id();
        this.username = user.getUser_name();
        this.password = user.getUser_pass(); // ハッシュ済みのパスワード
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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
}