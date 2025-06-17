# Example Mapping

## Format de restitution
*(rappel, pour chaque US)*

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

### Bank can convert to the same currency without exchange rate
```gherkin
Given a bank with pivot currency EUR
    And 10 EUR
When I convert to EUR
Then I get 10 EUR
```

### Bank can convert his pivot currency to the exchange rate
```gherkin
Given a bank with pivot currency EUR 
    And exhange rate to USD equals to 1.2 
    And 10 EUR
When I convert my EUR to USD
Then I get 12 USD
```

### Bank can convert the exchange rate to his pivot currency
```gherkin
Given a bank with pivot currency EUR 
    And exhange rate to USD equals to 1.2
    And 12 USD
When I convert my USD to EUR
Then I get 10 USD
```

### Bank can not convert the exchange rate to pivot currency not in the bank 
```gherkin
Given a bank with pivot currency USD 
    And exhange rate to EUR equals to 0.82
    And 10 USD
When I convert my USD to KRW
Then I get Exception
```