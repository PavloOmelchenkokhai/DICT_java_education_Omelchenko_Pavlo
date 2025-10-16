import java.util.*;

 public class CoffeeMachine {

    private enum State {
        CHOOSING_ACTION,
        CHOOSING_COFFEE_TYPE,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_BEANS,
        FILLING_CUPS,
        EXITING
    }

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

    private State currentState;
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private int tempWater, tempMilk, tempBeans, tempCups;

    private final Scanner scanner;

    public CoffeeMachine() {
        this.currentState = State.CHOOSING_ACTION;
        this.scanner = new Scanner(System.in);
        displayPrompt();
    }

    private void displayPrompt() {
        switch (currentState) {
            case CHOOSING_ACTION:
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                break;
            case CHOOSING_COFFEE_TYPE:
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back – to main menu:");
                break;
            case FILLING_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                break;
            case FILLING_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case FILLING_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case FILLING_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
            case EXITING:
                break;
        }
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
        if (water < coffeeType.water) missingIngredient = "water";
        else if (milk < coffeeType.milk) missingIngredient = "milk";
        else if (beans < coffeeType.beans) missingIngredient = "coffee beans";
        else if (cups < 1) missingIngredient = "disposable cups";

        if (!missingIngredient.isEmpty()) {
            System.out.println("Sorry, not enough " + missingIngredient + "!");
            return false;
        }
        return true;
    }

    private void makeCoffee(Coffee coffeeType) {
        if (checkResources(coffeeType)) {
            System.out.println("I have enough resources, making you a coffee!");
            water -= coffeeType.water;
            milk -= coffeeType.milk;
            beans -= coffeeType.beans;
            cups -= 1;
            money += coffeeType.cost;
        }
    }

    private void take() {
        System.out.println("I gave you " + money);
        money = 0;
    }

    public void processInput(String input) {
        input = input.trim();
        if (input.isEmpty() && currentState != State.CHOOSING_ACTION) return;

        switch (currentState) {
            case CHOOSING_ACTION:
                handleChoosingAction(input);
                break;
            case CHOOSING_COFFEE_TYPE:
                handleChoosingCoffeeType(input);
                break;
            case FILLING_WATER:
            case FILLING_MILK:
            case FILLING_BEANS:
            case FILLING_CUPS:
                handleFilling(input);
                break;
            case EXITING:
                return;
        }

        if (currentState != State.EXITING) {
            displayPrompt();
        }
    }

    private void handleChoosingAction(String input) {
        switch (input.toLowerCase()) {
            case "buy":
                currentState = State.CHOOSING_COFFEE_TYPE;
                break;
            case "fill":
                tempWater = tempMilk = tempBeans = tempCups = 0;
                currentState = State.FILLING_WATER;
                break;
            case "take":
                take();
                break;
            case "remaining":
                remaining();
                break;
            case "exit":
                currentState = State.EXITING;
                break;
            default:
                System.out.println("Invalid action. Please use 'buy', 'fill', 'take', 'remaining', or 'exit'.");
                break;
        }
    }

    private void handleChoosingCoffeeType(String input) {
        if (input.equalsIgnoreCase("back")) {
            currentState = State.CHOOSING_ACTION;
            return;
        }

        try {
            int coffeeId = Integer.parseInt(input);
            if (coffeeId >= 1 && coffeeId <= COFFEE_TYPES.length) {
                makeCoffee(COFFEE_TYPES[coffeeId - 1]);
                currentState = State.CHOOSING_ACTION;
            } else {
                System.out.println("Invalid selection. Please choose 1, 2, 3, or 'back'.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter 1, 2, 3, or 'back'.");
        }
    }

    private void handleFilling(String input) {
        try {
            int amount = Integer.parseInt(input);
            if (amount < 0) {
                System.out.println("Amount must be non-negative.");
                return;
            }

            switch (currentState) {
                case FILLING_WATER:
                    tempWater = amount;
                    currentState = State.FILLING_MILK;
                    break;
                case FILLING_MILK:
                    tempMilk = amount;
                    currentState = State.FILLING_BEANS;
                    break;
                case FILLING_BEANS:
                    tempBeans = amount;
                    currentState = State.FILLING_CUPS;
                    break;
                case FILLING_CUPS:
                    tempCups = amount;

                    water += tempWater;
                    milk += tempMilk;
                    beans += tempBeans;
                    cups += tempCups;

                    System.out.println("Resources added successfully.");
                    currentState = State.CHOOSING_ACTION;
                    break;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a whole number.");
        }
    }

    public void start() {
        while (currentState != State.EXITING) {
            String input = scanner.nextLine();
            processInput(input);
        }
        scanner.close();
        System.out.println("Coffee machine turning off.");
    }

    public static void main(String[] args) {
        new CoffeeMachine().start();
    }
}
