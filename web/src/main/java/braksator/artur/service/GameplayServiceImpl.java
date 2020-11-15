package braksator.artur.service;

import braksator.artur.Game;
import braksator.artur.entity.Gameplay;
import braksator.artur.entity.User;
import braksator.artur.repository.GameplayRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Slf4j
@Service

public class GameplayServiceImpl implements GameplayService {
//public class GameplayServiceImpl {

    private GameplayRepository gameplayRepository;

    //    private Gameplay gameplay;
    private GameService gameService;

    private Gameplay gameplay = new Gameplay();

    @Autowired
    public GameplayServiceImpl(GameplayRepository gameplayRepository, GameService gameService) {
        this.gameplayRepository = gameplayRepository;

        this.gameService = gameService;
    }

//    public GameplayServiceImpl(){
//        this.gameplay.setNumberOfGuesses(0);
//        this.gameplay.setMinNumber(gameService.getGame().getNumberGenerator().getMinNumber());
//        this.gameplay.setMaxNumber(gameService.getGame().getNumberGenerator().getMaxNumber());
//
//    }

    public void minNumber() {

        log.debug("gameService.getGame().getNumberGenerator().getMinNumber() = {}", gameService.getGame().getNumberGenerator().getMinNumber());

        gameplay.setMinNumber(gameService.getGame().getNumberGenerator().getMinNumber());
//       saveGameplay(gameplay);
    }

    @Override
    public void maxNumber() {
        gameplay.setMaxNumber(gameService.getGame().getNumberGenerator().getMaxNumber());
    }

    @Override
    public void numberOfGuesses() {
//     gameplay.setNumberOfGuesses(gameService.getGame().getGuess());
        log.debug("number of guess= {}", gameService.getGame().getGuess());
        int numberOfGuesses = gameplay.getNumberOfGuesses() + 1;
        gameplay.setNumberOfGuesses(numberOfGuesses);
    }


    public boolean isWon() {
      if(gameService.getGame().isGameWon()){
          return true;
      }else{
          return false;
      }
    }
//    private boolean isGameOver(){
//        return gameService.isGameOver();
//    }

    public void bindUser(HttpSession session){
       gameplay.setUser((User) session.getAttribute("user"));
    }

    public void saveGameplay(HttpSession session) {
        if(gameplay.getNumberOfGuesses()!=0) {
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
}
