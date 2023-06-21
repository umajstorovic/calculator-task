package com.example.calculator.service;

import com.example.calculator.dto.InstallmentRequestDTO;
import com.example.calculator.dto.InstallmentResponseDTO;
import com.example.calculator.model.Installment;
import com.example.calculator.repository.InstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.example.calculator.util.CalculatorUtil.*;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private final InstallmentRepository installmentRepository;

    @Override
    public InstallmentResponseDTO calculateInstallment(InstallmentRequestDTO installmentRequestDTO) {
        final var loanAmount = installmentRequestDTO.amount();
        final var annualInterestRate = installmentRequestDTO.annualInterest();
        final var loanTermMonths = installmentRequestDTO.numberOfMonths();


        final var monthlyInterestRate = calculateMonthlyInterestRate(annualInterestRate);
        final var monthlyPayment = calculateMonthlyPayment(loanAmount, monthlyInterestRate, loanTermMonths);

        BigDecimal totalPayment = monthlyPayment.multiply(BigDecimal.valueOf(loanTermMonths)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal totalInterest = totalPayment.subtract(loanAmount);

        Installment installment = new Installment(loanAmount, annualInterestRate, loanTermMonths, monthlyPayment,
                totalPayment, totalInterest);
        generateAmortizationSchedule(loanAmount, monthlyInterestRate, loanTermMonths, monthlyPayment, installment);

        Installment savedInstallment = this.installmentRepository.save(installment);

        return new InstallmentResponseDTO(convertToInstallmentMonthDTOs(savedInstallment.getInstallmentMonths()));
    }
}


