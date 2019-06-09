package com.joker.security_test.Service;

import com.joker.security_test.Enity.User;

import javax.naming.AuthenticationException;

public interface UserService  {

    User findUserInfo(String username);

    String login(String username, String password) throws AuthenticationException;

}
