package com.example.lab2server.service;

import com.example.lab2server.util.Solver;
import com.example.lab2server.dto.SystemRequestDto;
import com.example.lab2server.dto.SystemResponseDto;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class SystemServiceImpl implements SystemService {

    @Override
    public SystemResponseDto solve(SystemRequestDto systemRequestDto) {
        BiFunction<Double, Double, Double> f1;
        BiFunction<Double, Double, Double> f1DerivativeX;
        BiFunction<Double, Double, Double> f1DerivativeY;
        BiFunction<Double, Double, Double> f2;
        BiFunction<Double, Double, Double> f2DerivativeX;
        BiFunction<Double, Double, Double> f2DerivativeY;

        Double x0 = systemRequestDto.getX();
        Double y0 =  systemRequestDto.getY();
        Double eps = systemRequestDto.getEps();
        if (systemRequestDto.getSystem() == 1) {
            f1 = (x, y) -> (x * x * x + 2 * x * x - 2 * x - 3 - 3 * y);
            f1DerivativeX = (x, y) -> (3 * x * x + 4 * x - 2);
            f1DerivativeY = (x, y) -> (- 3.);

        }  else {
            f1 = (x, y) -> (x * x * x * x * x + 3 * x * x * x - x + 2 - 3 * y);
            f1DerivativeX = (x, y) -> (3 * x * x + 4 * x - 2);
            f1DerivativeY = (x, y) -> (- 3.);
        }
        f2 = (x, y) -> (-x * x * x + x * x - Math.log(x * x) + 3 * Math.sin(x) - 3 * y);
        f2DerivativeX = (x, y) -> (-3 * x * x + 2 * x - 2 / x + 3 * Math.cos(x));
        f2DerivativeY = (x, y) -> (- 3.);
        return Solver.method6(x0, y0, eps, f1, f1DerivativeX, f1DerivativeY,
                f2, f2DerivativeX, f2DerivativeY);
    }
}

