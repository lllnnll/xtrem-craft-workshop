# Example Mapping

## Format de restitution

_(rappel, pour chaque US)_

```markdown
## Titre de l'US (post-it jaunes)

> Question (post-it rouge)

### Règle Métier (post-it bleu)

Exemple: (post-it vert)

- [ ] 5 USD + 10 EUR = 17 USD
```

Vous pouvez également joindre une photo du résultat obtenu en utilisant les post-its.

## Story 1: Define Pivot Currency

```gherkin
As a Foreign Exchange Expert
I want to be able to define a Pivot Currency
So that I can express exchange rates based on it
```

## Story 2: Add an exchange rate

```gherkin
As a Foreign Exchange Expert
I want to add/update exchange rates by specifying: a multiplier rate and a currency
So they can be used to evaluate client portfolios
```

## Story 3: Convert a Money

```gherkin
As a Bank Consumer
I want to convert a given amount in currency into another currency
So it can be used to evaluate client portfolios
```

### Bank converts to the same currency without exchange rate

```gherkin
Given a bank with pivot currency EUR
    And 10 EUR
When I convert to EUR
Then I get 10 EUR
```

### Bank converts his pivot currency to the exchange rate by multiplying

```gherkin
Given a bank with pivot currency EUR
    And exhange rate to USD equals to 1.2
    And 10 EUR
When I convert my EUR to USD
Then I get 12 USD
```

### Bank converts the exchange rate to his pivot currency by dividing

```gherkin
Given a bank with pivot currency EUR
    And exhange rate to USD equals to 1.2
    And 12 USD
When I convert my USD to EUR
Then I get 10 EUR
```

### Bank converts the exchange rate to his pivot currency then back to his exchange rate

```gherkin
Given a bank with pivot currency EUR
    And exhange rate to USD equals to 1.2
    And 10 EUR
When I convert my EUR to USD
    AND I convert the result to EUR
Then I get 10 EUR
```

### Bank converts the exchange rate but the client give less than the avergae amount

```gherkin
Given a bank with pivot currency EUR
    And exhange rate to KRW equals to 1344
    And a average amount of 1% of exchange rate that is 13,44 KRW
    And 13,43 KRW
When I convert my KRW to EUR
Then I get Exception
```

### Bank converts the exchangre rate but the client give more or equals than the avergae amount

```gherkin
Given a bank with pivot currency EUR
    And exhange rate to KRW equals to 1344
    And a average amount of 1% of exchange rate that is 13,44 KRW
    And 13,45 KRW
When I convert my KRW to EUR
Then I get 0,01
```

### Bank converts the exchange rate to his pivot currency and round the result to the inferior number

```gherkin
Given a bank with pivot currency KRW
    And exhange rate to EUR equals to 0.00073
    And 1248 KRW
When I convert my KRW to EUR
Then I get 0,91 EUR rounded of 0,91104 EUR
```

### Bank converts the exchange rate to his exchange rate directly

```gherkin
Given a bank with pivot currency KRW
    And exhange rate to EUR equals to 0,00073
    And 1248 KRW
When I convert my KRW to EUR
    And I convert my EUR to KRW
    And I take the original amount
Then I get 1248 KRW
```

### Bank does not convert the exchange rate to pivot currency not in the bank

```gherkin
Given a bank with pivot currency USD
    And exhange rate to EUR equals to 0.82
    And 10 USD
When I convert my USD to KRW
Then I get Exception
```
