package com.project2.worklet.util_interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAuthHandler implements HandlerInterceptor {

    //2.메서드 오버라이딩
    //pre - 컨트롤러 진입 전
    //post - 컨트롤러 실행 후
    //aftercompletion - viewresolver 실행 후

    //3.스프링 설정파일에 인터셉터 등록

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //리턴에 true가 들어가면 controller를 실행, false가 들어가면 컨트롤러 실행 안함
        System.out.println("컨트롤러 실행전 인터셉터 동작");
        //세션의 존재 여부를 확인해서 세션이 없으면 로그인페이지로 리다이렉트 보낸다.

        //request에서 세션얻기
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        if(userId == null) {
            //인증되지 않음 = 로그인안됨
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("컨트롤러 실행 후 인터셉터 동작");
    }
}
