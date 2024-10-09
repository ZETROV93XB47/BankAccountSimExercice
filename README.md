# 💰 **Bank Account** 💰

# Sujet

Ce kata est un challenge d'[architecture hexagonale](https://fr.wikipedia.org/wiki/Architecture_hexagonale) autour du
domaine de la banque.

> Il a donc volontairement un scope très large.
>
> Dans le premier cas, vous pouvez le réaliser de plusieurs façons
> selon le temps que vous voulez investir dans l'exercice :
>
> - vous avez peu de temps (une soirée) : faites uniquement le code métier, testé et fonctionnel, avec des adapteurs de
    tests.
> - vous avez plus de temps (plusieurs soirées) : le code métier, exposé derrière une api REST, et une persistance
    fonctionnelle ; le tout testé de bout en bout.
> - vous avez beaucoup de temps, et envie d'aller plus loin : la même chose, avec la containerisation de l'application,
    et une pipeline de CI/CD ;p
>
> Vous serez évalués notamment sur les points suivants :
>
> - Tout code livré doit être testé de manière adéquate (cas passants et non passants)
> - Nous serons très vigilants sur le design, la qualité, et la lisibilité du code (et des commits)

### Feature 1 : le compte bancaire

On souhaite proposer une fonctionnalité de compte bancaire.

Ce dernier devra disposer :

- D'un numéro de compte unique (format libre)
- D'un solde
- D'une fonctionnalité de dépôt d'argent
- D'une fonctionnalité de retrait d'argent

La règle métier suivante doit être implémentée :

- Un retrait ne peut pas être effectué s'il représente plus d'argent qu'il n'y en a sur le compte

__

### Feature 2 : le découvert

On souhaite proposer un système de découvert autorisé sur les comptes bancaires.

La règle métier suivante doit être implémentée :

- Si un compte dispose d'une autorisation de découvert, alors un retrait qui serait supérieur au solde du compte est
  autorisé
  si le solde final ne dépasse pas le montant de l'autorisation de découvert

__

### Feature 3 : le livret

On souhaite proposer un livret d'épargne.

Un livret d'épargne est un compte bancaire qui :

- dispose d'un plafond de dêpot : on ne peut déposer d'argent sur ce compte que dans la limite de ce plafond
- ne peut pas avoir d'autorisation de découvert

__

### Feature 4 : le relevé de compte

On souhaite proposer une fonctionnalité de relevé mensuel (sur un mois glissant) des opérations sur le compte

Ce relevé devra faire apparaître :

- Le type de compte (Livret ou Compte Courant)
- Le solde du compte à la date d'émission
- La liste des opérations ayant eu lieu sur le compte, triées par date, dans l'ordre antéchronologique

## Bonne chance !

# 💰 Réalisation 🔥🔥🔥

# 💰 **Bank Account - Hexagonal Architecture** 💰

Ce projet implémente un système de gestion de comptes bancaires, basé sur l'**architecture hexagonale** (ou architecture
des ports et des adaptateurs). Le projet inclut des fonctionnalités de comptes courants et livrets d'épargne, avec
gestion de découvert, dépôt/retrait d'argent, et génération de relevé de compte.

## Table des matières

1. [Fonctionnalités](#fonctionnalités)
2. [Architecture](#architecture)
3. [Prérequis](#prérequis)
4. [Installation](#installation)
5. [Utilisation](#utilisation)

---

## Fonctionnalités

1. **Compte courant** :
    - Numéro de compte unique
    - Solde affiché
    - Dépôts et retraits avec vérification du solde
    - Possibilité de découvert autorisé

2. **Livret d'épargne** :
    - Plafond de dépôt
    - Pas de découvert possible

3. **Relevé de compte** :
    - Type de compte (Livret ou Courant)
    - Solde actuel
    - Historique des transactions, triées par date

## Architecture

Ce projet suit le modèle d'**architecture hexagonale** avec les principaux éléments suivants :

- **Domaine** : Le cœur du système contenant la logique métier (ex : gestion des comptes, transactions, etc.).
- **Ports** : Interfaces représentant les actions possibles sur le domaine.
- **Adaptateurs** :
    - **Entrants** : Contrôleurs REST exposant l'API du système.
    - **Sortants** : Requêtes vers la base de données MySQL via JPA.

### Diagramme de l'architecture

![archi-hexa](./assets/hexa-schema.png)

## Prérequis

- Java 22
- Spring Boot 3.3
- Docker (pour la base de données MySQL)
- Maven (pour la gestion des dépendances)

### Outils recommandés

- **Lombok** (pour la réduction du code boilerplate)
- **JUnit** et **Mockito** (pour les tests unitaires et d'intégration)

## Installation

1. **Clone le dépôt** :

   ```bash
   git clone https://github.com/ZETROV93XB47/BankAccountSimExercice.git
   cd hexagonal-bank-account
   ````
2. **Faire un clean install** :

   ```bash
   mvn clean install -U
   ````
2. **Faire un docker compose up dans le dossier où est contenu le fichier compose.yaml** :
   ```bash
   docker compose up
   ````

3. **Lancer l'application** :
   ```bash
    mvn spring-boot:run
   ````