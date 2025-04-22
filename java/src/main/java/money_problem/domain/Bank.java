package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private final Map<String, Double> exchangeRates;

    private Bank(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static Bank withExchangeRate(Currency currencySource, Currency currencyTarget, double rate) {
        var bank = new Bank(new HashMap<>());
        bank.addExchangeRate(currencySource, currencyTarget, rate);

        return bank;
    }

    public void addExchangeRate(Currency currencySource, Currency currencyTarget, double rate) throws SameCurrencyException {
        if((currencySource == currencyTarget)){
            throw new SameCurrencyException(currencySource,currencyTarget);
        }
        exchangeRates.put(currencySource + "->" + currencyTarget, rate);
    }

    public double convert(double amount, Currency currencySource, Currency currencyTarget) throws MissingExchangeRateException {
        if (!canConvert(currencySource, currencyTarget)) {
            throw new MissingExchangeRateException(currencySource, currencyTarget);
        }
        return currencySource == currencyTarget
                ? amount
                : amount * exchangeRates.get(currencySource + "->" + currencyTarget);
    }

    private boolean canConvert(Currency currencySource, Currency currencyTarget){
        return (currencySource == currencyTarget || exchangeRates.containsKey(currencySource + "->" + currencyTarget));
    }

}