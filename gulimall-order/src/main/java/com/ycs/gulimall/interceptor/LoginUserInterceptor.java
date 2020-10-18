package com.ycs.gulimall.interceptor;

import com.ycs.gulimall.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.ycs.gulimall.constant.AuthConstant.LOGIN_USER;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    //使用此线程保存用户信息,接下来该线程访问的所有方法都可以从中获取用户信息
    public static ThreadLocal<MemberResponseVo> loginUser = new ThreadLocal<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/order/order/status/**", uri);
        boolean match1 = antPathMatcher.match("/payed/notify", uri);
        if (match || match1) {
            return true;
        }

        //获取登录的用户信息
        MemberResponseVo memberResponseVo = (MemberResponseVo) request.getSession().getAttribute(LOGIN_USER);

        if (memberResponseVo != null) {
            //把登录后用户的信息放在ThreadLocal里面进行保存
            loginUser.set(memberResponseVo);
            return true;
        } else {
            //未登录，返回登录页面
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>location.href='http://www.auth.gulimall.com/login.html'</script>");
            // session.setAttribute("msg", "请先进行登录");
            // response.sendRedirect("http://www.auth.gulimall.com/login.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
