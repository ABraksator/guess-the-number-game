package braksator.artur.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class LoginForm {

    @NotBlank
    private String userName = "Your Name keeepasa";
    @NotBlank
    private String password = "Your Password keeepasa";

    public LoginForm() {}

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
        this.password = password;
    }
}