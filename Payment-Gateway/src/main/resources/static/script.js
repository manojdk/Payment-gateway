// script.js

const stripePublicKey = 'pk_test_51PSvJFHWK6503mtCUO5olEIFn7DlW9SDLcVfFTNxpvFFdwE3SqiCMOxNr9VcoZjwSf3JIRzcDSkGEHc7nH3Xf0WU00B44Km5JY'; // Replace with your actual publishable key
const stripe = Stripe(stripePublicKey);

document.getElementById('checkout-button').addEventListener('click', async () => {
	try {
		const response = await fetch('/api/payments/create-checkout-session?amount=2000&currency=usd', {
			method: 'POST',
		});

		if (!response.ok) {
			throw new Error('Failed to fetch session');
		}

		const sessionData = await response.json();
		console.log('Session data:', sessionData);

		const sessionId = sessionData.sessionId;

		const result = await stripe.redirectToCheckout({
			sessionId: sessionId,
		});

		if (result.error) {
			console.error('Stripe Checkout error:', result.error);
			alert('An error occurred while processing the payment: ' + result.error.message);
		}
	} catch (error) {
		console.error('Error occurred:', error);
		alert('An error occurred while processing the payment');
	}
});
