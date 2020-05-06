package com.qianlima.application.config.security;

import com.qianlima.user.api.security.QLMSessionInfo;
import com.qianlima.user.api.service.UserLoginUniqueKeyService;
import com.qianlima.user.api.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * @author ZHangYJ
 */
public class QianlimaRememberMeServices implements RememberMeServices, InitializingBean, LogoutHandler {

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private final String key;
    private final UserService userService;
    private final UserLoginUniqueKeyService userLoginUniqueKeyService;

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "qlm_username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "qlm_password";

    public QianlimaRememberMeServices(String key, UserService userService, UserLoginUniqueKeyService userLoginUniqueKeyService) {
        this.key = key;
        this.userService = userService;
        this.userLoginUniqueKeyService = userLoginUniqueKeyService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(this.key, "key cannot be empty or null");
        // Assert.notNull(this.userDetailsService, "A UserDetailsService is required");
    }

    @Override
    public Authentication autoLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String username = getCookieValue(cookies, SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = getCookieValue(cookies, SPRING_SECURITY_FORM_PASSWORD_KEY);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return null;
        }
        username = username.trim();
//        final String SPRING_SECURITY_FORM_LOGIN_UNIQUE_KEY = username+password;
//        String cookieLoginUniqueValue = getCookieValue(cookies,SPRING_SECURITY_FORM_LOGIN_UNIQUE_KEY);
//        Boolean isLogin = userLoginUniqueKeyService.checkSingleClientLogin(SPRING_SECURITY_FORM_LOGIN_UNIQUE_KEY, cookieLoginUniqueValue);
//        if (!isLogin){
//            return null;
//        }
        /*String redisLoginUniqueValue = userLoginUniqueKeyService.getLoginValue(SPRING_SECURITY_FORM_LOGIN_UNIQUE_KEY);
        if (cookieLoginUniqueValue==null || !cookieLoginUniqueValue.equals(redisLoginUniqueValue)){
            System.out.println("username: check");
            return null;
        }*/
        // check user
        Long id = userService.findIdByUsernameAndPassword(username,password);
        if (id == null) {
            return null;
        }

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("member");
        QLMSessionInfo qianlimaUser = new QLMSessionInfo(id, username, password, Collections.singletonList(grantedAuthority));
        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(this.key, qianlimaUser, this.authoritiesMapper.mapAuthorities(qianlimaUser.getAuthorities()));
        auth.setDetails(this.authenticationDetailsSource.buildDetails(httpServletRequest));
        return auth;
    }

    private String getCookieValue(Cookie[] cookies, String cookieKey){
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieKey)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @Override
    public void loginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String username = this.retrieveUserName(authentication);
        String password = this.retrievePassword(authentication);
        // setCookie(username, password, 10, httpServletResponse);
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

    }

    private void setCookie(String username, String password, int maxAge,HttpServletResponse response) {

        Cookie cookie = new Cookie(SPRING_SECURITY_FORM_USERNAME_KEY, username);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setDomain(".qianlima.com");

        Cookie passwordCookie = new Cookie(SPRING_SECURITY_FORM_PASSWORD_KEY, password);
        passwordCookie.setMaxAge(maxAge);
        passwordCookie.setPath("/");
        passwordCookie.setDomain(".qianlima.com");

        response.addCookie(cookie);
        response.addCookie(passwordCookie);
    }

    protected String retrieveUserName(Authentication authentication) {
        return this.isInstanceOfUserDetails(authentication) ? ((UserDetails)authentication.getPrincipal()).getUsername() : authentication.getPrincipal().toString();
    }

    protected String retrievePassword(Authentication authentication) {
        if (this.isInstanceOfUserDetails(authentication)) {
            return ((UserDetails)authentication.getPrincipal()).getPassword();
        } else {
            return authentication.getCredentials() == null ? null : authentication.getCredentials().toString();
        }
    }

    private boolean isInstanceOfUserDetails(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetails;
    }
}
