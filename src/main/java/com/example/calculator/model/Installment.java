package com.example.calculator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor(force = true)
@Table(name = "installment")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_loaned")
    @NonNull
    private BigDecimal amountLoaned;

    @Column(name = "annual_interest")
    @NonNull
    private BigDecimal annualInterest;

    @Column(name = "months")
    @NonNull
    private Integer months;

    @Column(name = "monthly_payment")
    @NonNull
    private BigDecimal monthlyPayment;

    @Column(name = "total_amount_paid")
    @NonNull
    private BigDecimal totalAmountPaid;

    @Column(name = "total_interest")
    @NonNull
    private BigDecimal totalInterest;

    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstallmentMonth> installmentMonths = new ArrayList<>();

    public void addInstallmentMonth(InstallmentMonth installmentMonth){
        installmentMonths.add(installmentMonth);
        installmentMonth.setInstallment(this);
    }
}
