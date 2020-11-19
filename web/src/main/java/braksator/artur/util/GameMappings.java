package braksator.artur.util;

public final class GameMappings {

    // == constants ==
    public static final String PLAY = "/play";
    public static final String REDIRECT_PLAY = "redirect:" + PLAY;
    public static final String RESTART = "restart";
    public static final String HOME = "/";
    public static final String REDIRECT_HOME = "redirect:"+HOME;
    public static final String CHANGE_RANGE = PLAY + "/changeRange";
    public static final String GAMEPLAYS = PLAY + "/gameplays";
    public static final String GAMEPLAY_DETAILS = GAMEPLAYS + "/details/{id}";



    // == constructor ==
    private GameMappings() {}
}
