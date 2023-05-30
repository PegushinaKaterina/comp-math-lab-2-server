package com.example.lab2server.service;

import com.example.lab2server.dto.EquationRequestDto;
import com.example.lab2server.dto.EquationResponseDto;
import com.example.lab2server.exeption.BadIntervalException;
import com.example.lab2server.util.Solver;
import com.example.lab2server.util.Validator;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EquationServiceImpl implements EquationService {
    @Override
    public EquationResponseDto solve(EquationRequestDto equationRequestDto) {
        Function<Double, Double> f;
        Function<Double, Double> fDerivative;
        Function<Double, Double> fDerivativeDerivative;
        Function<Double, Double> fi;
        Function<Double, Double> fiDerivative;
        Double a = equationRequestDto.getLeft();
        Double b = equationRequestDto.getRight();
        Double eps = equationRequestDto.getEps();
        if (equationRequestDto.getEquation() == 1) {
            f = (x) -> (x * x * x + 2 * x * x - 2 * x - 3);
            fDerivative = (x) -> (3 * x * x + 4 * x - 2);
            fDerivativeDerivative = (x) -> (6 * x + 4);
        } else if (equationRequestDto.getEquation() == 2) {
            f = (x) -> (x * x * x * x * x + 3 * x * x * x - x + 2);
            fDerivative = (x) -> (5 * x * x * x * x + 9 * x * x - 1);
            fDerivativeDerivative = (x) -> (20 * x * x * x + 18 * x );
        } else {
            f = (x) -> (-x * x * x + x * x - Math.log(x * x) + 3 * Math.sin(x));
            fDerivative = (x) -> (-3 * x * x + 2 * x - 2 / x + 3 * Math.cos(x));
            fDerivativeDerivative = (x) -> (-6 * x + 2 + 1 / (x * x) - 3 * Math.sin(x) );
        }
        double lambda = -1 / Math.max(Math.abs(fDerivative.apply(a)), Math.abs(fDerivative.apply(b)));
        fi = (x) -> (x + lambda * f.apply(x));
        fiDerivative = (x) -> (x + lambda * fDerivative.apply(x));
        if (Validator.validate(a, b, f)) {
            if (equationRequestDto.getMethod() == 1) {
                return Solver.method1(a, b, eps, f);
            } else if (equationRequestDto.getMethod() == 3) {
                return Solver.method3(a, b, eps, f, fDerivative, fDerivativeDerivative);
            } else {
                if (Validator.validateConvergence(a, b, fiDerivative)) {
                    return Solver.method5(a, b, eps, fi);
                } else {
                    throw new BadIntervalException("Сходимость для метода простых итераций не выполнена." +
                            "\nВыберите другой метод или смените интервал.");
                }
            }
        } else {
            throw new BadIntervalException("Некорректный интервал. " +
                    "\nВыберите интервал, на котором будет только один корень.");
        }
    }
}

