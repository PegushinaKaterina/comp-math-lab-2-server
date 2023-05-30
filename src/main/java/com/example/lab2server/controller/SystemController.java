package com.example.lab2server.controller;

import com.example.lab2server.dto.SystemRequestDto;
import com.example.lab2server.dto.SystemResponseDto;
import com.example.lab2server.exeption.BadApproximationException;
import com.example.lab2server.exeption.BadIntervalException;
import com.example.lab2server.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class SystemController {
    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }



    @PostMapping("/system")
    public ResponseEntity<SystemResponseDto> solve(@RequestBody SystemRequestDto systemRequestDto) {
        return ResponseEntity.ok(systemService.solve(systemRequestDto));
    }


    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(BadApproximationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
