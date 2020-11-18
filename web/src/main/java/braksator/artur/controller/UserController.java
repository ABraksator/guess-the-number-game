package braksator.artur.controller;

import braksator.artur.entity.Gameplay;
import braksator.artur.entity.User;
import braksator.artur.form.LoginForm;
import braksator.artur.repository.GameplayRepository;
import braksator.artur.repository.UserRepository;
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
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    //no wlasnie juz nie reposotory, tylko service, to on bedzie mial w sobie reposutory. Znaczy zeby zrobić takie rzeczy jak ponizej to trzeba ale
    // w zalezności od kodu to nie
    @Autowired
    private GameplayRepository gameplayRepository ;

    // Created hardcoded User (for demonstrations purpose)
    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        User user = new User("a", BCrypt.hashpw("a", BCrypt.gensalt()), "artur@gmail.com");
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
        gameplay2.setWon(true);
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

    @GetMapping(path = "/login")
    public String login(Model model, @ModelAttribute("loginMessage") String loginMessage) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("loginMessage", loginMessage);
        log.debug(" model.containsAttribute(loginMessage) ={}", model.containsAttribute("loginError"));
        return "login";
    }

    @PostMapping(path = "/login")
    public String processLoginRequest(@Valid LoginForm loginForm, BindingResult result, HttpSession session, RedirectAttributes atts) {

        if (result.hasErrors()) {
            atts.addAttribute("loginMessage", "Fill up the form, please");
            return "redirect:/user/login";
        }
        User user = userRepository.findByUserName(loginForm.getUserName());
        if (user != null && BCrypt.checkpw(loginForm.getPassword(), user.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/play";
        } else {
            atts.addAttribute("loginMessage", "Sorry, Incorrect Login or password");
            return "redirect:/user/login";
        }
    }

    @GetMapping(path = "register")
    public String register(Model model, @ModelAttribute("registerMessage") String message) {
        if (message.length() == 0) {
            model.addAttribute("message", message);
        }
        return "register";
    }

    @PostMapping(path = "register")
    public String register(@Valid User user, BindingResult result, RedirectAttributes atts) {

        if (result.hasErrors()) {
            atts.addAttribute("registerMessage", "Fields are not filled up correctly.");
            return "redirect:/user/register";
        }

        User existingUserByEmail = userRepository.findFirstByEmail(user.getEmail());
        User existingUserByName = userRepository.findByUserName(user.getUserName());
        if (existingUserByEmail != null || existingUserByName != null) {
            atts.addAttribute("registerMessage", "Sorry, this user name or e-mail is already registered.");
            return "redirect:/user/register";
        } else {
            userRepository.save(user);
            atts.addAttribute("loginMessage", "Congratulation, your account has been successfully created. Pleas login");
            return "redirect:/user/login";
        }
    }

    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping(path = "/test")
    public String test(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "redirect:/play";
        }
        return "redirect:/";
    }
}