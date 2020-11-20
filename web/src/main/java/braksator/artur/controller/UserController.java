package braksator.artur.controller;

import braksator.artur.entity.Gameplay;
import braksator.artur.entity.User;
import braksator.artur.form.LoginForm;
import braksator.artur.repository.GameplayRepository;
import braksator.artur.repository.UserRepository;
import braksator.artur.util.GameMappings;
import braksator.artur.util.LoginMessages;
import braksator.artur.util.UserMappings;
import braksator.artur.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(path = UserMappings.USER)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameplayRepository gameplayRepository ;

    @Autowired
    private LoginMessages loginMessages;

    // Created hardcoded User (for demonstrations purpose)
    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        User user = new User("User", BCrypt.hashpw("User", BCrypt.gensalt()), "user@mail.com");
        userRepository.save(user);
        Gameplay gameplay = new Gameplay();
        gameplay.setWon(true);
        gameplay.setMaxNumber(100);
        gameplay.setMinNumber(0);
        gameplay.setNumberOfGuesses(5);
        gameplay.setUser(user);
        gameplay.setWantedNumber(50);
        gameplayRepository.save(gameplay);
        Gameplay gameplay2 = new Gameplay();
        gameplay2.setWon(false);
        gameplay2.setMaxNumber(80);
        gameplay2.setMinNumber(20);
        gameplay2.setNumberOfGuesses(8);
        gameplay2.setUser(user);
        gameplay2.setWantedNumber(40);
        gameplayRepository.save(gameplay2);

        Gameplay gameplay3 = new Gameplay();
        gameplay3.setWon(true);
        gameplay3.setMaxNumber(65);
        gameplay3.setMinNumber(30);
        gameplay3.setNumberOfGuesses(8);
        gameplay3.setUser(user);
        gameplay3.setWantedNumber(60);
        gameplayRepository.save(gameplay3);
    }

    @GetMapping(path = UserMappings.LOGIN)
    public String login(Model model, @ModelAttribute("loginMessage") String loginMessage) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("loginMessage", loginMessage);
        return ViewNames.LOGIN;
    }

    @PostMapping(path = UserMappings.LOGIN)
    public String processLoginRequest(@Valid LoginForm loginForm, BindingResult result, HttpSession session, RedirectAttributes atts) {

        if (result.hasErrors()) {
            atts.addAttribute("loginMessage", loginMessages.user_login_empty());
            return UserMappings.REDIRECT_LOGIN;
        }
        User user = userRepository.findByUserName(loginForm.getUserName());
        if (user != null && BCrypt.checkpw(loginForm.getPassword(), user.getPassword())) {
            session.setAttribute("user", user);
            return GameMappings.REDIRECT_PLAY;
        } else {
            atts.addAttribute("loginMessage", loginMessages.user_login_incorrect());
            return UserMappings.REDIRECT_LOGIN;
        }
    }

    @GetMapping(path = UserMappings.REGISTER)
    public String register(Model model, @ModelAttribute("registerMessage") String message) {
        if (message.length() == 0) {
            model.addAttribute("message", message);
        }
        return ViewNames.REGISTER;
    }

    @PostMapping(path = UserMappings.REGISTER)
    public String register(@Valid User user, BindingResult result, RedirectAttributes atts) {

        if (result.hasErrors()) {
            atts.addAttribute("registerMessage", loginMessages.user_registration_incorrect());
            return UserMappings.REDIRECT_REGISTER;
        }

        User existingUserByEmail = userRepository.findFirstByEmail(user.getEmail());
        User existingUserByName = userRepository.findByUserName(user.getUserName());
        if (existingUserByEmail != null || existingUserByName != null) {
            atts.addAttribute("registerMessage", loginMessages.user_registration_exists());
            return UserMappings.REDIRECT_REGISTER;
        } else {
            userRepository.save(user);
            atts.addAttribute("loginMessage", loginMessages.user_registration_successful());
            return UserMappings.REDIRECT_LOGIN;
        }
    }

    @GetMapping(path = UserMappings.LOGOUT)
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return GameMappings.REDIRECT_HOME;
    }
}