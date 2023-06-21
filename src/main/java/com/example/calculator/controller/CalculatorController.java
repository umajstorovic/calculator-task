package com.example.calculator.controller;

import com.example.calculator.dto.InstallmentRequestDTO;
import com.example.calculator.dto.InstallmentResponseDTO;
import com.example.calculator.service.CalculatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.calculator.constant.CalculatorRouter.CALCULATOR;

@RestController
@AllArgsConstructor
@RequestMapping(CALCULATOR)
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping
    public ResponseEntity<InstallmentResponseDTO> calculateInstallment(@Valid @RequestBody InstallmentRequestDTO installmentRequestDTO) {
        return ResponseEntity.ok(this.calculatorService.calculateInstallment(installmentRequestDTO));
    }
}
