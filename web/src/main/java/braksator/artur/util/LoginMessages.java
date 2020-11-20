package braksator.artur.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Slf4j
@Component
public class LoginMessages {

    // == constants ==
    public static final String USER_LOGIN_EMPTY = "user.login.empty";

    public static final String USER_LOGIN_INCORRECT = "user.login.incorrect";

    public static final String USER_REGISTRATION_INCORRECT = "user.registration.incorrect";

    public static final String USER_REGISTRATION_EXISTS = "user.registration.exists";

    public static final String USER_REGISTRATION_SUCCESSFUL = "user.registration.successful";






    private final MessageSource messageSource;


    // == constructor ==

    public LoginMessages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String user_login_empty(){
        return getMessage(USER_LOGIN_EMPTY);
    }

    public String user_login_incorrect() {
        return getMessage(USER_LOGIN_INCORRECT);
    }

    public String user_registration_incorrect() {
        return getMessage(USER_REGISTRATION_INCORRECT);
    }

    public String user_registration_exists() {
        return getMessage(USER_REGISTRATION_EXISTS);
    }

    public String user_registration_successful() {
        return getMessage(USER_REGISTRATION_SUCCESSFUL);
    }



        // == private methods ==
    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
