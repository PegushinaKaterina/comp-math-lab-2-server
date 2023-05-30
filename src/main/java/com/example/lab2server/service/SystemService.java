package com.example.lab2server.service;


import com.example.lab2server.dto.SystemRequestDto;
import com.example.lab2server.dto.SystemResponseDto;

public interface SystemService {
    SystemResponseDto solve(SystemRequestDto systemRequestDto);
}
