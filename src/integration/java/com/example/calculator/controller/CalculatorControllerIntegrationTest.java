package com.example.calculator.controller;

import com.example.calculator.dto.InstallmentMonthDTO;
import com.example.calculator.dto.InstallmentRequestDTO;
import com.example.calculator.dto.InstallmentResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class CalculatorControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_when_correct_input_then_calculate_installment() {
        InstallmentRequestDTO installmentRequestDTO = new InstallmentRequestDTO(new BigDecimal("1000"), new BigDecimal("5"), 5);

        var response = restTemplate.postForEntity("/calculator", installmentRequestDTO, InstallmentResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(getMockedInstallmentPlan(), response.getBody());
    }

    private InstallmentResponseDTO getMockedInstallmentPlan() {
        InstallmentMonthDTO installmentMonthDTO1 = new InstallmentMonthDTO(1, new BigDecimal("202.51"), new BigDecimal("198.34"), new BigDecimal("4.17"), new BigDecimal("801.66"));
        InstallmentMonthDTO installmentMonthDTO2 = new InstallmentMonthDTO(2, new BigDecimal("202.51"), new BigDecimal("199.17"), new BigDecimal("3.34"), new BigDecimal("602.49"));
        InstallmentMonthDTO installmentMonthDTO3 = new InstallmentMonthDTO(3, new BigDecimal("202.51"), new BigDecimal("200.00"), new BigDecimal("2.51"), new BigDecimal("402.49"));
        InstallmentMonthDTO installmentMonthDTO4 = new InstallmentMonthDTO(4, new BigDecimal("202.51"), new BigDecimal("200.83"), new BigDecimal("1.68"), new BigDecimal("201.66"));
        InstallmentMonthDTO installmentMonthDTO5 = new InstallmentMonthDTO(5, new BigDecimal("202.51"), new BigDecimal("201.67"), new BigDecimal("0.84"), new BigDecimal("-0.01"));
        return new InstallmentResponseDTO(List.of(installmentMonthDTO1, installmentMonthDTO2, installmentMonthDTO3, installmentMonthDTO4, installmentMonthDTO5));
    }
}
