package com.example.lab2server.service;

import com.example.lab2server.dto.EquationRequestDto;
import com.example.lab2server.dto.EquationResponseDto;


public interface EquationService {
    EquationResponseDto solve(EquationRequestDto equationRequestDto);
}
