package braksator.artur.controller;

import braksator.artur.service.GameService;
import braksator.artur.util.AttributeNames;
import braksator.artur.util.GameMappings;
import braksator.artur.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
public class GameController {

    // == fields ==
    private final GameService gameService;

    // == constructor ==
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // == request methods ==
    @GetMapping(GameMappings.PLAY)
    public String play(Model model) {
        model.addAttribute(AttributeNames.MAIN_MESSAGE, gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());
        log.info("model= {}", model);

        if (gameService.isGameOver()) {
            return ViewNames.GAME_OVER;
        }

        return ViewNames.PLAY;
    }

    @PostMapping(GameMappings.PLAY)
    public String processMessage(@RequestParam Optional<Integer> guess) {
        if (guess.isPresent()) {
            log.info("guess= {}", guess.get());
            gameService.checkGuess(guess.get());
        }
        return GameMappings.REDIRECT_PLAY;
    }

    @PostMapping("/play/changeRange")
    public String changeRange(@RequestParam Optional<Integer> minNumber, @RequestParam Optional<Integer> maxNumber) {
//    public String changeRange(@RequestParam int minNumber) {
        if (minNumber.isPresent()) {
            gameService.getGame().getNumberGenerator().setMinNumber(minNumber.get());
        }
        if (maxNumber.isPresent()) {
            gameService.getGame().getNumberGenerator().setMaxNumber(maxNumber.get());
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
}
