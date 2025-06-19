package money_problem.domain;

public class InvalidRateException extends Exception {
    public InvalidRateException(double rate) {
        super(String.format("%s Cannot convert", rate));
    }
}
