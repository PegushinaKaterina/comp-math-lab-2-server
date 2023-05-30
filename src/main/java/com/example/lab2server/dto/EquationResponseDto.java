package com.example.lab2server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EquationResponseDto {
    private Double x;
    private Double y;
    private Integer numberOfIterations;
}
