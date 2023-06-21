package com.example.calculator.util;

import com.example.calculator.dto.InstallmentMonthDTO;
import com.example.calculator.model.Installment;
import com.example.calculator.model.InstallmentMonth;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.example.calculator.constant.NumberConstants.*;
import static java.util.stream.Collectors.toList;

public class CalculatorUtil {

    public static BigDecimal calculateMonthlyInterestRate(BigDecimal interestRate) {
        return interestRate
                .divide(BigDecimal.valueOf(PERCENTAGE_DENOMINATOR), TEN_DIGITS_ROUNDING, RoundingMode.HALF_EVEN)
                .divide(BigDecimal.valueOf(MONTHS_IN_YEAR_DENOMINATOR), TEN_DIGITS_ROUNDING, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal monthlyInterestRate, int loanTermMonths) {
        final var denominator = BigDecimal.ONE.add(monthlyInterestRate).pow(loanTermMonths).subtract(BigDecimal.ONE);
        return loanAmount
                .multiply(monthlyInterestRate)
                .multiply(BigDecimal.ONE.add(monthlyInterestRate).pow(loanTermMonths))
                .divide(denominator, TEN_DIGITS_ROUNDING, RoundingMode.HALF_EVEN)
                .setScale(TWO_DIGITS_ROUNDING, RoundingMode.HALF_EVEN);
    }

    public static void generateAmortizationSchedule(BigDecimal loanAmount, BigDecimal monthlyInterestRate,
                                                    int loanTermMonths, BigDecimal monthlyPayment,
                                                    Installment installment) {
        var remainingBalance = loanAmount;

        for (int paymentNumber = 1; paymentNumber <= loanTermMonths; paymentNumber++) {
            final var interestAmount = remainingBalance.multiply(monthlyInterestRate)
                    .setScale(TWO_DIGITS_ROUNDING, RoundingMode.HALF_EVEN);
            final var principalAmount = monthlyPayment.subtract(interestAmount)
                    .setScale(TWO_DIGITS_ROUNDING, RoundingMode.HALF_EVEN);
            remainingBalance = remainingBalance.subtract(principalAmount);

            installment.addInstallmentMonth(new InstallmentMonth(paymentNumber, monthlyPayment,
                    principalAmount, interestAmount, remainingBalance));
        }
    }

    public static List<InstallmentMonthDTO> convertToInstallmentMonthDTOs(List<InstallmentMonth> installmentMonths) {
        return installmentMonths.stream()
                .map(installmentMonth ->
                        new InstallmentMonthDTO(installmentMonth.getPaymentNumber(),
                                installmentMonth.getMonthlyPayment(),
                                installmentMonth.getPrincipalAmount(),
                                installmentMonth.getInterestAmount(),
                                installmentMonth.getRemainingBalance()))
                .collect(toList());
    }
}
