package edu.miu590.paymentservice.entity;

import edu.miu590.paymentservice.model.PaymentType;
import edu.miu590.paymentservice.model.PaymentTypeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
@Builder
public class Payment {

    @Id
    private String id;
    private String bookingId;
    private String vehicleId;
    private String userId;
    private BigDecimal totalPrice;
    private LocalDate createdDate;
    private String paymentType; //card ,paypal
    private String description;
    private String currency;
    private String txEmail;
    private String status;




}
