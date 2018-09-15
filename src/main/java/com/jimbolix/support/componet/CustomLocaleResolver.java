package com.jimbolix.support.componet;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.support.componet
 * @Description: 自定义国际化转换器
 * @date 2018/9/13
 */
public class CustomLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = Locale.getDefault();
        String languge = request.getParameter("languge");
        if(!StringUtils.isEmpty(languge)){
            locale = new Locale(languge.split("-")[0],languge.split("-")[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
