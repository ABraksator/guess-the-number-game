package braksator.artur.service;

import braksator.artur.entity.Gameplay;
import braksator.artur.entity.User;
import braksator.artur.repository.GameplayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Service

public class GameplayServiceImpl implements GameplayService {

    private GameplayRepository gameplayRepository;

    private GameService gameService;

    private Gameplay gameplay = new Gameplay();

    @Autowired
    public GameplayServiceImpl(GameplayRepository gameplayRepository, GameService gameService) {
        this.gameplayRepository = gameplayRepository;

        this.gameService = gameService;
    }

    public void minNumber() {
        gameplay.setMinNumber(gameService.getGame().getNumberGenerator().getMinNumber());
    }

    @Override
    public void maxNumber() {
        gameplay.setMaxNumber(gameService.getGame().getNumberGenerator().getMaxNumber());
    }

    @Override
    public void numberOfGuesses() {
        int numberOfGuesses = gameplay.getNumberOfGuesses() + 1;
        gameplay.setNumberOfGuesses(numberOfGuesses);
    }


    public boolean isWon() {
        if (gameService.getGame().isGameWon()) {
            return true;
        } else {
            return false;
        }
    }

    public void bindUser(HttpSession session) {
        gameplay.setUser((User) session.getAttribute("user"));
    }

    public void saveGameplay(HttpSession session) {
        if (gameplay.getNumberOfGuesses() != 0) {
            bindUser(session);
            gameplay.setWon(isWon());
            gameplay.setWantedNumber(gameService.getGame().getNumber());
            gameplayRepository.save(gameplay);
        }
        gameplay = new Gameplay();
        gameplay.setNumberOfGuesses(0);

        gameplay.setMinNumber(gameService.getGame().getNumberGenerator().getMinNumber());
        gameplay.setMaxNumber(gameService.getGame().getNumberGenerator().getMaxNumber());
        gameplay.setWon(true);
    }

    @Override
    public List<Gameplay> findAllGameplays() {
        List<Gameplay> findAll = gameplayRepository.findAll();
        return findAll;
    }

    @Override
    public Gameplay findById(Integer id) {
        return gameplayRepository.findById(id).get();
    }
}
