package com.example.calculator.util;

import com.example.calculator.dto.InstallmentMonthDTO;
import com.example.calculator.dto.InstallmentResponseDTO;
import com.example.calculator.model.Installment;
import com.example.calculator.model.InstallmentMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorUtilTest {

    @Mock
    private Installment installment;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateMonthlyInterestRate() {
        BigDecimal interestRate = BigDecimal.valueOf(5.0);
        BigDecimal expected = BigDecimal.valueOf(0.0041666667);
        BigDecimal result = CalculatorUtil.calculateMonthlyInterestRate(interestRate);

        assertEquals(expected, result);
    }

    @Test
    void calculateMonthlyPayment() {
        BigDecimal loanAmount = BigDecimal.valueOf(10000);
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(0.0041666667);
        int loanTermMonths = 12;

        BigDecimal expected = BigDecimal.valueOf(856.07);

        BigDecimal result = CalculatorUtil.calculateMonthlyPayment(loanAmount, monthlyInterestRate, loanTermMonths);

        assertEquals(expected, result);
    }

    @Test
    void generateAmortizationSchedule_shouldPopulateInstallmentCorrectly() {
        BigDecimal loanAmount = BigDecimal.valueOf(10000);
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(0.0041666667);
        BigDecimal annualInterestRate = BigDecimal.valueOf(5);
        int loanTermMonths = 5;
        BigDecimal monthlyPayment = BigDecimal.valueOf(202.51);
        BigDecimal totalPayment = BigDecimal.valueOf(1012.55);
        BigDecimal totalInterest = BigDecimal.valueOf(12.55);

        var expectedInstallment = getInstallment(loanAmount, annualInterestRate, loanTermMonths, monthlyPayment, totalPayment, totalInterest);
        var actualInstallment = getInstallment(loanAmount, annualInterestRate, loanTermMonths, monthlyPayment, totalPayment, totalInterest);

        CalculatorUtil.generateAmortizationSchedule(loanAmount, monthlyInterestRate, loanTermMonths, monthlyPayment, actualInstallment);

        assertEquals(expectedInstallment.getInstallmentMonths().get(0).getPrincipalAmount(), actualInstallment.getInstallmentMonths().get(0).getPrincipalAmount());
        assertEquals(expectedInstallment.getInstallmentMonths().get(3).getPrincipalAmount(), actualInstallment.getInstallmentMonths().get(3).getPrincipalAmount());
        assertEquals(expectedInstallment.getInstallmentMonths().get(4).getPrincipalAmount(), actualInstallment.getInstallmentMonths().get(4).getPrincipalAmount());
    }

    @Test
    void convertToInstallmentMonthDTOs_shouldConvertCorrectly() {
        InstallmentMonth installmentMonth = new InstallmentMonth(1, BigDecimal.valueOf(856.07),
                BigDecimal.valueOf(800), BigDecimal.valueOf(56.07), BigDecimal.valueOf(9200));
        List<InstallmentMonth> installmentMonths = List.of(installmentMonth);

        List<InstallmentMonthDTO> result = CalculatorUtil.convertToInstallmentMonthDTOs(installmentMonths);

        assertEquals(1, result.size());
        InstallmentMonthDTO convertedInstallmentMonthDTO = result.get(0);
        assertEquals(1, convertedInstallmentMonthDTO.paymentNumber());
        assertEquals(BigDecimal.valueOf(856.07), convertedInstallmentMonthDTO.monthlyPayment());
    }

    private Installment getInstallment(BigDecimal loanAmount, BigDecimal annualInterestRate, int loanTermMonths,
                                       BigDecimal monthlyPayment, BigDecimal totalPayment, BigDecimal totalInterest) {
        Installment installment = new Installment(loanAmount, annualInterestRate, loanTermMonths, monthlyPayment, totalPayment, totalInterest);

        installment.addInstallmentMonth(new InstallmentMonth(1, new BigDecimal("202.51"), new BigDecimal("198.34"), new BigDecimal("4.17"), new BigDecimal("801.66")));
        installment.addInstallmentMonth(new InstallmentMonth(2, new BigDecimal("202.51"), new BigDecimal("199.17"), new BigDecimal("3.34"), new BigDecimal("602.49")));
        installment.addInstallmentMonth(new InstallmentMonth(3, new BigDecimal("202.51"), new BigDecimal("200.00"), new BigDecimal("2.51"), new BigDecimal("402.49")));
        installment.addInstallmentMonth(new InstallmentMonth(4, new BigDecimal("202.51"), new BigDecimal("200.83"), new BigDecimal("1.68"), new BigDecimal("201.66")));
        installment.addInstallmentMonth(new InstallmentMonth(5, new BigDecimal("202.51"), new BigDecimal("201.67"), new BigDecimal("0.84"), new BigDecimal("-0.01")));
        return installment;
    }

}

