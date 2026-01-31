package org.example;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class Main {
    //fonction pour verifier que les res soit correcte
    public static void printMatrix(IntVar[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%2d ", matrix[i][j].getValue());
            }
            System.out.println();
        }
    }

    //main fonction
    public static void SBLS(int n) {
        Model model = new Model(n + "SBLS");

        // matrice des valeurs
        IntVar[][] matrix = model.intVarMatrix("matrix", n, n, 1, n);

        // la valeur de la somme des distances de chaque paire
        IntVar K = model.intVar("K", n, n * (n - 1));


        // marice de la position dans la ligne pour le calcule des distances
        IntVar[][] posRow = model.intVarMatrix("posRow", n, n, 0, n - 1);
        // marice de la position dans la colone pour le calcule des distances
        IntVar[][] posCol = model.intVarMatrix("posCol", n, n, 0, n - 1);

        //contrainte du carre latin
        for (int i = 0; i < n; i++) {
            model.allDifferent(matrix[i], "AC").post();
            model.inverseChanneling(matrix[i], posRow[i], 1, 0).post();

            IntVar[] colVar = new IntVar[n];
            for (int j = 0; j < n; j++) {
                colVar[j] = matrix[j][i];
            }

            model.allDifferent(colVar, "AC").post();
            model.inverseChanneling(colVar, posCol[i], 1, 0).post();
        }

        // contraintes pour verifier les distances
        for (int a = 1; a <= n; a++) {
            for (int b = a + 1; b <= n; b++) {

                IntVar[] distH = model.intVarArray("distH_" + a + "_" + b, n, 1, n - 1);
                IntVar[] distV = model.intVarArray("distV_" + a + "_" + b, n, 1, n - 1);

                for (int i = 0; i < n; i++) {
                    // distance horisontale
                    model.distance(posRow[i][a - 1], posRow[i][b - 1], "=", distH[i]).post();

                    // distance verticale
                    model.distance(posCol[i][a - 1], posCol[i][b - 1], "=", distV[i]).post();
                }

                // check si les somme des distance sont bien egales
                model.sum(distH, "=", K).post();
                model.sum(distV, "=", K).post();
            }
        }

        // cassage de symetrie pour restrindre les instalce (on force la premier ligne)
        for (int j = 0; j < n; j++) {
            model.arithm(matrix[0][j], "=", j + 1).post();
        }

        // resolution
        int numSolutions = 0;
        while (model.getSolver().solve()) {
            numSolutions++;
            //printmatrice(matrix);
        }

        //pour les infos sur les nodes et temps de resolution
        model.getSolver().printStatistics();
    }

    public static void main(String[] args) {
        //on essaye les differents taille possible
        for (int i = 2; i <= 10; i++) {
            SBLS(i);
        }
    }
}