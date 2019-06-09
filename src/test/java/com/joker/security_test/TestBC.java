package com.joker.security_test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName TestBC
 * @Description:
 * @Author JOKER
 * @Date: 2019/6/4 17:26
 * @Version
 */
public class TestBC {



    public static void main(String[] args){

        String pass = "123456";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(pass.trim());


        System.out.println(encodedPassword);

    }
}
