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

## No exchange rate needed when all currencies aor evaluation are the same

```gherkin
Given a portfolio containing 5 USD
When I evaluate the portfolio to the Bank in USD
Then I should receive the evaluated 5 USD
```

```gherkin
Given a portfolio containing 5 USD
AND 10 USD
When I evaluate the portfolio to the Bank in USD
Then I should receive the evaluated 15 USD
```

## Evaluation = sum of amouts in portfolio converted into target currency

```gherkin
Given a portfolio containing 5 USD
And 10 EUR
And a Bank with exchange rate of 1.2 between EUR and USD
When I evaluate the portfolio to the Bank in USD
Then I should receive the evaluated 17USD
```

```gherkin
Given a portfolio containing 5 USD
And 10 EUR
And a Bank with exchange rate of 1344 between EUR and KRW
And a Bank with exchange rate of 1100 between USD and KRW
When I evaluate the portfolio to the Bank in KRW
Then I should receive the evaluated 18940 KRW
```

## Missing exchange rate => error on evaluation

```gherkin
Given a portfolio containing 5 USD
And 10 EUR
And a Bank with exchange rate of 1.2 between EUR and USD
When I evaluate the portfolio to the Bank in KRW
Then I should receive the evaluated 17USD
Then error: missing exchange rate
```

## Évaluation d'un portefeuille
