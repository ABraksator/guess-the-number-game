package braksator.artur;

public interface NumberGenerator {

    int next();

    int getMaxNumber();

    int getMinNumber();

    void setMinNumber(int minNumber);

    void setMaxNumber(int maxNumber);

}
