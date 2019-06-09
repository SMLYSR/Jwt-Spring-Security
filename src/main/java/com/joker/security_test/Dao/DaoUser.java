package com.joker.security_test.Dao;

import com.joker.security_test.Enity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DaoUser {

    User selectByUsername(String username);

}
