package money_problem.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BankTest {

    @Test
    void convert_eur_to_usd_returns_double() throws MissingExchangeRateException {
        // Arrange
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();

        // Act
        Money amount = bank.convert(new Money(10, EUR), USD);

        // Assert
        assertThat(amount).isEqualTo(new Money(12, USD));
    }

    @Test
    void convert_eur_to_eur_returns_same_value() throws MissingExchangeRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Money convert = bank.convert(new Money(10, EUR), EUR);
        assertThat(convert)
                .isEqualTo(new Money(10, EUR));
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() throws MissingExchangeRateException {
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        // assert
        ThrowableAssert.ThrowingCallable action = () -> bank.convert(new Money(10, EUR), KRW);
        assertThatThrownBy(action)
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException {
        // Arrange
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        Bank bank2 = BankBuilder.aEuropeanBank().withExchangeRate(1.3,USD).build();
        // Act
        Money convert = bank.convert(new Money(10,EUR),USD);
        Money convert2 = bank2.convert(new Money(10,EUR),USD);
        // Assert
        assertThat(convert)
                .isEqualTo(new Money(12,USD));
        assertThat(convert2)
                .isEqualTo(new Money(13,USD));
        //assertFalse(convert_negative);
    }

    @Test
    void convert_with_same_currency() throws SameCurrencyException, MissingExchangeRateException {
        // Arrange
        Bank bank = BankBuilder.aEuropeanBank().withExchangeRate(1.2,USD).build();
        // Assert
        ThrowableAssert.ThrowingCallable action = () -> bank.addExchangeRate(EUR, EUR, 0.8);;
        assertThatThrownBy(action)
                .isInstanceOf(SameCurrencyException.class)
                .hasMessage("EUR->EUR");
    }
}