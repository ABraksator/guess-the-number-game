package braksator.artur.service;

import braksator.artur.entity.Gameplay;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface GameplayService {
    void minNumber();

    void maxNumber();

    void numberOfGuesses();

    void saveGameplay(HttpSession session);

    List<Gameplay> findAllGameplays();

    Gameplay findById(Integer id);

}
