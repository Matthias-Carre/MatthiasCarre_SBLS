# Spatially Balanced Latin Square (SBLS) - CP Solver

Ce projet propose une modélisation et une résolution du problème des **Carrés Latins Spatialement Équilibrés (SBLS)** en utilisant la programmation par contraintes avec la bibliothèque **Choco Solver**.

L'objectif est de générer une grille  où chaque élément apparaît une seule fois par ligne et par colonne, tout en garantissant que la somme des distances entre chaque paire de valeurs soit constante afin d'éviter les biais de voisinage (utile notamment en expérimentation agronomique).

## Fonctionnalités

* Modélisation complète du problème SBLS.
* Techniques de **cassage de symétrie** pour optimiser la recherche.
* Comparaison des performances selon les contraintes appliquées (lignes vs colonnes).

## Prérequis
* **Java 22** ou supérieur.
* **Maven** pour la gestion des dépendances.
* **Choco Solver 4.10.14**.

## Installation et Utilisation

1. **Cloner le dépôt :**
```bash
git clone https://github.com/Matthias-Carre/MatthiasCarre_SBLS.git
cd ./MatthiasCarre_SBLS

```


2. **Compiler le projet avec Maven :**
```bash
mvn clean install

```


3. **Exécuter le programme :**
Le `Main.java` lance automatiquement la résolution pour des tailles  allant de 2 à 10.
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"

```


## Structure du Projet

* `src/main/java/org/example/Main.java` : Point d'entrée contenant le modèle Choco et la boucle de test.
* `pom.xml` : Configuration Maven et dépendances (Choco Solver).
* `rapport.pdf` : (Optionnel) Pour une explication détaillée de la modélisation mathématique.

## Résultats de performance

Le solveur affiche les statistiques détaillées (nœuds, backtracks, temps de résolution) grâce à la méthode :

```java
model.getSolver().printStatistics();

```

