import java.util.*;

public class TicTacToe {

    private static final String HORIZONTAL_LINE = "---------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String cells = scanner.nextLine();

        if (cells.length() != 9) {
            System.out.println("Error: The string must contain exactly 9 characters.");
            scanner.close();
            return;
        }

        displayBoard(cells);
        cells = getUserMove(cells, scanner);
        displayBoard(cells);

        scanner.close();
    }

    private static String getUserMove(String cells, Scanner scanner) {

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
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                if (row < 1 || row > 3 || col < 1 || col > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                int index = (col - 1) * 3 + (row - 1);

                if (board[index] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                board[index] = 'X';
                moveAccepted = true;

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }

        } while (!moveAccepted);

        return new String(board);
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