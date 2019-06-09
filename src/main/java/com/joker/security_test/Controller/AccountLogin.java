package com.joker.security_test.Controller;

import com.joker.security_test.Enity.SecurityUserDetails;
import com.joker.security_test.Service.UserService;
import com.joker.security_test.Util.Result;
import com.joker.security_test.Util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

/**
 * @ClassName AccountLogin
 * @Description:
 * @Author JOKER
 * @Date: 2019/5/30 21:34
 * @Version
 */
@RestController
@RequestMapping("user")
public class AccountLogin {

    private static final Logger LOG = LoggerFactory.getLogger(AccountLogin.class);

    @Autowired
    private UserService userService;


    @PostMapping("login")
    public Result login(@RequestBody Map<String,String> map){

        try {
            String token = userService.login(map.get("username"),map.get("password"));
            return Result.successJson(token);
        } catch (AuthenticationException e) {
            LOG.error("登录失败",e);
            return Result.errorJson(ResultCode.PASSWORD_ERROR.getMsg(),ResultCode.PASSWORD_ERROR.getIndex());
        }

    }

    @GetMapping("userInfo")
    public Result userInfo(){

        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(authentication instanceof SecurityUserDetails){
            return Result.successJson(userService.findUserInfo(((SecurityUserDetails) authentication).getUsername()));
        }

        return Result.errorJson(ResultCode.LOGIN_AGIN.getMsg(),ResultCode.LOGIN_AGIN.getIndex());
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.successJson("退出成功");
    }

}
