/*
 * package com.springPaymentGateway;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.stripe.exception.StripeException; import
 * com.stripe.model.PaymentIntent; import
 * com.stripe.param.PaymentIntentCreateParams;
 * 
 * @Service public class PaymentService {
 * 
 * public PaymentIntent createPaymentIntent(Long amount, String currency) throws
 * StripeException { PaymentIntentCreateParams params =
 * PaymentIntentCreateParams.builder() .setAmount(amount) .setCurrency(currency)
 * .build();
 * 
 * return PaymentIntent.create(params); } }
 */