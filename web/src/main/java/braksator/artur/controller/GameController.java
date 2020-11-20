package braksator.artur.controller;

import braksator.artur.entity.Gameplay;
import braksator.artur.entity.User;
import braksator.artur.service.GameService;
import braksator.artur.service.GameplayService;
import braksator.artur.util.AttributeNames;
import braksator.artur.util.GameMappings;
import braksator.artur.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class GameController {

    // == fields ==
    private final GameService gameService;

    private final GameplayService gameplayService;

    // == constructor ==
    @Autowired
    public GameController(GameService gameService, GameplayService gameplayService) {
        this.gameService = gameService;
        this.gameplayService = gameplayService;
    }

    // == request methods ==
    @GetMapping(GameMappings.PLAY)
    public String play(Model model, HttpSession session) {
        model.addAttribute(AttributeNames.MAIN_MESSAGE, gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());
        if (gameService.isGameOver()) {
            gameplayService.saveGameplay(session);
            return ViewNames.GAME_OVER;
        }
        return ViewNames.PLAY;
    }

    @PostMapping(GameMappings.PLAY)
    public String processMessage(@RequestParam Optional<Integer> guess) {
        if (guess.isPresent()) {
            gameService.checkGuess(guess.get());
            gameplayService.numberOfGuesses();
        }
        return GameMappings.REDIRECT_PLAY;
    }

    @PostMapping(GameMappings.CHANGE_RANGE)
    public String changeRange(@RequestParam Optional<Integer> minNumber, @RequestParam Optional<Integer> maxNumber) {
        if (minNumber.isPresent()) {
            gameService.getGame().getNumberGenerator().setMinNumber(minNumber.get());
            gameplayService.minNumber();
        }
        if (maxNumber.isPresent()) {
            gameService.getGame().getNumberGenerator().setMaxNumber(maxNumber.get());
            gameplayService.maxNumber();
        }
        if (minNumber.isPresent() || maxNumber.isPresent()) {
            gameService.reset();
        }
        return GameMappings.REDIRECT_PLAY;
    }

    @GetMapping(GameMappings.RESTART)
    public String restart() {
        gameService.reset();
        return GameMappings.REDIRECT_PLAY;
    }


    @GetMapping(GameMappings.GAMEPLAYS)
    public String allGameplay(Model model, HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("user");
        List<Gameplay> gameplaysList = gameplayService.findAllGameplays();
        gameplaysList.removeIf(o -> o.getUser().getId() != user.getId());
        model.addAttribute("gameplays", gameplaysList);

        return ViewNames.GAMEPLAYS;
    }

    @GetMapping(GameMappings.GAMEPLAY_DETAILS)
    public String showGameplay(@PathVariable("id") int id, Model model, HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("user");
        Gameplay gameplayDetail = gameplayService.findById(id);

        if (gameplayDetail.getUser().getId() == user.getId()) {
            model.addAttribute("gameplay", gameplayDetail);
        }

        return ViewNames.GAMEPLAY_DETAILS;
    }
}
