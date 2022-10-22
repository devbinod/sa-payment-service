package edu.miu590.paymentservice.util;


import com.stripe.exception.StripeException;
import edu.miu590.paymentservice.exception.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StripeException.class})
    public ResponseEntity<ErrorResponse> badRequestException(StripeException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({FeignException.class})
    public ResponseEntity<ErrorResponse> feignClientException(StripeException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }
}
