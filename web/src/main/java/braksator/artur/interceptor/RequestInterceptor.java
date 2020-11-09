package braksator.artur.interceptor;

import braksator.artur.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
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
//        if(request.getMethod().equals("POST")){
        //dodajć jeste /user/logout -- chociaż nie bo przeciez musisz byc zalogowanym zeby moc sie wylogowac
        //wypisanie wyjatków
                if(request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register")) {
                    return true;
                }

        log.info("request.getRequestURI() = {}", request.getRequestURI());

        log.info("request.getRequestURI() = {}", request.getRequestURI().startsWith("/user/"));

        //wypisanie blacklisty, mozemy dodac kolejne endpoint np request.getRequestURI().startsWith("/play")
        if(request.getRequestURI().startsWith("/user/") || request.getRequestURI().startsWith("/play")){
//            User user = (User) request.getSession(false).getAttribute("user");
            //chyba powinno byc isRequestedSessionIdValid() - w snesie zamiast warunku z nullem
//            log.info("User user = (User) request.getSession(false) = {}", user);
            log.info("User user = (User) request.getSession(false) = {}", request.getSession(false));
            log.info("request.isRequestedSessionIdValid() = {}", request.isRequestedSessionIdValid());

//            if(user != null){
//            if(request.getSession(false) != null){
            User user = (User) request.getSession().getAttribute("user");
            if(user != null){

                return true;
            }else{
//                request.setAttribute("loginMessage", "Sorry, you have to login before");
                FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
                outputFlashMap.put("loginMessage", "Sorry, you have to login before");
                //New utility added in Spring 5
                RequestContextUtils.saveOutputFlashMap("/user/login", request, response);

                response.sendRedirect("/user/login");
//                Model model =
//                Model model.addAttribute("loginMessage", "Sorry, Incorrect Login or password");

//                Rediratts.addAttribute("loginMessage", "Fill up the form, please");
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
