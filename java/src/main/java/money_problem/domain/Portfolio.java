package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<Currency, Double> amounts = new HashMap<>();

    public Portfolio() {}

    public void add(double amount, Currency currency) {
        // Implementation for adding an amount in a specific currency
        if (!amounts.containsKey(currency)){
            amounts.put(currency, amount);
        }else{
            amounts.put(currency, amounts.get(currency) + amount);
        }
    }

    public double evaluate(Bank bank, Currency currency) throws MissingExchangeRateException {
        double total = 0;
        for(var amount : amounts.entrySet()) {
            Currency sourceCurrency = amount.getKey();
            double sourceAmount = amounts.get(sourceCurrency);
            total += bank.convert(sourceAmount, sourceCurrency, currency);
        }
        return total;
    }
}
