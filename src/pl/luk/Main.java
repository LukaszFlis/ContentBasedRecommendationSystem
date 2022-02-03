package pl.luk;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //znormalizowana Term Document Matrix
        double[][] A = createTDM(2, 2);

        //wektor zapytania (przyjmuje wartości 0 lub 1)
        double[] Z = createVector(2);

        //znormalizowany wektor zapytania
        double[] ZN = vectorNormalization(Z);

        //wartośc podobieństwa wybranego dokumentu do znormalizowanego wektora zapytania
        double sim = similarity(A, ZN);
        System.out.println();
        System.out.println("Podobieństwo wybranego dokumentu do zapytania 'Z' równa się: " + sim);
    }

    //inicjalizacja Term Document Matrix
    public static double[][] createTDM(int n, int m) {
        double[][] tdm = new double[n][m];
        try {
            for (int i = 0; i < tdm.length; i++) {
                for (int j = 0; j < tdm[i].length; j++) {
                    System.out.print("Wprowadz kolejny element macierzy A: ");
                    tdm[i][j] = sc.nextDouble();
                }
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        System.out.println();
        return tdm;
    }

    //inicjalizacja wektora
    public static double[] createVector(int n) {
        double[] vector = new double[n];
        try {
            for (int i = 0; i < vector.length; i++) {
                System.out.print("Wprowadz kolejny element macierzy Z: ");
                vector[i] = sc.nextDouble();
            }
        } catch (InputMismatchException ex) {
            ex.printStackTrace();
        }
        System.out.println();
        return vector;
    }

    //normalizacja pojedynczego dokumentu
    public static double[] vectorNormalization(double[] vector) {
        final double normFactor = f_norm(vector);
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= normFactor;
        }
        return vector;
    }

    //obliczenie czynnika normalizującego
    public static double f_norm(double[] vector) {
        //Sum of powers
        double sum = 0;
        for (double e : vector) {
            if (e == 1) {
                sum += Math.pow(e, 2);
            }
        }
        return Math.round(1 / Math.sqrt(sum) * 10000) / 10000.0;
    }

    //wyzanczenie podobieństwa wybranego dokumentu do znormalizowanego wketora zapytania
    public static double similarity(double[][] A, double[] Z) {
        double sim = 0;
        try {
            System.out.print("Macierz A zawiera: " + A.length +
                    " dokumentów. Wybierz dokument, " +
                    "którego podobieństwo do zapytania 'Z' należy wyznaczyć: ");
            int nDoc = sc.nextInt();
            double[] document = new double[A[nDoc].length];
            System.arraycopy(A[nDoc], 0, document, 0, document.length);
            double productOfVectors = 0;
            for (int i = 0; i < Z.length; i++) {
                productOfVectors += document[i] * Z[i];
            }
            if (productOfVectors == 0.0) {
                return 0;
            } else {
                //suma potęg elementów dokumentu (wektora)
                double sum = 0;
                for (double e : Z) {
                    sum += Math.pow(e, 2);
                }

                double qLength = Math.sqrt(sum);

                //długość wszystkich dokumentów w znormalizowanej macierzy TDM zawsze wynosi |D| = 1

                sim = productOfVectors / qLength;
            }


        } catch (InputMismatchException ex) {
            ex.printStackTrace();
        }
        return sim;
    }
}
