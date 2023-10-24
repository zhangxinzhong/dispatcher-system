package com.yunnan.dispatcher.privilege.utils;

import com.yunnan.dispatcher.privilege.model.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * @description: oauth 获取登录用户信息
 * @author: zhangxinzhong
 * @date: 2019-09-23 上午10:47
 */
@Slf4j
@Component
public class OauthUtils extends LoginUtils<LoginUserInfo> {

    @Override
    public Optional<LoginUserInfo> getLogin() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) request.getUserPrincipal();
//        log.info("oAuth2Authentication：{}", oAuth2Authentication);
//        LoginUserInfo loginCustomer = (LoginUserInfo) oAuth2Authentication.getPrincipal();
//        log.info("login Customer info：{}", loginCustomer);
        return Optional.of(null);

    }
}
