package com.joker.security_test.Enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @ClassName SecurityUserDetails
 * @Description:
 * @Author JOKER
 * @Date: 2019/6/2 11:02
 * @Version
 */
public class SecurityUserDetails implements UserDetails {

    private String username;

    private String password;

    private Integer state;

    private Collection<? extends GrantedAuthority> authorties;

    public SecurityUserDetails(String username,String password,Integer state,Collection<? extends GrantedAuthority> authorties){

        this.username = username;

        this.password = password;

        this.state = state;

        this.authorties = authorties;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorties;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return state == 1;
    }
}
