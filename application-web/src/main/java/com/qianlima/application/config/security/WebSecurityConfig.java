package com.qianlima.application.config.security;

import com.qianlima.user.api.service.UserLoginUniqueKeyService;
import com.qianlima.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZHangYJ
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String rememberMeKey = "qianlima";
        http.cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(getPermitAllMatchers()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("http://center.qianlima.com/login.jsp");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
//        System.out.println(corsDomainList);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://www.qianlima.com",
                "http://center.qianlima.com", "http://wap.qianlima.com",
                "http://detail.vip.qianlima.com","http://vip-dev.qianlima.com","http://vip.qianlima.com", "http://search.vip.qianlima.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "content-disposition", "X-Auth-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/database/**", configuration);
        source.registerCorsConfiguration("/publish/**", configuration);
        source.registerCorsConfiguration("/company/**", configuration);
        source.registerCorsConfiguration("/project/**", configuration);
        return source;
    }

    private String[] getPermitAllMatchers() {
        return new String[]{
                "/swagger-ui.html",
                "swagger-ui.html",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/testController/**",
                "/database/**",
                "/publish/**",
                "/company/**",
                "/**"
        };
    }
}
