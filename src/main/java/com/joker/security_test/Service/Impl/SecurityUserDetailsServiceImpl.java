package com.joker.security_test.Service.Impl;

import com.joker.security_test.Dao.DaoUser;
import com.joker.security_test.Enity.Roles;
import com.joker.security_test.Enity.SecurityUserDetails;
import com.joker.security_test.Enity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SecurityUserDetailsServiceImpl
 * @Description:
 * @Author JOKER
 * @Date: 2019/6/2 11:16
 * @Version
 */
@Primary
@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    private DaoUser daoUser;

    @Autowired
    public SecurityUserDetailsServiceImpl(DaoUser daoUser){

        this.daoUser = daoUser;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = daoUser.selectByUsername(username);
        if (user != null){

            List<SimpleGrantedAuthority> collect = user.getRoles().stream().map(Roles::getRolename)
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            return new SecurityUserDetails(user.getUsername(),user.getUserpass(),user.getState(),collect);

        }

        System.out.println("该用户不存在");
        return null;
    }
}
