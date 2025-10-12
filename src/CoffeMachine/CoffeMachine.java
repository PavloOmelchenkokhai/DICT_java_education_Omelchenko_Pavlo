import java.util.*;

 public class CoffeMachine {

    private static class Coffee {
        final String name;
        final int water;
        final int milk;
        final int beans;
        final int cost;
        final int id;

        public Coffee(int id, String name, int water, int milk, int beans, int cost) {
            this.id = id;
            this.name = name;
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }
    }

    private static final Coffee ESPRESSO = new Coffee(1, "espresso", 250, 0, 16, 4);
    private static final Coffee LATTE = new Coffee(2, "latte", 350, 75, 20, 7);
    private static final Coffee CAPPUCCINO = new Coffee(3, "cappuccino", 200, 100, 12, 6);

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

    private void displayStatus() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    private boolean checkResources(Coffee coffeeType) {
        if (water < coffeeType.water) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk < coffeeType.milk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (beans < coffeeType.beans) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }
        return true;
    }

    private void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: (enter number)");

        try {
            int choice = scanner.nextInt();

            if (choice < 1 || choice > COFFEE_TYPES.length) {
                System.out.println("Invalid selection. Please choose 1, 2, or 3.");
                return;
            }

            Coffee selectedCoffee = COFFEE_TYPES[choice - 1];

            if (checkResources(selectedCoffee)) {
                System.out.println("I have enough resources, making you a " + selectedCoffee.name + "!");

                water -= selectedCoffee.water;
                milk -= selectedCoffee.milk;
                beans -= selectedCoffee.beans;
                cups -= 1;
                money += selectedCoffee.cost;
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number for your choice.");
            scanner.next();
        }
    }

    private void fill() {
        try {
            System.out.println("Write how many ml of water you want to add:");
            int addedWater = scanner.nextInt();

            System.out.println("Write how many ml of milk you want to add:");
            int addedMilk = scanner.nextInt();

            System.out.println("Write how many grams of coffee beans you want to add:");
            int addedBeans = scanner.nextInt();

            System.out.println("Write how many disposable coffee cups you want to add:");
            int addedCups = scanner.nextInt();

            water += addedWater;
            milk += addedMilk;
            beans += addedBeans;
            cups += addedCups;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter whole numbers for amounts.");
            scanner.next();
        }
    }

    private void take() {
        System.out.println("I gave you " + money);
        money = 0;
    }

    public void start() {
        displayStatus();

        System.out.println("\nWrite action (buy, fill, take):");

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
            default:
                System.out.println("Unknown action: " + action + ". Please use 'buy', 'fill', or 'take'.");
                break;
        }

        displayStatus();
        scanner.close();
    }

    public static void main(String[] args) {
        new CoffeMachine().start();
    }
}
