import java.util.*;

 public class TicTacToe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String cells = scanner.nextLine();
        scanner.close();

        if (cells.length() != 9) {
            System.out.println("Error: The string must contain exactly 9 characters.");
            return;
        }

        String horizontalLine = "---------";
        System.out.println(horizontalLine);

        printRow(cells.substring(0, 3));

        printRow(cells.substring(3, 6));

        printRow(cells.substring(6, 9));

        System.out.println(horizontalLine);
    }

    private static void printRow(String rowString) {
        StringBuilder formattedRow = new StringBuilder();

        formattedRow.append("| ");

        for (int i = 0; i < rowString.length(); i++) {
            formattedRow.append(rowString.charAt(i));
            if (i < rowString.length() - 1) {
                formattedRow.append(" ");
            }
        }

        formattedRow.append(" |");

        System.out.println(formattedRow.toString());
    }
}