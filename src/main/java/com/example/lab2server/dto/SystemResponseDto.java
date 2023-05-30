package com.example.lab2server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SystemResponseDto {
    private Double x;
    private Double y;
    private Integer numberOfIterations;
    private List<Double> maxDxDy;
}
