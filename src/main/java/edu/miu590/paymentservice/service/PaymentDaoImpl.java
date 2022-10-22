package edu.miu590.paymentservice.service;

import com.stripe.model.Charge;
import edu.miu590.paymentservice.client.BookingClient;
import edu.miu590.paymentservice.entity.Payment;
import edu.miu590.paymentservice.mapper.PaymentMapper;
import edu.miu590.paymentservice.model.*;
import edu.miu590.paymentservice.repository.PaymentRepository;

import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PaymentDaoImpl implements PaymentDao {

    private StripeService stripeService;
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private BookingClient bookingClient;

    public PaymentDaoImpl(StripeService stripeService, PaymentRepository paymentRepository, PaymentMapper paymentMapper, BookingClient bookingClient) {
        this.stripeService = stripeService;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.bookingClient = bookingClient;
    }

    @Override
    public PaymentResponseDto save(PaymentRequestDto paymentRequestDto) throws FeignException.FeignClientException {

        BookingResponseDto bookingResponseDto = bookingClient.findById(paymentRequestDto.getBookingId());

        Charge charge = stripeService.charge(paymentRequestDto);

        String status = charge == null ? PaymentTypeStatus.PAYMENT_FAILED.toString() : charge.getStatus();
        Payment payment = Payment.builder()
                .bookingId(paymentRequestDto.getBookingId())
                .vehicleId(paymentRequestDto.getVehicleId())
                .totalPrice(paymentRequestDto.getAmount())
                .description(paymentRequestDto.getDescription())
                .paymentType(paymentRequestDto.getTokenType())
                .currency(paymentRequestDto.getCurrency())
                .status(status)
                .build();

        paymentRepository.save(payment);
        bookingResponseDto = bookingClient.updateBookingStatus(BookingUpdateRequestDto.builder()
                .bookingId(bookingResponseDto.getBookingId())
                .bookingStatus(status == PaymentTypeStatus.PAYMENT_FAILED.toString() ?
                        BookingStatus.FAILED : BookingStatus.COMPLETED
                ).build()
        );
        return paymentMapper.toDto(payment);

    }

    @Override
    public List<PaymentResponseDto> findAll() {
        return null;
    }

    @Override
    public PaymentResponseDto findById(String paymentId) {
        return null;
    }


}
