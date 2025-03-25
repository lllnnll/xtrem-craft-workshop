package money_problem.domain;

import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankTest {

    @Test
    void convert_eur_to_usd_returns_double() throws MissingExchangeRateException {
        // Arrange
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

        // Act
        double amount = bank.convert(10, EUR, USD);

        // Assert
        assertThat(amount).isEqualTo(12);
    }

    @Test
    void convert_eur_to_eur_returns_same_value() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        double convert = bank.convert(10, EUR, EUR);
        assertThat(convert)
                .isEqualTo(10);
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() {
        assertThatThrownBy(() -> {
            Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
            double convert = bank.convert(10, EUR, KRW);
        })
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException {
        assertThat(Bank.withExchangeRate(EUR, USD, 1.2).convert(10, EUR, USD))
                .isEqualTo(12);

        assertThat(Bank.withExchangeRate(EUR, USD, 1.3).convert(10, EUR, USD))
                .isEqualTo(13);
    }
}