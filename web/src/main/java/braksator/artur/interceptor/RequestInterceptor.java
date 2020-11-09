package braksator.artur.interceptor;

import braksator.artur.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register")) {
            return true;
        }

        log.info("request.getRequestURI() = {}", request.getRequestURI());

        log.info("request.getRequestURI() = {}", request.getRequestURI().startsWith("/user/"));

        if (request.getRequestURI().startsWith("/user/") || request.getRequestURI().startsWith("/play")) {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {

                return true;
            } else {
                FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
                outputFlashMap.put("loginMessage", "Sorry, you have to login before");
                RequestContextUtils.saveOutputFlashMap("/user/login", request, response);
                response.sendRedirect("/user/login");
                return false;
            }
        }
        log.debug("preHandle method called. handler = {}", handler);
        log.debug("URL = {}", request.getRequestURL());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
//        log.debug("postHandle method called. handler = {}", handler);
//        log.debug("URL = {}", request.getRequestURL());
////        log.debug("model = {}", modelAndView.getModel());
//        log.debug("view = {}", modelAndView.getViewName());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        log.debug("afterCompletion method called. handler = {}", handler);
        log.debug("URL = {}", request.getRequestURL());

    }
}
