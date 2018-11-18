package cn.gh.fms.config;

import cn.gh.fms.interceptor.InitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 郭宏
 * @date on 2018-11-18.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public InitInterceptor getInitInterceptor() {
        return new InitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(getInitInterceptor());
        registration.addPathPatterns("/bill/open/**");
        registration.addPathPatterns("/add/bill");
        registration.addPathPatterns("/edit/bill");
        registration.addPathPatterns("/delete/bill");
        registration.addPathPatterns("/open/month/static");
        registration.addPathPatterns("/open/user/login");
        registration.addPathPatterns("/to/user/info");
        registration.addPathPatterns("/user/login/excute");
        registration.addPathPatterns("/open/user/modify/pwd");
        registration.addPathPatterns("/user/login/out");
    }

}
