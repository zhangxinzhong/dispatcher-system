package com.yunnan.dispatcher.privilege.utils;

import com.yunnan.dispatcher.privilege.model.LoginUserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @description: 获取登录用户 的默认实现
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午5:04
 */
@Component
public class SessionUtils extends LoginUtils<LoginUserInfo> {

    /**
     * 获取登录用户session
     * @return
     */
    @Override
    public Optional<LoginUserInfo> getLogin() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        LoginUserInfo login =  (LoginUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER_INFO);
        if(login == null){
        return Optional.empty();
        }

        return Optional.of(login);
    }
}
