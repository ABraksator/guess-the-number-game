package braksator.artur.controller;

import braksator.artur.entity.User;
import braksator.artur.form.LoginForm;
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

    // Created hardcoded User (for demonstrations purpose)
    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        User user = new User("a", BCrypt.hashpw("a", BCrypt.gensalt()), "artur@gmail.com");
        userRepository.save(user);
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