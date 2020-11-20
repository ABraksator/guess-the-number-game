package braksator.artur.interceptor;

import braksator.artur.entity.User;
import braksator.artur.util.AttributeNames;
import braksator.artur.util.GameMappings;
import braksator.artur.util.LoginMessages;
import braksator.artur.util.UserMappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginMessages loginMessages;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getRequestURI().equals(UserMappings.USER+UserMappings.LOGIN) || request.getRequestURI().equals(UserMappings.USER+UserMappings.REGISTER)) {
            return true;
        }

        if (request.getRequestURI().startsWith(UserMappings.USER) || request.getRequestURI().startsWith(GameMappings.PLAY)) {
            User user = (User) request.getSession().getAttribute(AttributeNames.USER);
            if (user != null) {
                return true;
            } else {
                FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
                outputFlashMap.put(AttributeNames.LOGIN_MESSAGE, loginMessages.user_login_required());
                RequestContextUtils.saveOutputFlashMap(UserMappings.HOME_LOGIN, request, response);
                response.sendRedirect(UserMappings.HOME_LOGIN);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }
}
