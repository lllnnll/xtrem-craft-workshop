package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoneyCalculatorTest {
    @Test
    void shouldAddInUsd() {
        // Act
        double money = MoneyCalculator.add(5,USD,10);
        // Assert
        assertThat(money)
                .isEqualTo(15);
    }

    @Test
    void shouldMultiplyInEuros() {
        //Act
        double money = MoneyCalculator.times(10, EUR, 2);
        //Assert
        assertThat(money)
                .isEqualTo(20);
    }

    @Test
    void shouldDivideInKoreanWons() {

        //Act
        double money = MoneyCalculator.divide(4002, KRW, 4);
        //Assert
        assertThat(money)
                .isEqualTo(1000.5);
    }
}