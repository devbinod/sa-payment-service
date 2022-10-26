package edu.miu590.paymentservice.service;

import com.stripe.model.Charge;
import edu.miu590.paymentservice.client.BookingClient;
import edu.miu590.paymentservice.entity.Payment;
import edu.miu590.paymentservice.mapper.PaymentMapper;
import edu.miu590.paymentservice.model.*;
import edu.miu590.paymentservice.repository.PaymentRepository;

import edu.miu590.paymentservice.util.AppUtil;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class PaymentDaoImpl implements PaymentDao {

    private StripeService stripeService;
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private BookingClient bookingClient;
    private KafkaTemplate<Object,Object> kafkaTemplate;



    @Value("${booking.payment-info.service.kafka.topic}")
    private String bookingTopic;

    public PaymentDaoImpl(StripeService stripeService,
                          PaymentRepository paymentRepository,
                          PaymentMapper paymentMapper,
                          BookingClient bookingClient,
                          KafkaTemplate<Object, Object> kafkaTemplate) {
        this.stripeService = stripeService;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.bookingClient = bookingClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public PaymentResponseDto save(PaymentRequestDto paymentRequestDto) throws FeignException.FeignClientException {

//        BookingResponseDto bookingResponseDto = bookingClient.findById(paymentRequestDto.getBookingId());

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
                .userId(AppUtil.getCurrentUser())
                .build();

        paymentRepository.save(payment);


      BookingUpdateRequestDto bookingUpdateRequestDto =  BookingUpdateRequestDto.builder()
                .bookingId(paymentRequestDto.getBookingId())
                .bookingStatus(Objects.equals(status, PaymentTypeStatus.PAYMENT_FAILED.toString()) ?
                        BookingStatus.FAILED : BookingStatus.COMPLETED
                )
              .email(paymentRequestDto.getEmail())
              .totalPrice(paymentRequestDto.getAmount())
              .build();

//        bookingResponseDto = bookingClient.updateBookingStatus(BookingUpdateRequestDto.builder()
//                .bookingId(bookingResponseDto.getBookingId())
//                .bookingStatus(Objects.equals(status, PaymentTypeStatus.PAYMENT_FAILED.toString()) ?
//                        BookingStatus.FAILED : BookingStatus.COMPLETED
//                ).build()
//        );

        kafkaTemplate.send(bookingTopic,bookingUpdateRequestDto);
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
