package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public class BankBuilder {

    private Currency pivotCurrency;
    private Map<Currency, Double> exchangeRates = new HashMap<>();

    public static BankBuilder aBank(){
        return new BankBuilder();
    }

    public BankBuilder withPivotCurrency(Currency pivotCurrency){
        this.pivotCurrency = pivotCurrency;
        return this;
    }

    public BankBuilder withExchangeRate(double rate, Currency currency){
        this.exchangeRates.put(currency, rate);
        return this;
    }

    public Bank build(){
        Currency currency = (Currency)this.exchangeRates.keySet().toArray()[0];
        Bank bank = Bank.withExchangeRate(this.pivotCurrency, currency, this.exchangeRates.get(currency));
        for(Currency cur: this.exchangeRates.keySet()){
            if (cur != currency){
                bank.addExchangeRate(this.pivotCurrency, cur, this.exchangeRates.get(cur));
            }
        }
        return bank;
    }

    public static BankBuilder aEuropeanBank(){
        return BankBuilder.aBank().withPivotCurrency(Currency.EUR);
    }
}
