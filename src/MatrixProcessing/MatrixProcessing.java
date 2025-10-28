import java.util.*;

public class MatrixProcessing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n1 = sc.nextInt();
        int m1 = sc.nextInt();

        int[][] A = new int[n1][m1];
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < m1; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        int n2 = sc.nextInt();
        int m2 = sc.nextInt();

        int[][] B = new int[n2][m2];
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < m2; j++) {
                B[i][j] = sc.nextInt();
            }
        }

        if (n1 != n2 || m1 != m2) {
            System.out.println("ERROR");
        } else {
            for (int i = 0; i < n1; i++) {
                for (int j = 0; j < m1; j++) {
                    System.out.print((A[i][j] + B[i][j]) + " ");
                }
                System.out.println();
            }
        }
        sc.close();
    }
}