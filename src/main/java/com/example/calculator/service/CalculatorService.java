package com.example.calculator.service;

import com.example.calculator.dto.InstallmentRequestDTO;
import com.example.calculator.dto.InstallmentResponseDTO;

public interface CalculatorService {
    InstallmentResponseDTO calculateInstallment(InstallmentRequestDTO installmentRequestDTO);
}
