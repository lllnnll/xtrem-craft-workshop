package money_problem.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BankTest {

    /*
    Given a Bank with exchange rate of 1.2 from EUR to USD
    When I convert 10 EUR to USD
    Then I should receive 12 USD
     */
    @Test
    void convert_eur_to_usd_returns_double() throws MissingExchangeRateException, InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money amount = bank.convert(new Money(10, EUR), USD);
        assertThat(amount).isEqualTo(new Money(12, USD));
    }

    /*
    Given a Bank with exchange rate of 1.2 from EUR to USD
    When I convert 10 EUR to EUR
    Then I should receive 10 EUR (same value, no conversion)
     */
    @Test
    void convert_eur_to_eur_returns_same_value() throws MissingExchangeRateException, InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money convert = bank.convert(new Money(10, EUR), EUR);
        assertThat(convert).isEqualTo(new Money(10, EUR));
    }

    /*
    Given a Bank with exchange rate of 1.2 from EUR to USD
    When I convert 10 EUR to KRW
    Then I should receive an exception for missing exchange rate "EUR->KRW"
     */
    @Test
    void convert_throws_exception_on_missing_exchange_rate() throws InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        ThrowableAssert.ThrowingCallable action = () -> bank.convert(new Money(10, EUR), KRW);
        assertThatThrownBy(action)
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    /*
    Given two Banks with different exchange rates from EUR to USD (1.2 and 1.3)
    When I convert 10 EUR in each Bank
    Then I should receive different results: 12 USD and 13 USD
     */
    @Test
    void convert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException, InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Bank bank2 = BankBuilder.aEuropeanBank().withExchangeRate(1.3,USD).build();
        Money convert = bank.convert(new Money(10,EUR),USD);
        Money convert2 = bank2.convert(new Money(10,EUR),USD);
        assertThat(convert).isEqualTo(new Money(12,USD));
        assertThat(convert2).isEqualTo(new Money(13,USD));
    }

    /*
    Given a Bank
    When I add an exchange rate from EUR to EUR
    Then I should receive a SameCurrencyException with message "EUR->EUR"
     */
    @Test
    void convert_with_same_currency() throws SameCurrencyException, InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        ThrowableAssert.ThrowingCallable action = () -> bank.addExchangeRate(EUR, 0.8);
        assertThatThrownBy(action)
                .isInstanceOf(SameCurrencyException.class)
                .hasMessage("EUR->EUR");
    }

    /*
    Given a Bank without any exchange rate
    When I convert 10 EUR to EUR
    Then I should receive 10 EUR
     */
    @Test
    void convert_to_same_currency_without_exchangeRate() throws MissingExchangeRateException, InvalidRateException{
        Bank bank = BankBuilder.aEuropeanBank().build();
        Money convert = bank.convert(new Money(10, EUR), EUR);
        assertThat(convert).isEqualTo(new Money(10, EUR));
    }

    /*
    Given a Bank with exchange rate of 1.2 from EUR to USD
    When I convert 10 EUR to USD
    Then I should receive 12 USD
     */
    @Test
    void convert_currency_to_exchange_rate() throws MissingExchangeRateException, InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money convert = bank.convert(new Money(10, EUR),USD);
        assertThat(convert).isEqualTo(new Money(12, USD));
    }

    /*
    Given a Bank with exchange rate of 1.2 from EUR to USD
    When I convert 10 EUR to USD
        And then back to EUR
    Then I should receive 10 EUR
     */
    @Test
    void convert_to_pivot_and_back() throws MissingExchangeRateException, InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money convert = bank.convert(new Money(10, EUR),USD);
        Money back = bank.convert(convert, EUR);
        assertThat(back).isEqualTo(new Money(10, EUR));
    }

    /*
    Given a Bank with exchange rate of 1.2 from EUR to USD
    When I add an exchange rate of 0 from KRW
    Then I should receive an InvalidRateException with message "0.0 Cannot convert"
     */
    @Test
    void convert_to_rate_with_zero() throws InvalidRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        ThrowableAssert.ThrowingCallable action = () -> bank.addExchangeRate(KRW, 0);
        assertThatThrownBy(action)
                .isInstanceOf(InvalidRateException.class)
                .hasMessage("0.0 Cannot convert");
    }
 @Test
    void convert_to_pivot() throws MissingExchangeRateException, InvalidRateException {
        // Arrange
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        // Act
        Money convert = bank.convert(new Money(12, USD), EUR);
        // Assert
        assertThat(convert)
                .isEqualTo(new Money(10, EUR));
    }


}