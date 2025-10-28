import java.util.*;

class Matrix {
    private int rows;
    private int cols;
    private double[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double[][] getData() {
        return data;
    }

    public void read(Scanner sc) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = sc.nextDouble();
            }
        }
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class MatrixUtils {

    public static Matrix add(Matrix A, Matrix B) {
        if (A.getRows() != B.getRows() || A.getCols() != B.getCols()) {
            return null;
        }

        Matrix result = new Matrix(A.getRows(), A.getCols());
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getCols(); j++) {
                result.getData()[i][j] = A.getData()[i][j] + B.getData()[i][j];
            }
        }
        return result;
    }

    public static Matrix multiplyByConstant(Matrix A, double k) {
        Matrix result = new Matrix(A.getRows(), A.getCols());
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getCols(); j++) {
                result.getData()[i][j] = A.getData()[i][j] * k;
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.getCols() != B.getRows()) {
            return null;
        }

        Matrix result = new Matrix(A.getRows(), B.getCols());
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < B.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < A.getCols(); k++) {
                    sum += A.getData()[i][k] * B.getData()[k][j];
                }
                result.getData()[i][j] = sum;
            }
        }
        return result;
    }
}

public class MatrixProcessing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Your choice: > ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addMatrices(sc);
                case 2 -> multiplyByConstant(sc);
                case 3 -> multiplyMatrices(sc);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("0. Exit");
    }

    private static void addMatrices(Scanner sc) {
        System.out.print("Enter size of first matrix: > ");
        int n1 = sc.nextInt();
        int m1 = sc.nextInt();
        Matrix A = new Matrix(n1, m1);
        System.out.println("Enter first matrix:");
        A.read(sc);

        System.out.print("Enter size of second matrix: > ");
        int n2 = sc.nextInt();
        int m2 = sc.nextInt();
        Matrix B = new Matrix(n2, m2);
        System.out.println("Enter second matrix:");
        B.read(sc);

        Matrix result = MatrixUtils.add(A, B);
        if (result == null) {
            System.out.println("The operation cannot be performed.");
        } else {
            System.out.println("The result is:");
            result.print();
        }
    }

    private static void multiplyByConstant(Scanner sc) {
        System.out.print("Enter size of matrix: > ");
        int n = sc.nextInt();
        int m = sc.nextInt();
        Matrix A = new Matrix(n, m);
        System.out.println("Enter matrix:");
        A.read(sc);

        System.out.print("Enter constant: > ");
        double k = sc.nextDouble();

        Matrix result = MatrixUtils.multiplyByConstant(A, k);
        System.out.println("The result is:");
        result.print();
    }

    private static void multiplyMatrices(Scanner sc) {
        System.out.print("Enter size of first matrix: > ");
        int n1 = sc.nextInt();
        int m1 = sc.nextInt();
        Matrix A = new Matrix(n1, m1);
        System.out.println("Enter first matrix:");
        A.read(sc);

        System.out.print("Enter size of second matrix: > ");
        int n2 = sc.nextInt();
        int m2 = sc.nextInt();
        Matrix B = new Matrix(n2, m2);
        System.out.println("Enter second matrix:");
        B.read(sc);

        Matrix result = MatrixUtils.multiply(A, B);
        if (result == null) {
            System.out.println("The operation cannot be performed.");
        } else {
            System.out.println("The result is:");
            result.print();
        }
    }
}


