import java.util.*;

public class TicTacToe {

    private static final String HORIZONTAL_LINE = "---------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String cells = "_________";
        char currentPlayer = 'X';

        displayBoard(cells);

        while (true) {

            cells = getUserMove(cells, scanner, currentPlayer);

            displayBoard(cells);

            String gameStatus = analyzeStatus(cells);

            if (!gameStatus.equals("Game not finished")) {
                System.out.println(gameStatus);
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        scanner.close();
    }

    private static String getUserMove(String cells, Scanner scanner, char player) {

        char[] board = cells.toCharArray();
        boolean moveAccepted = false;

        do {
            System.out.print("Enter the coordinates: ");
            String inputLine = scanner.nextLine();

            String[] parts = inputLine.trim().split("\\s+");

            if (parts.length != 2) {
                System.out.println("You should enter numbers!");
                continue;
            }

            try {
                int col = Integer.parseInt(parts[0]);
                int row = Integer.parseInt(parts[1]);

                if (col < 1 || col > 3 || row < 1 || row > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                int index = (row - 1) * 3 + (col - 1);

                if (board[index] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                board[index] = player;
                moveAccepted = true;

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }

        } while (!moveAccepted);

        return new String(board);
    }

    private static String analyzeStatus(String cells) {

        boolean xWins = checkWin(cells, 'X');
        boolean oWins = checkWin(cells, 'O');

        long xCount = cells.chars().filter(ch -> ch == 'X').count();
        long oCount = cells.chars().filter(ch -> ch == 'O').count();
        long emptyCount = cells.chars().filter(ch -> ch == '_').count();

        if (xWins && oWins) {
            return "Impossible";
        }
        if (Math.abs(xCount - oCount) >= 2) {
            return "Impossible";
        }

        if (xWins) {
            return "X wins";
        }

        if (oWins) {
            return "O wins";
        }

        if (emptyCount > 0) {
            return "Game not finished";
        } else {
            return "Draw";
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
}