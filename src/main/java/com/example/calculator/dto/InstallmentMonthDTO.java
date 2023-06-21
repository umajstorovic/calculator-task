package com.example.calculator.dto;

import java.math.BigDecimal;

public record InstallmentMonthDTO(Integer paymentNumber,
                                  BigDecimal monthlyPayment,
                                  BigDecimal principalAmount,
                                  BigDecimal interestAmount,
                                  BigDecimal remainingBalance) {
}
