package cn.gh.fms.controller;

import cn.gh.fms.BO.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    //cookie有效时长，1年
    private final static int maxAge = 12 * 30 * 24 * 60 * 60;
    private final static String CUR_USER = "loginUser";

    public Long getLoginUser() {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(CUR_USER)) {
            Cookie cookie = cookieMap.get(CUR_USER);
            try {
                return Long.parseLong(URLDecoder.decode(cookie.getValue(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 更新cookie中的当前用户，没有就添加，有就更新
     *
     * @param user
     */
    public void updateUser(User user) {
        try {
            if (user == null) {
                //登出，删除cookie用户
                Cookie cookie = new Cookie(CUR_USER, null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return;
            }
            String cookieValue = URLEncoder.encode(String.valueOf(user.getUserId()), "utf-8");
            Map<String, Cookie> cookieMap = ReadCookieMap(this.request);
            Cookie userCookie = null;
            if (cookieMap.containsKey(CUR_USER)) {
                userCookie = cookieMap.get(CUR_USER);
                userCookie.setValue(cookieValue);
            } else {
                userCookie = new Cookie(CUR_USER, cookieValue);
            }
            userCookie.setPath("/");
            //设置有效时间
            userCookie.setMaxAge(maxAge);
            response.addCookie(userCookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
