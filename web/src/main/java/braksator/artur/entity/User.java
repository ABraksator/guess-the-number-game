package braksator.artur.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User {

    public User() {
    }


    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String userName;

    @NotEmpty
    @NotBlank
    private String password;

    @NotEmpty
    @Email
    private String email;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name="match_id")
    private List<Gameplay> matches = new ArrayList<Gameplay>();


    public List<Gameplay> getMatches() {
        return matches;
    }

    public void setMatches(List<Gameplay> matches) {
        this.matches = matches;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
//        String hash = encoder().encode(password);
//        this.password = password;

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = hashed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + userName + ", password=" + password
                + ", email=" + email + "]";
    }


}