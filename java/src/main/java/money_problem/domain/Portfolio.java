package money_problem.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<Currency, Double> amounts = new HashMap<>();
    private ArrayList<Money> moneys = new ArrayList<>();

    public Portfolio() {}

    public void add(double amount, Currency currency) {
        // Implementation for adding an amount in a specific currency
        if (!amounts.containsKey(currency)){
            amounts.put(currency, amount);
        }else{
            amounts.put(currency, amounts.get(currency) + amount);
        }
        moneys.add(new Money(amount, currency));
    }

    public double old_evaluate(Bank bank, Currency currency) throws MissingExchangeRateException {
        double total = 0;
        for(var amount : amounts.entrySet()) {
            Currency sourceCurrency = amount.getKey();
            double sourceAmount = amounts.get(sourceCurrency);
            Money sourceMoney = new Money(sourceAmount, sourceCurrency);
            total += bank.convert(sourceMoney, currency).amount();
        }
        return total;
    }

    public double evaluate(Bank bank, Currency currency) throws MissingExchangeRateException {
        Money total = new Money(0, currency);
        for(Money money: moneys) {
            total = new Money(bank.convert(money, currency).amount() + total.amount(), currency);
        }
        return total.amount();
    }

    public void add(Money money) {
        add(money.amount(), money.currency());
    }
}
