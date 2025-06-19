package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private final Map<String, Double> exchangeRates;
    private Currency pivotCurrency;

    private Bank(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    private Bank(Map<String, Double> exchangeRates, Currency pivotCurrency) {
        this.exchangeRates = exchangeRates;
        this.pivotCurrency = pivotCurrency;
    }

    public static Bank withExchangeRate(Currency currencySource, Currency currencyTarget, double rate) throws InvalidRateException{
        var bank = new Bank(new HashMap<>());
        bank.addExchangeRate(currencyTarget, rate);

        return bank;
    }

    public static Bank withExchangeRate(Currency currencySource, Currency currencyTarget, double rate, Currency pivotCurrency) throws InvalidRateException{
        var bank = new Bank(new HashMap<>(), pivotCurrency);
        bank.addExchangeRate(currencyTarget, rate);

        return bank;
    }

    public void addExchangeRate(Currency currencyTarget, double rate) throws SameCurrencyException, InvalidRateException {
        if((pivotCurrency == currencyTarget)){
            throw new SameCurrencyException(pivotCurrency,currencyTarget);
        }
        if(rate <= 0){
            throw new InvalidRateException(rate);
        }
        exchangeRates.put(pivotCurrency + "->" + currencyTarget, rate);
        exchangeRates.put(currencyTarget + "->" + pivotCurrency, 1/rate);
    }

    public double convert(double amount, Currency currencySource, Currency currencyTarget) throws MissingExchangeRateException {
        if (!canConvert(currencySource, currencyTarget)) {
            throw new MissingExchangeRateException(currencySource, currencyTarget);
        }

        if (currencySource == currencyTarget) {
            return amount;
        }

        String directKey = currencySource + "->" + currencyTarget;
        if (exchangeRates.containsKey(directKey)) {
            return amount * exchangeRates.get(directKey);
        }

        String sourceToPivot = currencySource + "->" + pivotCurrency;
        String pivotToTarget = pivotCurrency + "->" + currencyTarget;

        if (currencySource == pivotCurrency && exchangeRates.containsKey(pivotToTarget)) {
            return amount * exchangeRates.get(pivotToTarget);
        }

        if (currencyTarget == pivotCurrency && exchangeRates.containsKey(sourceToPivot)) {
            return amount * exchangeRates.get(sourceToPivot);
        }

        if (exchangeRates.containsKey(sourceToPivot) && exchangeRates.containsKey(pivotToTarget)) {
            double toPivot = amount * exchangeRates.get(sourceToPivot);
            return toPivot * exchangeRates.get(pivotToTarget);
        }

        throw new MissingExchangeRateException(currencySource, currencyTarget);
    }

    private boolean canConvert(Currency currencySource, Currency currencyTarget){
        return (currencySource == currencyTarget
                || ( pivotCurrency == currencySource && exchangeRates.containsKey(pivotCurrency+"->"+currencyTarget)))
                || ( pivotCurrency == currencyTarget && exchangeRates.containsKey(currencySource+"->"+pivotCurrency))
                || ( exchangeRates.containsKey(currencySource+"->"+pivotCurrency) && exchangeRates.containsKey(pivotCurrency+"->"+currencyTarget))
                || exchangeRates.containsKey(currencySource + "->" + currencyTarget);
    }

    public Money convert(Money money, Currency currency) throws MissingExchangeRateException {
        return new Money(this.convert(money.amount(), money.currency(), currency), currency);
    }
}