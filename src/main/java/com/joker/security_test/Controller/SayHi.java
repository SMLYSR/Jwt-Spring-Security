package com.joker.security_test.Controller;

import com.joker.security_test.Util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试权限验证Controller
 * @author JOKER
 */
@Slf4j
@RestController
@RequestMapping("hi")
public class SayHi {

    @GetMapping("sayHello")
    public Result sayHello(){
        log.info("该接口已保护！！！");
        return Result.successJson("hello~~~");
    }

    @GetMapping("sayHi")
    public Result sayHi(){
        log.info("该接口未保护！！！");
        return Result.successJson("hi~~~");
    }

}
