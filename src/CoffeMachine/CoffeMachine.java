import java.util.*;

public class CoffeMachine {

    private static final int WATER_PER_CUP_ML = 200;
    private static final int MILK_PER_CUP_ML = 50;
    private static final int COFFEE_BEANS_PER_CUP_G = 15;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write how many cups of coffee you will need:");

        if (scanner.hasNextInt()) {
            int cups = scanner.nextInt();

            if (cups > 0) {
                int totalWater = cups * WATER_PER_CUP_ML;
                int totalMilk = cups * MILK_PER_CUP_ML;
                int totalCoffeeBeans = cups * COFFEE_BEANS_PER_CUP_G;

                System.out.println("\nFor " + cups + " cups of coffee you will need:");
                System.out.println(totalWater + " ml of water");
                System.out.println(totalMilk + " ml of milk");
                System.out.println(totalCoffeeBeans + " g of coffee beans");
            } else {
                System.out.println("Please enter a positive number of cups.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
        }

        scanner.close();
    }
}