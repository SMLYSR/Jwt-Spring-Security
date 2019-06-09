package com.joker.security_test.Enity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName User
 * @Description:
 * @Author JOKER
 * @Date: 2019/5/30 20:59
 * @Version
 */
@Data
public class User {

    private String userid;

    private String username;

    private String userpass;

    private Integer state;

    private List<Roles> roles;
}
