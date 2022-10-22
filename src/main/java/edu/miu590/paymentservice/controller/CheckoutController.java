package edu.miu590.paymentservice.controller;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import edu.miu590.paymentservice.service.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@CrossOrigin
public class CheckoutController {
    private StripeService stripeService;

    public CheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }





//    @PostMapping("/charge")
//    public String charge(ChargeRequest chargeRequest, Model model)
//            throws StripeException {
//        chargeRequest.setDescription("Example charge");
//        chargeRequest.setCurrency(Currency.EUR);
//        try {
//            java.security.Security.setProperty("networkaddress.cache.ttl" , "60");
//
//            Charge charge = stripeService.charge(chargeRequest);
//            model.addAttribute("id", charge.getId());
//            model.addAttribute("status", charge.getStatus());
//            model.addAttribute("chargeId", charge.getId());
//            model.addAttribute("balance_transaction", charge.getBalanceTransaction());
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//        }
//
//        return "result";
//    }


}
