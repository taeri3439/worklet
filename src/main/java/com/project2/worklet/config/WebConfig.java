package com.project2.worklet.config;

import com.project2.worklet.handlerInterceptor.LoginInterceptor;
import com.project2.worklet.util_interceptor.UserAuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration //이 클래스를 스프링의 자바설정파일로 쓴다
@PropertySource("classpath:/application-production.properties") //classpath: 리소스 아래를 가리킨
public class WebConfig implements WebMvcConfigurer {

    //인터셉터 등록
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/user/mypage/**", "/user/resume/**")
                .excludePathPatterns("/user/login", "/user/regist", "/css/**", "/js/**");
    }
}
