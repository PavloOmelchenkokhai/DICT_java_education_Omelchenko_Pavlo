package RockPaperScissors;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        System.out.println("Hello, " + name);

        int rating = 0;
        try {
            File file = new File("rating.txt");
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String user = fileScanner.next();
                int score = fileScanner.nextInt();
                if (user.equals(name)) {
                    rating = score;
                    break;
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
        }

        String inputOptions = scanner.nextLine().trim();
        List<String> options;
        if (inputOptions.isEmpty()) {
            options = Arrays.asList("rock", "paper", "scissors");
        } else {
            options = Arrays.asList(inputOptions.split(","));
            options.replaceAll(String::trim);
        }

        System.out.println("Okay, let's start");

        while (true) {
            String userChoice = scanner.nextLine().trim().toLowerCase();

            if (userChoice.equals("!exit")) {
                System.out.println("Bye!");
                break;
            }

            if (userChoice.equals("!rating")) {
                System.out.println("Your rating: " + rating);
                continue;
            }

            if (!options.contains(userChoice)) {
                System.out.println("Invalid input");
                continue;
            }

            String computerChoice = options.get(random.nextInt(options.size()));

            if (userChoice.equals(computerChoice)) {
                System.out.println("There is a draw (" + computerChoice + ")");
                rating += 50;
            } else {
                if (isUserWinner(userChoice, computerChoice, options)) {
                    System.out.println("Well done. The computer chose " + computerChoice + " and failed");
                    rating += 100;
                } else {
                    System.out.println("Sorry, but the computer chose " + computerChoice);
                }
            }
        }
    }

     private static boolean isUserWinner(String userChoice, String computerChoice, List<String> options) {
        int total = options.size();
        int userIndex = options.indexOf(userChoice);

        List<String> shifted = new ArrayList<>();
        shifted.addAll(options.subList(userIndex + 1, total));
        shifted.addAll(options.subList(0, userIndex));

        int half = shifted.size() / 2;
        List<String> losers = shifted.subList(0, half);
        List<String> winners = shifted.subList(half, shifted.size());

        return winners.contains(computerChoice);

    }
}