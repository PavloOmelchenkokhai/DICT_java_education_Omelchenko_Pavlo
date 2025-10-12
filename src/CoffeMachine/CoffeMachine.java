import java.util.*;

public class CoffeMachine {

    private static final int WATER_PER_CUP_ML = 200;
    private static final int MILK_PER_CUP_ML = 50;
    private static final int COFFEE_BEANS_PER_CUP_G = 15;

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.println("Write how many ml of water the coffee machine has:");
                int availableWater = scanner.nextInt();

                System.out.println("Write how many ml of milk the coffee machine has:");
                int availableMilk = scanner.nextInt();

                System.out.println("Write how many grams of coffee beans the coffee machine has:");
                int availableBeans = scanner.nextInt();

                System.out.println("Write how many cups of coffee you will need:");
                int cupsNeeded = scanner.nextInt();

                int maxCupsWater = availableWater / WATER_PER_CUP_ML;
                int maxCupsMilk = availableMilk / MILK_PER_CUP_ML;
                int maxCupsBeans = availableBeans / COFFEE_BEANS_PER_CUP_G;

                int maxCups = Math.min(Math.min(maxCupsWater, maxCupsMilk), maxCupsBeans);

                if (maxCups >= cupsNeeded) {
                    int extraCups = maxCups - cupsNeeded;

                    if (extraCups > 0) {
                        System.out.println("Yes, I can make that amount of coffee (and even " + extraCups + " more than that)");
                    } else {
                        System.out.println("Yes, I can make that amount of coffee");
                    }
                } else {
                    System.out.println("No, I can make only " + maxCups + " cups of coffee");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a whole number.");
            } catch (ArithmeticException e) {
                System.out.println("Error during calculation. Check recipe constants.");
            } finally {
                scanner.close();
            }
        }
    }