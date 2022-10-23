package edu.miu590.paymentservice.controller;
import com.stripe.exception.StripeException;
import edu.miu590.paymentservice.api.PaymentsApi;
import edu.miu590.paymentservice.model.PaymentDetailsRequest;
import edu.miu590.paymentservice.model.PaymentRequestDto;
import edu.miu590.paymentservice.model.PaymentResponseDto;
import edu.miu590.paymentservice.model.StripPaymentDetailRequest;
import edu.miu590.paymentservice.service.PaymentDao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
public class PaymentController implements PaymentsApi {


    private PaymentDao paymentDao;

    public PaymentController(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public ResponseEntity<List<PaymentResponseDto>> getAllPayment() {
        return PaymentsApi.super.getAllPayment();
    }

    @Override
    public ResponseEntity<PaymentResponseDto> getPaymentById(String id) {
        return PaymentsApi.super.getPaymentById(id);
    }

    @Override
    public ResponseEntity<PaymentResponseDto> savePayment(PaymentRequestDto paymentRequestDto) {

        return ResponseEntity.ok(paymentDao.save(paymentRequestDto));
    }



}
