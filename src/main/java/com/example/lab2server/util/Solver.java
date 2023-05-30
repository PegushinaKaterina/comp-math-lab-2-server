package com.example.lab2server.util;

import com.example.lab2server.dto.EquationResponseDto;
import com.example.lab2server.dto.SystemResponseDto;
import com.example.lab2server.exeption.BadApproximationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Solver {
    //1 - Метод половинного деления
    public static EquationResponseDto method1(double a, double b, double eps, Function<Double, Double> f) {
        double x = (a + b) / 2;
        int numberOfIteration = 0;
        while (Math.abs(f.apply(x)) > eps) {
            numberOfIteration++;
            if (f.apply(a) * f.apply(x) > 0) {
                a = x;
            } else {
                b = x;
            }
            x = (a + b) / 2;
        }
        return new EquationResponseDto(x, f.apply(x), numberOfIteration);

    }
    //3 - Метод Ньютона
    public static EquationResponseDto method3(double a, double b, double eps, Function<Double, Double> f, Function<Double, Double> fDerivative, Function<Double, Double> fDerivativeDerivative) {
        double lastX = f.apply(a) * fDerivativeDerivative.apply(a) > 0 ? a : b;
        double x = lastX - f.apply(lastX)/ fDerivative.apply(lastX);
        int numberOfIteration = 0;
        while (Math.abs(f.apply(x)) > eps) {
            numberOfIteration++;
            lastX = x;
            x = lastX - f.apply(lastX)/ fDerivative.apply(lastX);
        }
        return new EquationResponseDto(x, f.apply(x), numberOfIteration);
    }
    //5 - Метод простой итерации
    public static EquationResponseDto method5(double a, double b, double eps, Function<Double, Double> fi) {
        Double lastX = (a + b) / 2;
        Double x = fi.apply(lastX);
        int numberOfIteration = 0;
        while (Math.abs(x - lastX) > eps) {
            numberOfIteration++;
            lastX = x;
            x = fi.apply(lastX);
        }
        return new EquationResponseDto(x, fi.apply(x), numberOfIteration);
    }
    //6 – Метод Ньютона
    public static SystemResponseDto method6(double x, double y, double eps,
                                            BiFunction<Double, Double, Double> f1,
                                            BiFunction<Double, Double, Double> f1DerivativeX,
                                            BiFunction<Double, Double, Double> f1DerivativeY,
                                            BiFunction<Double, Double, Double> f2,
                                            BiFunction<Double, Double, Double> f2DerivativeX,
                                            BiFunction<Double, Double, Double> f2DerivativeY) {

        double[][] matrix = new double[2][3];
        List<Double> maxDxDy = new ArrayList<>();
        double x0 = x;
        double y0 = y;

        int numberOfIteration = 0;
        do {
            numberOfIteration++;
            matrix[0][0] = f1DerivativeX.apply(x0, y0);
            matrix[0][1] = f1DerivativeY.apply(x0, y0);
            matrix[0][2] = - f1.apply(x0, y0);
            matrix[1][0] = f2DerivativeX.apply(x0, y0);
            matrix[1][1] = f2DerivativeY.apply(x0, y0);
            matrix[1][2] = - f2.apply(x0, y0);

            double[] answer = solveGauss(matrix);
            double x1 = answer[0] + x0;
            double y1 = answer[1] + y0;

            maxDxDy.add(Math.max(Math.abs(x1 - x0), Math.abs(y1 - y0)));

            if (maxDxDy.get(maxDxDy.size() - 1) <= eps) {
                return new SystemResponseDto(x1, y1, numberOfIteration, maxDxDy);
            }

            x0 = x1;
            y0 = y1;

        } while (numberOfIteration < 500);

        throw new BadApproximationException("Метод не решил систему за 500 итераций," +
                "измените начальное приближение.");

    }
    private static double[] solveGauss(double[][] matrix) {
        int n = matrix.length;
        triangulateMatrix(matrix);

        for (int i = 1; i < n; i++) {
            for (int jBeforeI = 0; jBeforeI < i; jBeforeI++) {
                double i_head = matrix[jBeforeI][i];
                for (int k = i; k < n + 1; k++) {
                    matrix[jBeforeI][k] = matrix[jBeforeI][k] - matrix[i][k] * i_head;
                }
            }
        }

        double[] answer = new double[n];
        for (int i = 0; i < matrix.length; i++) {
            answer[i] = matrix[i][n];
        }
        return answer;
    }

    private static void triangulateMatrix(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] == 0) {
                swapLines(matrix, i);
            }

            double mainDiagonalElement = matrix[i][i];

            for (int jInILine = i; jInILine < n + 1; jInILine++) {
                matrix[i][jInILine] = matrix[i][jInILine] / mainDiagonalElement;
            }

            for (int jAfterI = i + 1; jAfterI < n; jAfterI++) {
                double jLineHead = matrix[jAfterI][i];

                for (int k = i; k < n + 1; k++) {
                    matrix[jAfterI][k] = matrix[jAfterI][k] - jLineHead * matrix[i][k];
                }
            }

        }
    }

    private static void swapLines(double[][] matrix, int i) {
        for (int j = i + 1; j < matrix.length; j++) {
            if (matrix[j][i] != 0) {
                double[] jMatrix = matrix[j];
                matrix[j] = matrix[i];
                matrix[i] = jMatrix;

                return;
            }
        }
    }
}
