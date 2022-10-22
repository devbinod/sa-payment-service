package edu.miu590.paymentservice.mapper;

import edu.miu590.paymentservice.entity.Payment;
import edu.miu590.paymentservice.model.PaymentRequestDto;
import edu.miu590.paymentservice.model.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentResponseDto toDto(Payment payment);
    Payment toEntity(PaymentRequestDto paymentRequestDto);
    List<PaymentResponseDto> toDtoList(List<Payment> paymentList);

}
