package com.joker.security_test.Enity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Roles
 * @Description:
 * @Author JOKER
 * @Date: 2019/6/2 14:56
 * @Version
 */

@Data
public class Roles {

    private String roleid;

    private String rolename;

    private Integer state;

    private List<User> users;

}
