package braksator.artur.service;

import javax.servlet.http.HttpSession;

public interface GameplayService {
    void minNumber( );
//    int minNumber();
    void  maxNumber();
void numberOfGuesses();
//    boolean isWon();
    void saveGameplay(HttpSession session);

}
