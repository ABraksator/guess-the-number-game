package braksator.artur.util;

public final class UserMappings {

    // == constants ==
    public static final String USER = "/user";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String HOME_LOGIN= USER+LOGIN;
    public static final String REGISTER =  "/register";
    public static final String REDIRECT_LOGIN = "redirect:" + USER + LOGIN;
    public static final String REDIRECT_REGISTER = "redirect:" + USER + REGISTER;


    // == constructor ==
    private UserMappings() {}
}
