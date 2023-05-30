package com.example.lab2server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EquationRequestDto {
    private Integer equation;
    private Double left;
    private Double right;
    private Double x;
    private Double eps;
    private Integer method;

}
