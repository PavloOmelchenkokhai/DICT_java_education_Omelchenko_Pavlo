import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        game.start();
        scanner.close();
    }
}

class Game {
    private final Scanner scanner;
    private final Board board;
    private char currentPlayer;

    public Game(Scanner scanner) {
        this.scanner = scanner;
        this.board = new Board("_________");
        this.currentPlayer = 'X';
    }

    public void start() {
        board.display();

        while (true) {
            PlayerMove.makeMove(board, scanner, currentPlayer);
            board.display();

            String status = board.analyzeStatus();

            if (!status.equals("Game not finished")) {
                System.out.println(status);
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }
}

class Board {
    private static final String HORIZONTAL_LINE = "---------";
    private char[] cells;

    public Board(String initialState) {
        this.cells = initialState.toCharArray();
    }

    public void setCell(int index, char player) {
        cells[index] = player;
    }

    public char getCell(int index) {
        return cells[index];
    }

    public boolean isCellEmpty(int index) {
        return cells[index] == '_';
    }

    public void display() {
        System.out.println(HORIZONTAL_LINE);
        printRow(cells, 0);
        printRow(cells, 3);
        printRow(cells, 6);
        System.out.println(HORIZONTAL_LINE);
    }

    private void printRow(char[] c, int start) {
        System.out.printf("| %c %c %c |\n", c[start], c[start + 1], c[start + 2]);
    }

    public String analyzeStatus() {
        boolean xWins = checkWin('X');
        boolean oWins = checkWin('O');

        long xCount = Arrays.stream(new String(cells).split("")).filter(s -> s.equals("X")).count();
        long oCount = Arrays.stream(new String(cells).split("")).filter(s -> s.equals("O")).count();
        long emptyCount = Arrays.stream(new String(cells).split("")).filter(s -> s.equals("_")).count();

        if (xWins && oWins) return "Impossible";
        if (Math.abs(xCount - oCount) >= 2) return "Impossible";
        if (xWins) return "X wins";
        if (oWins) return "O wins";
        if (emptyCount > 0) return "Game not finished";
        return "Draw";
    }

    private boolean checkWin(char player) {
        int[][] lines = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] line : lines) {
            if (cells[line[0]] == player && cells[line[1]] == player && cells[line[2]] == player)
                return true;
        }
        return false;
    }
}

class PlayerMove {
    public static void makeMove(Board board, Scanner scanner, char player) {
        boolean moveAccepted = false;

        do {
            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();
            String[] parts = input.trim().split("\\s+");

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

                if (!board.isCellEmpty(index)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                board.setCell(index, player);
                moveAccepted = true;

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        } while (!moveAccepted);
    }
}