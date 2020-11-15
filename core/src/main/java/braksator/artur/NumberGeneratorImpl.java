package braksator.artur;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Random;

@Component
@SessionScope
public class NumberGeneratorImpl implements NumberGenerator {

    // == fields ==
    private final Random random = new Random();

    @Getter
    private int maxNumber;

    @Getter
    private int minNumber;

    // == constructors ==

    @Autowired
    public NumberGeneratorImpl(@MaxNumber int maxNumber, @MinNumber int minNumber) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
    }

    // == public methods ==
    @Override
    public int next() {
        // example:  min=5 max=20 -> max-min=15 -> range 0-15 + min -> 5-20
        return random.nextInt(maxNumber - minNumber) + minNumber;
    }

    @Override
    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    @Override
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
}
