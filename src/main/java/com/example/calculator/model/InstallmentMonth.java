package com.example.calculator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor(force = true)
@Table(name = "installment_month")
public class InstallmentMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_number")
    @NonNull
    private Integer paymentNumber;

    @Column(name = "montly_payment")
    @NonNull
    private BigDecimal monthlyPayment;

    @Column(name = "principle_amount")
    @NonNull
    private BigDecimal principalAmount;

    @Column(name = "interest_amount")
    @NonNull
    private BigDecimal interestAmount;

    @Column(name = "remaining_balance")
    @NonNull
    private BigDecimal remainingBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installment_id")
    private Installment installment;
}
