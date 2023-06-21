package com.example.calculator.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record InstallmentRequestDTO(@NotNull(message = "Amount must not be empty")
                                    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
                                    BigDecimal amount,
                                    @NotNull(message = "Annual interest must not be empty")
                                    @DecimalMin(value = "0.0", inclusive = false, message = "Annual interest must be greater than zero")
                                    BigDecimal annualInterest,
                                    @NotNull(message = "Number of months must not be empty")
                                    @Min(value = 1, message = "Number of months must be greater than zero")
                                    Integer numberOfMonths) {
}
