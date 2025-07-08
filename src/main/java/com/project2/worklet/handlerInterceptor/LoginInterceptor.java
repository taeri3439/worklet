package com.project2.worklet.handlerInterceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
                                throws Exception {

        HttpSession session = request.getSession(false); // ✅ false로 해서 세션 없으면 새로 만들지 않도록

        System.out.println(">>> preHandle – session: "
                + (session != null ? session.getId() : "null"));
        System.out.println(">>> preHandle – loginUser: "
                + (session != null
                ? session.getAttribute("loginUser")
                : "no session"));

        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }

        return true;
    }
}
