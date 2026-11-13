package com.lab;

/** Simple entry point required by the submission run.sh. */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        ShippingCalculator calculator = new ShippingCalculator();
        System.out.println("STANDARD (5kg): " + calculator.calculate(5, "STANDARD"));
        System.out.println("EXPRESS (5kg): " + calculator.calculate(5, "EXPRESS"));
    }
}
