package com.joker.security_test.Config;

import com.joker.security_test.Filter.JwtAuthenticationTokenFilter;
import com.joker.security_test.Util.JwtIgnoreUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName SecurityConfigurer
 * @Description:
 * @Author JOKER
 * @Date: 2019/6/2 0:10
 * @Version
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private JwtIgnoreUrl jwtIgnoreUrl;

    @Autowired
    public SecurityConfigurer(UserDetailsService userDetailsService,
                              JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                              JwtIgnoreUrl jwtIgnoreUrl){
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.jwtIgnoreUrl = jwtIgnoreUrl;
    }

    /**
     *  这必须手动注入spring管理，否则之后使用的时候找不到，
     *  报错信息：Field authenticate in com.ywh.security.service.impl.SysUserServiceImpl required a bean of type
     *          'org.springframework.security.authentication.AuthenticationManager' that could not be found.
     * @return AuthenticationManager
     * @throws Exception 异常信息
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers(HttpMethod.POST,"/user/login");
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//测试Spring Security

//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/test/securityTest1").permitAll()
//                .antMatchers(HttpMethod.POST,"/test/securityTest2").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin();

        httpSecurity
                // 暂时禁用csrc否则无法提交
                .csrf().disable()
                // session管理
                .sessionManagement()
                // 我们使用SessionCreationPolicy.STATELESS无状态的Session机制（即Spring不使用HTTPSession），对于所有的请求都做权限校验，
                // 这样Spring Security的拦截器会判断所有请求的Header上有没有”X-Auth-Token”。
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 设置最多一个用户登录，如果第二个用户登陆则第一用户被踢出，并跳转到登陆页面
                .maximumSessions(1).expiredUrl("/login.html");

//        httpSecurity
//                // 开始认证
//                .authorizeRequests()
//                // 对静态文件和登陆页面放行
//                .antMatchers("/user/**","/a/chaeak").permitAll()
//                .antMatchers("/login.html").permitAll()
//                // 其他请求需要认证登陆
//                .anyRequest().authenticated();
        String[] ignoreUrls = jwtIgnoreUrl.getIgnoreUrl().split(",");

        httpSecurity
                .authorizeRequests()
                .antMatchers(ignoreUrls).permitAll()
                .anyRequest().authenticated();



        // 注入我们刚才写好的 jwt过滤器,添加在UsernamePasswordAuthenticationFilter过滤器之前
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 这块是配置跨域请求的
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        // 让Spring security放行所有preflight request
        registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        之前使用的是通过内存管理的方式实现对用户的管理

//        auth
////                .inMemoryAuthentication()
////                .withUser("root")
////                .password("root")
////                .roles("user")
////                .and()
////                .passwordEncoder(CharEncoder.getINSTANCE());

        auth.userDetailsService(this.userDetailsService)
                    .passwordEncoder(passwordEncoder());


    }

    /**
     * 密码加密
     * @return 返回加密后的密码
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 这块是配置跨域请求的
     * @return Cors过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }




}
