import java.util.*;

public class TicTacToe {

    private static final String HORIZONTAL_LINE = "---------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String cells = scanner.nextLine();
        scanner.close();

        if (cells.length() != 9) {
            System.out.println("Error: The string must contain exactly 9 characters.");
            return;
        }

        displayBoard(cells);

        analyzeAndPrintResult(cells);
    }

    private static void displayBoard(String cells) {
        System.out.println(HORIZONTAL_LINE);
        printRow(cells.substring(0, 3));
        printRow(cells.substring(3, 6));
        printRow(cells.substring(6, 9));
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printRow(String rowString) {
        String formattedSymbols = String.join(" ", rowString.split(""));
        System.out.printf("| %s |\n", formattedSymbols);
    }

    private static void analyzeAndPrintResult(String cells) {

        boolean xWins = checkWin(cells, 'X');
        boolean oWins = checkWin(cells, 'O');

        long xCount = cells.chars().filter(ch -> ch == 'X').count();
        long oCount = cells.chars().filter(ch -> ch == 'O').count();
        long emptyCount = cells.chars().filter(ch -> ch == '_').count();

        if (xWins && oWins) {
            System.out.println("Impossible");
            return;
        }

        if (Math.abs(xCount - oCount) >= 2) {
            System.out.println("Impossible");
            return;
        }

        if (xWins) {
            System.out.println("X wins");
            return;
        }

        if (oWins) {
            System.out.println("O wins");
            return;
        }

        if (emptyCount > 0) {
            System.out.println("Game not finished");
        } else {
            System.out.println("Draw");
        }
    }

    private static boolean checkWin(String cells, char player) {

        if (checkThree(cells, player, 0, 1, 2) ||
                checkThree(cells, player, 3, 4, 5) ||
                checkThree(cells, player, 6, 7, 8)) {
            return true;
        }

        if (checkThree(cells, player, 0, 3, 6) ||
                checkThree(cells, player, 1, 4, 7) ||
                checkThree(cells, player, 2, 5, 8)) {
            return true;
        }

        if (checkThree(cells, player, 0, 4, 8) ||
                checkThree(cells, player, 2, 4, 6)) {
            return true;
        }

        return false;
    }

    private static boolean checkThree(String cells, char player, int i1, int i2, int i3) {
        return cells.charAt(i1) == player &&
                cells.charAt(i2) == player &&
                cells.charAt(i3) == player;
    }
}