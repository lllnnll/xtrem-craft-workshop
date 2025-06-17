package money_problem.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private ArrayList<Money> moneys = new ArrayList<>();

    public Portfolio() {}

    public Money evaluate(Bank bank, Currency currency) throws MissingExchangeRateException {
        Money total = new Money(0, currency);
        for(Money money: moneys) {
            total = new Money(bank.convert(money, currency).amount() + total.amount(), currency);
        }
        return total;
    }

    public void add(Money money) {
        moneys.add(money);
    }
}
