# Guia de Contribució per a TravelSync

## Estratègia de Branques

Utilitzem el model de branques següent:

- master: Conté el codi estable i preparat per a producció. Només es fusionen canvis aprovats.
- develop: Branca principal de desenvolupament. Tots els canvis s'han de fusionar aquí abans d'arribar a master.
- feature/{nom-de-la-feature}: Cada nova funcionalitat es desenvolupa en una branca separada creada a partir de develop.

### Com crear una branca
~~~
# Per una nova funcionalitat
git checkout develop
git pull origin develop
git checkout -b feature/{nom-de-la-feature}
~~~

Després de completar el treball, fes un commit i puja la branca:
~~~
git add .
git commit -m "Descripció clara del canvi"
git push origin feature/{nom-de-la-feature}
~~~

## Pull Requests (PR)

Abans de fer un PR:

1. Assegura't que la teva branca està actualitzada amb develop.

2. Revisa que el codi compleix les normes d'estil i passa els tests.

3. Escriu una bona descripció al PR explicant:
    * Quin problema resol.
    * Com es pot provar.
    * Si hi ha algun problema conegut.

Quan el PR estigui llest, es fusionarà a develop.

## Bones Pràctiques
* Escriu commits clars i descriptius.
* Manté el codi net i seguint les convencions del projecte.
* Comenta el codi quan sigui necessari per millorar la comprensió.
* Abans de fer un PR, revisa que no hi hagi errors i que passi totes les proves.

Gràcies per contribuir a TravelSync! 🚀
