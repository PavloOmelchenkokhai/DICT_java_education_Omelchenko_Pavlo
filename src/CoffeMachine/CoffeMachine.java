import java.util.*;

 public class CoffeMachine {

    private static class Coffee {
        final String name;
        final int water;
        final int milk;
        final int beans;
        final int cost;

        public Coffee(String name, int water, int milk, int beans, int cost) {
            this.name = name;
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }
    }

    private static final Coffee ESPRESSO = new Coffee("espresso", 250, 0, 16, 4);
    private static final Coffee LATTE = new Coffee("latte", 350, 75, 20, 7);
    private static final Coffee CAPPUCCINO = new Coffee("cappuccino", 200, 100, 12, 6);

    private static final Coffee[] COFFEE_TYPES = {ESPRESSO, LATTE, CAPPUCCINO};

    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private final Scanner scanner;

    public CoffeMachine() {
        this.scanner = new Scanner(System.in);
    }

    private void remaining() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    private boolean checkResources(Coffee coffeeType) {
        String missingIngredient = "";

        if (water < coffeeType.water) {
            missingIngredient = "water";
        } else if (milk < coffeeType.milk) {
            missingIngredient = "milk";
        } else if (beans < coffeeType.beans) {
            missingIngredient = "coffee beans";
        } else if (cups < 1) {
            missingIngredient = "disposable cups";
        }

        if (!missingIngredient.isEmpty()) {
            System.out.println("Sorry, not enough " + missingIngredient + "!");
            return false;
        }

        return true;
    }

    private void buy() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back – to main menu:");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("back")) {
            return;
        }

        try {
            int coffeeId = Integer.parseInt(choice);

            if (coffeeId < 1 || coffeeId > COFFEE_TYPES.length) {
                System.out.println("Invalid selection. Please choose 1, 2, 3, or 'back'.");
                return;
            }

            Coffee selectedCoffee = COFFEE_TYPES[coffeeId - 1];

            if (checkResources(selectedCoffee)) {
                System.out.println("I have enough resources, making you a coffee!");

                water -= selectedCoffee.water;
                milk -= selectedCoffee.milk;
                beans -= selectedCoffee.beans;
                cups -= 1;
                money += selectedCoffee.cost;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter 1, 2, 3, or 'back'.");
        }
    }

    private void fill() {
        try {
            System.out.println("Write how many ml of water do you want to add:");
            int addedWater = scanner.nextInt();

            System.out.println("Write how many ml of milk do you want to add:");
            int addedMilk = scanner.nextInt();

            System.out.println("Write how many grams of coffee beans do you want to add:");
            int addedBeans = scanner.nextInt();

            System.out.println("Write how many disposable cups of coffee do you want to add:");
            int addedCups = scanner.nextInt();

            water += addedWater;
            milk += addedMilk;
            beans += addedBeans;
            cups += addedCups;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter whole numbers for amounts.");
            scanner.nextLine();
        }
    }

    private void take() {
        System.out.println("I gave you " + money);
        money = 0;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");

            String action = scanner.next();

            switch (action.toLowerCase()) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    take();
                    break;
                case "remaining":
                    remaining();
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown action: " + action + ". Please use one of the listed commands.");
                    break;
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new CoffeMachine().start();
    }
}
