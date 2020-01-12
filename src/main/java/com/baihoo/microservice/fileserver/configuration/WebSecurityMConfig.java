/*
package com.baihoo.microservice.fileserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

*/
/**
 * @ClassName WebSecurityMvcConfig
 * @Description TODO
 * @Author baiHoo.chen
 * @Date 2019/7/14 19:45
 *//*

@Configuration
@EnableWebSecurity
public class WebSecurityMConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 配置 一
        httpSecurity
                .requestMatchers()                      // 1. 允许请求配置
                .anyRequest()                           // 1.2 允许任务请求
                .and()
                .authorizeRequests()                    // 2. 许经授权请求配置
                .antMatchers("/api/**")  // 2.1 配置许经授权请求路径
                .permitAll();                           // 2.2. 跳过许经授权认可，放行
        // 配置 二，允许来自同一框架内的请求
        */
/*httpSecurity.
                headers().
                frameOptions().
                sameOrigin();*//*


    }
}
*/
