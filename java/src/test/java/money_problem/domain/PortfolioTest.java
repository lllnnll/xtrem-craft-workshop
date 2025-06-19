package money_problem.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PortfolioTest {

    /*
    Given a portfolio containing 5 USD
    When I evaluate the portfolio to the Bank in USD
    Then I should receive the evaluated 5 USD
    */
    @Test
    void test_evaluate_portfolio() throws MissingExchangeRateException, InvalidRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(5, USD));
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2, USD).build();
        Money value = portfolio.evaluate(bank, USD);
        assertThat(value.amount())
                .isEqualTo(5);
    }

    /*
    Given a portfolio containing 5 USD
    AND 10 USD
    When I evaluate the portfolio to the Bank in USD
    Then I should receive the evaluated 15 USD
    */
    @Test
    void test_evaluate_portfolio_with_multiple_usd() throws MissingExchangeRateException, InvalidRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(5,USD));
        portfolio.add(new Money(10, USD));
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money value = portfolio.evaluate(bank, USD);
        assertThat(value.amount())
                .isEqualTo(15);
    }
    /*
    Given a portfolio containing 5 USD
    And 10 EUR
    And a Bank with exchange rate of 1.2 between EUR and USD
    When I evaluate the portfolio to the Bank in USD
    Then I should receive the evaluated 17USD
     */
    @Test
    void test_evaluate_portfolio_with_multiple_currencies() throws MissingExchangeRateException, InvalidRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(5, USD));
        portfolio.add(new Money(10, EUR));
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money value = portfolio.evaluate(bank, USD);
        assertThat(value.amount())
                .isEqualTo(17);
    }
    /*
    Given a portfolio containing 5 USD
    And 10 EUR
    And a Bank with exchange rate of 1344 between EUR and KRW
    And a Bank with exchange rate of 1100 between USD and KRW
    When I evaluate the portfolio to the Bank in KRW
        Then I should receive the evaluated 18940 KRW
     */
    @Test
    void test_evaluate_portfolio_with_multiple_currencies_and_different_exchange_rates() throws MissingExchangeRateException, InvalidRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(5, USD));
        portfolio.add(new Money(10, EUR));
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1344,KRW).withExchangeRate(1.2,USD).build();
        Money value = portfolio.evaluate(bank, KRW);
        assertThat(value.amount())
                .isEqualTo(19040);
    }
    /*
    Given a portfolio containing 5 USD
    And 10 EUR
    And a Bank with exchange rate of 1.2 between EUR and USD
    When I evaluate the portfolio to the Bank in KRW
    Then I should receive the evaluated 17USD
    Then error: missing exchange rate
     */
    @Test
    void test_evaluate_portfolio_with_missing_exchange_rate() throws InvalidRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(5, EUR));
        portfolio.add(new Money(10, EUR));
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        // assert
        ThrowableAssert.ThrowingCallable action = () -> portfolio.evaluate(bank, KRW);
        assertThatThrownBy(action)
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    void test_evaluate() throws MissingExchangeRateException, InvalidRateException{
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(5.6, EUR));
        portfolio.add(new Money(10, USD));
        portfolio.add(new Money(5.42, USD));
        portfolio.add(new Money(1200, KRW));
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).withExchangeRate(1344,KRW).build();
        Money value = portfolio.evaluate(bank, EUR);
        assertThat(value.amount())
                .isEqualTo(19.34);
    }
}
