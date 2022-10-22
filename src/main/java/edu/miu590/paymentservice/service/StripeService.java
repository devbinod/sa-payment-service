package edu.miu590.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import edu.miu590.paymentservice.model.PaymentRequestDto;
import edu.miu590.paymentservice.model.PaymentResponseDto;
import edu.miu590.paymentservice.model.StripPaymentDetailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {


    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge charge(PaymentRequestDto stripPaymentDetailRequest){

            try{
                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount", stripPaymentDetailRequest.getAmount());
                chargeParams.put("currency", stripPaymentDetailRequest.getCurrency());
                chargeParams.put("description", stripPaymentDetailRequest.getDescription());
                chargeParams.put("source", stripPaymentDetailRequest.getToken());
                 Charge charge = Charge.create(chargeParams);
                 return charge;
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }

    }

    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }
    public Charge chargeNewCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

}
