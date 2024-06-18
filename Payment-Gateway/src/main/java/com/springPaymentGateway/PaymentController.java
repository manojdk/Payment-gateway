package com.springPaymentGateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Value("${stripe.api.key}")
	private String stripeSecretKey;

	@PostMapping("/create-checkout-session")
	public ResponseEntity<?> createCheckoutSession(@RequestParam Long amount, @RequestParam String currency) {
		try {
			Stripe.apiKey = stripeSecretKey;

			SessionCreateParams.Builder builder = SessionCreateParams.builder();
			builder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);

			SessionCreateParams.LineItem.PriceData.ProductData productData = new SessionCreateParams.LineItem.PriceData.ProductData.Builder()
					.setName("Sample Product").build();

			SessionCreateParams.LineItem.PriceData priceData = new SessionCreateParams.LineItem.PriceData.Builder()
					.setCurrency(currency).setUnitAmount(amount).setProductData(productData).build();

			SessionCreateParams.LineItem lineItem = new SessionCreateParams.LineItem.Builder().setPriceData(priceData)
					.setQuantity(1L).build();

			builder.addLineItem(lineItem);

			builder.setMode(SessionCreateParams.Mode.PAYMENT);
			builder.setSuccessUrl("http://localhost:8080/success.html");
			builder.setCancelUrl("http://localhost:8080/cancel.html");

			SessionCreateParams params = builder.build();

			Session session = Session.create(params);

			Map<String, Object> responseData = new HashMap<>();
			responseData.put("sessionId", session.getId());
			return ResponseEntity.ok().body(responseData);
		} catch (StripeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
