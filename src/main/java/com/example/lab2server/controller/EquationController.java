package com.example.lab2server.controller;

import com.example.lab2server.exeption.BadIntervalException;
import com.example.lab2server.dto.EquationRequestDto;
import com.example.lab2server.dto.EquationResponseDto;
import com.example.lab2server.service.EquationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class EquationController {
    private final EquationService equationService;

    @Autowired
    public EquationController(EquationService equationService) {
        this.equationService = equationService;
    }

    @PostMapping("/equation")
    public ResponseEntity<EquationResponseDto> solve(@RequestBody EquationRequestDto equationRequestDto) {
        return ResponseEntity.ok(equationService.solve(equationRequestDto));
    }


    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(BadIntervalException exception) {
       return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
