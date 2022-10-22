package edu.miu590.paymentservice.client;


import edu.miu590.paymentservice.model.BookingResponseDto;
import edu.miu590.paymentservice.model.BookingUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "${booking.service.feign.name}", url = "${booking.service.feign.url}")
public interface BookingClient {


    @GetMapping("/bookings/{id}")
    BookingResponseDto findById(@PathVariable String id);
    @PutMapping("/bookings/update/bookingstatus")
    BookingResponseDto updateBookingStatus(BookingUpdateRequestDto bookingUpdateRequestDto);


}
