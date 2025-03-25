package money_problem.domain;

/**
 *  MoneyCalculator, all of the mathematical operations
 */
public class MoneyCalculator {
    public static double add(double amount, Currency currency, double amount2) {
        return amount + amount2;
    }

    /**
     * Function for the arithmetic operation of multiplication on money $
     * @param amount base amount to calculate
     * @param currency selected currency (EUR/USD)
     * @param value the multiplication factor
     * @return the result of the operation
     */
    public static double times(double amount, Currency currency, int value) {
        return amount * value;
    }

    public static double divide(double amount, Currency currency, int value) {
        return amount / value;
    }
}