package edu.miu590.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfigTopic {


    @Value("${booking.payment-info.service.kafka.topic}")
    private String bookingTopic;


    @Bean
    public NewTopic createBookingTopic(){
        return TopicBuilder.name(bookingTopic)
                .partitions(10)
                .build();
    }
}
