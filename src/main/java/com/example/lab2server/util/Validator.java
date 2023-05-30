package com.example.lab2server.util;

import java.util.function.Function;

public class Validator {
    public static boolean validate(double a, double b, Function<Double, Double> f) {
        int count = 0;

        for (double i = a + 0.1; i <= b; i += 0.1) {
            double left = f.apply(i - 0.1);
            double right = f.apply(i);
            if (left * right <= 0) {
                count++;
            }
        }

        return count == 1;
    }
    public static boolean validateConvergence(double a, double b, Function<Double, Double> fiDerivative) {
        return Math.abs(fiDerivative.apply(a)) < 1 && Math.abs(fiDerivative.apply(b)) < 1;
    }


}
