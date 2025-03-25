# Clean code VS Dirty code

## Qu'est-ce que du code sale ?

- 1. Mauvaise dénomination de variable

- 2. Mauvais découpage de code métier

- 3. non Single responsability

- 4. Duplication de code 

- 5. Accessibilité des variables (tout en privé publique)

- 6. Mauvaise docs / doc imcomplete / doc inexistante pour des parties complexes

- 7. fonction avec une compléxité cyclematique > 5

- 8. Pas de typage (any)

- 9. Pas de validation des inputs

Un marécage où on se perd
Du code spaghetti
Du code sans structure
Du code dupliqué
Un patchwork

## Qu'est-ce que du code propre ?

- 1. Nom explicite, pas de type du meme nom que la variable (Currency : currency1)

- 2. Classe d'interface avec logique métier, utilisation de MVC, 

- 3. Une methode/classe doit avoir une seul résponsabilité

- 4. Réutilisation de fonction déjà existante/ factorisation.

- 5. Accessibilité des variables (getter setter)

- 6. Documentation explicite, pas le meme nom que la classe

- 7. fonction avec une compléxité cyclematique <= 5

- 8. Pas de typage primitif si régle de gestion (typage avec montant impossible d'etre en négatif si pas de négatif)

- 9. Typage des inputs 

- Convertion du language utilisé

Lisible : se lit comme une phrase
Elégant : assez simple et direct
Facile à modifier: peut etre modifié par un autre que son auteur
Ecrit avec soin : Aucun probleme évident ne saute aux yeux
Sans surprise : Le corps des méthodes est ce qu'on avait deviné