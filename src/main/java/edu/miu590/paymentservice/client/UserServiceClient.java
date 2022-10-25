package edu.miu590.paymentservice.client;


import edu.miu590.paymentservice.model.UserVerifyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${user.service.feign.name}", url = "${user.service.feign.url}")

public interface UserServiceClient {

    @PostMapping("/verify")
    public Boolean verifyToken(@RequestBody UserVerifyDto userVerifyDto);
}
