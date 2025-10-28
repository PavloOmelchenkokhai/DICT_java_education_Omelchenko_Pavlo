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
                System.out.printf("%.2f ", data[i][j]);
            }
            System.out.println();
        }
    }
}

class MatrixUtils {

    public static Matrix add(Matrix A, Matrix B) {
        if (A.getRows() != B.getRows() || A.getCols() != B.getCols()) return null;
        Matrix result = new Matrix(A.getRows(), A.getCols());
        for (int i = 0; i < A.getRows(); i++)
            for (int j = 0; j < A.getCols(); j++)
                result.getData()[i][j] = A.getData()[i][j] + B.getData()[i][j];
        return result;
    }

    public static Matrix multiplyByConstant(Matrix A, double k) {
        Matrix result = new Matrix(A.getRows(), A.getCols());
        for (int i = 0; i < A.getRows(); i++)
            for (int j = 0; j < A.getCols(); j++)
                result.getData()[i][j] = A.getData()[i][j] * k;
        return result;
    }

    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.getCols() != B.getRows()) return null;
        Matrix result = new Matrix(A.getRows(), B.getCols());
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < B.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < A.getCols(); k++)
                    sum += A.getData()[i][k] * B.getData()[k][j];
                result.getData()[i][j] = sum;
            }
        }
        return result;
    }

    public static Matrix transposeMainDiagonal(Matrix A) {
        Matrix result = new Matrix(A.getCols(), A.getRows());
        for (int i = 0; i < A.getRows(); i++)
            for (int j = 0; j < A.getCols(); j++)
                result.getData()[j][i] = A.getData()[i][j];
        return result;
    }

    public static Matrix transposeSideDiagonal(Matrix A) {
        Matrix result = new Matrix(A.getCols(), A.getRows());
        for (int i = 0; i < A.getRows(); i++)
            for (int j = 0; j < A.getCols(); j++)
                result.getData()[A.getCols() - 1 - j][A.getRows() - 1 - i] = A.getData()[i][j];
        return result;
    }

    public static Matrix transposeVertical(Matrix A) {
        Matrix result = new Matrix(A.getRows(), A.getCols());
        for (int i = 0; i < A.getRows(); i++)
            for (int j = 0; j < A.getCols(); j++)
                result.getData()[i][A.getCols() - 1 - j] = A.getData()[i][j];
        return result;
    }

    public static Matrix transposeHorizontal(Matrix A) {
        Matrix result = new Matrix(A.getRows(), A.getCols());
        for (int i = 0; i < A.getRows(); i++)
            for (int j = 0; j < A.getCols(); j++)
                result.getData()[A.getRows() - 1 - i][j] = A.getData()[i][j];
        return result;
    }

    public static double determinant(Matrix A) {
        if (A.getRows() != A.getCols()) return Double.NaN;
        return calculateDeterminant(A.getData());
    }

    private static double calculateDeterminant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) return matrix[0][0];
        if (n == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double det = 0;
        for (int col = 0; col < n; col++) {
            double[][] minor = getMinor(matrix, 0, col);
            det += Math.pow(-1, col) * matrix[0][col] * calculateDeterminant(minor);
        }
        return det;
    }

    private static double[][] getMinor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minor[r][c++] = matrix[i][j];
            }
            r++;
        }
        return minor;
    }

    public static Matrix inverse(Matrix A) {
        int n = A.getRows();
        if (n != A.getCols()) return null;

        double det = determinant(A);
        if (Math.abs(det) < 1e-9) return null;

        Matrix adjugate = adjugate(A);
        return multiplyByConstant(adjugate, 1.0 / det);
    }

    private static Matrix adjugate(Matrix A) {
        int n = A.getRows();
        Matrix result = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] minor = getMinor(A.getData(), i, j);
                double detMinor = calculateDeterminant(minor);
                result.getData()[j][i] = Math.pow(-1, i + j) * detMinor;
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
                case 4 -> transposeMatrix(sc);
                case 5 -> calculateDeterminant(sc);
                case 6 -> inverseMatrix(sc);
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Matrix Operations ===");
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
    }

    private static void addMatrices(Scanner sc) {
        System.out.print("Enter size of first matrix: > ");
        int n1 = sc.nextInt(), m1 = sc.nextInt();
        Matrix A = new Matrix(n1, m1);
        System.out.println("Enter first matrix:");
        A.read(sc);

        System.out.print("Enter size of second matrix: > ");
        int n2 = sc.nextInt(), m2 = sc.nextInt();
        Matrix B = new Matrix(n2, m2);
        System.out.println("Enter second matrix:");
        B.read(sc);

        Matrix result = MatrixUtils.add(A, B);
        if (result == null) System.out.println("The operation cannot be performed.");
        else {
            System.out.println("The result is:");
            result.print();
        }
    }

    private static void multiplyByConstant(Scanner sc) {
        System.out.print("Enter size of matrix: > ");
        int n = sc.nextInt(), m = sc.nextInt();
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
        int n1 = sc.nextInt(), m1 = sc.nextInt();
        Matrix A = new Matrix(n1, m1);
        System.out.println("Enter first matrix:");
        A.read(sc);

        System.out.print("Enter size of second matrix: > ");
        int n2 = sc.nextInt(), m2 = sc.nextInt();
        Matrix B = new Matrix(n2, m2);
        System.out.println("Enter second matrix:");
        B.read(sc);

        Matrix result = MatrixUtils.multiply(A, B);
        if (result == null) System.out.println("The operation cannot be performed.");
        else {
            System.out.println("The result is:");
            result.print();
        }
    }

    private static void transposeMatrix(Scanner sc) {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: > ");
        int option = sc.nextInt();

        System.out.print("Enter matrix size: > ");
        int n = sc.nextInt(), m = sc.nextInt();
        Matrix A = new Matrix(n, m);
        System.out.println("Enter matrix:");
        A.read(sc);

        Matrix result = switch (option) {
            case 1 -> MatrixUtils.transposeMainDiagonal(A);
            case 2 -> MatrixUtils.transposeSideDiagonal(A);
            case 3 -> MatrixUtils.transposeVertical(A);
            case 4 -> MatrixUtils.transposeHorizontal(A);
            default -> null;
        };

        if (result == null) System.out.println("Invalid choice.");
        else {
            System.out.println("The result is:");
            result.print();
        }
    }

    private static void calculateDeterminant(Scanner sc) {
        System.out.print("Enter matrix size: > ");
        int n = sc.nextInt(), m = sc.nextInt();
        if (n != m) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        Matrix A = new Matrix(n, m);
        System.out.println("Enter matrix:");
        A.read(sc);

        double det = MatrixUtils.determinant(A);
        System.out.println("The result is:");
        System.out.println((Math.abs(det - Math.round(det)) < 1e-9) ? (int) det : det);
    }

    private static void inverseMatrix(Scanner sc) {
        System.out.print("Enter matrix size: > ");
        int n = sc.nextInt(), m = sc.nextInt();
        if (n != m) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        Matrix A = new Matrix(n, m);
        System.out.println("Enter matrix:");
        A.read(sc);

        Matrix inverse = MatrixUtils.inverse(A);
        if (inverse == null) {
            System.out.println("This matrix doesn't have an inverse.");
        } else {
            System.out.println("The result is:");
            inverse.print();
        }
    }
}
