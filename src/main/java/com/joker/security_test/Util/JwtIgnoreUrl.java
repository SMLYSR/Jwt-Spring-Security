package com.joker.security_test.Util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 鉴权忽略列表
 * @author JOKER
 */
@Configuration
@ConfigurationProperties(prefix = "ignorelist")
@Data
public class JwtIgnoreUrl {

    private String ignoreUrl;

}
