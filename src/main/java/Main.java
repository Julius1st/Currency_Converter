package main.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConversionRates conversionRates = new ConversionRates();

        while (true) {
            // Read base and target currency as well as amount to be converted from console
            System.out.println("Please state the base currency you want to convert (in the ISO 4217 Three Letter Currency Code Format):");
            ConversionRates.Codes base = readNextInputAsCode(scanner);

            System.out.println("Please state the target currency you want to convert to (in the ISO 4217 Three Letter Currency Code Format):");
            ConversionRates.Codes target = readNextInputAsCode(scanner);

            System.out.println("Please enter the amount you want to convert:");
            Double amount = readNextInputAsDouble(scanner);


            // Get conversion rate and calculate the converted amount
            Double rate = conversionRates.getRate(base, target);
            System.out.println("The conversion rate from " + base.name() + " to " + target.name() + " is " + rate +
                    ". The converted amount is " + (amount * rate) + " " + target.name() + ".");

            // Potential termination of the program
            System.out.println("Do you want to convert another currency? (y/n)");
            if (determineTermination(scanner)) {
                System.out.println("Thank you for using the Currency Converter. Goodbye!");
                break;
            }
        }
    }

    private static Double readNextInputAsDouble(Scanner scanner) {
        Double result;
        while (true) {
            try {
                result = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: That is not a valid number. Use . not , as decimal separator. Please try again:");
                continue;
            }
            break;
        }
        return result;
    }

    private static Boolean determineTermination(Scanner scanner) {
        while (true) {
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return false;
            } else if (answer.equals("n") || answer.equals("no")) {
                return true;
            } else {
                System.out.println("ERROR: That is not a valid answer. Please try again:");
            }
        }
    }

    private static ConversionRates.Codes readNextInputAsCode(Scanner scanner) {
        ConversionRates.Codes result;
        while (true) {
            try {
                result = ConversionRates.Codes.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: That is not a valid Three Letter Currency Code. Please try again:");
                continue;
            }
            break;
        }
        return result;
    }
}
