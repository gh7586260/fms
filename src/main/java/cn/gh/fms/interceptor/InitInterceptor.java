package cn.gh.fms.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 郭宏
 * @date on 2018-11-18.
 */
public class InitInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InitInterceptor.class);

    @Value("${fms.page.file.path}")
    private String filePath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //2.初始化css、js、images文件路径
        Object filePathObj = session.getAttribute("filePath");
        if (filePathObj == null) {
            session.setAttribute("filePath", filePath);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //--------------处理请求完成后视图渲染之前的处理操作---------------
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //---------------视图渲染之后的操作-------------------------
    }
}
