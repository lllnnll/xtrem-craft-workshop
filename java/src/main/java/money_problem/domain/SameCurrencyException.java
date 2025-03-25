package money_problem.domain;

public class SameCurrencyException extends RuntimeException {
    public SameCurrencyException(Currency currency1, Currency currency2) {
        super(String.format("%s->%s", currency1, currency2));
    }
}
