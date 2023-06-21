//package com.example.calculator.mapper;
//
//import com.example.calculator.dto.InstallmentMonthDTO;
//import com.example.calculator.model.InstallmentMonth;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface InstallmentMonthMapper {
//    @Mapping(target = "paymentNumber", source = "installmentMonth.paymentNumber")
//    @Mapping(target = "monthlyPayment", source = "installmentMonth.monthlyPayment")
//    @Mapping(target = "principalAmount", source = "installmentMonth.principalAmount")
//    @Mapping(target = "interestAmount", source = "installmentMonth.interestAmount")
//    @Mapping(target = "remainingBalance", source = "installmentMonth.remainingBalance")
//    InstallmentMonthDTO installmentMonthToInstalmentMonthDTO(InstallmentMonth installmentMonth);
//}
