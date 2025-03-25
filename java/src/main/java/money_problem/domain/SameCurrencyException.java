package money_problem.domain;

public class SameCurrencyException extends RuntimeException {
  public SameCurrencyException(String message) {
    super(message);
  }
}
