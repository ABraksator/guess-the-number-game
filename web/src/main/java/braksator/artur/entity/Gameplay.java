package braksator.artur.entity;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;


//@Id
//@GeneratedValue
//private Integer id;
//private int minNumber;
//private int maxNumber;
////    private boolean isWon;
//private int numberOfGuesses;
@Entity
@Slf4j
public class Gameplay {

    @Id
    @GeneratedValue
    private Integer id;
    private int minNumber;
    private int maxNumber;
    private int numberOfGuesses;
    @Column(nullable = false)
    private boolean isWon = false;
    private int wantedNumber;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public void setNumberOfGuesses(int numberOfGuesses) {
        this.numberOfGuesses = numberOfGuesses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public int getWantedNumber() {
        return wantedNumber;
    }

    public void setWantedNumber(int wantedNumber) {
        this.wantedNumber = wantedNumber;
    }

    @Override
    public String toString() {
        return "Gameplay{" +
                "id=" + id +
                ", minNumber=" + minNumber +
                ", maxNumber=" + maxNumber +
                ", numberOfGuesses=" + numberOfGuesses +
                ", isWon=" + isWon +
                ", wantedNumber=" + wantedNumber +
                ", user=" + user +
                '}';
    }
}
