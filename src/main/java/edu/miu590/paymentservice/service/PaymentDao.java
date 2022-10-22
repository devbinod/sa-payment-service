package edu.miu590.paymentservice.service;

import edu.miu590.paymentservice.model.PaymentDetailsRequest;
import edu.miu590.paymentservice.model.PaymentRequestDto;
import edu.miu590.paymentservice.model.PaymentResponseDto;
import edu.miu590.paymentservice.model.StripPaymentDetailRequest;

import java.util.List;

public interface PaymentDao {

    PaymentResponseDto save(PaymentRequestDto paymentRequestDto);
    List<PaymentResponseDto> findAll();
    PaymentResponseDto findById(String paymentId);
}
