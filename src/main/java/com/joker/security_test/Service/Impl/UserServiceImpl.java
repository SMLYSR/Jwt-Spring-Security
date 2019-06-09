package com.joker.security_test.Service.Impl;

import com.joker.security_test.Dao.DaoUser;
import com.joker.security_test.Enity.User;
import com.joker.security_test.Service.UserService;
import com.joker.security_test.Util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * @ClassName UserServiceImpl
 * @Description:
 * @Author JOKER
 * @Date: 2019/5/31 21:50
 * @Version
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log  = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private AuthenticationManager authenticate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public User findUserInfo(String username) {
        return daoUser.selectByUsername(username);
    }

    @Override
    public String login(String username, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        Authentication auth = authenticate.authenticate(authRequest);

        log.debug("===============权限============" + auth);

        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtTokenUtil.generateToken(username);
    }


}
