import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import static spark.Spark.post;

public class TestStripe {

    private static final String apiSecretKey = "sk_test_51KvI9eFR8eNchoCRhu66maqcesQSNYUjL3Ia2LxcpCH4Q9n9817FR29X5Rr7whMp3a9xsEUQpkNH5XqGDjzDugwc00XIKNMbhk";

    public static void main(String[] args) throws StripeException {
        initApi();
        runTest();
    }

    private static void initApi() {
        Stripe.apiKey = apiSecretKey;
    }

    private static void runTest() throws StripeException {
        Customer customer = createCustomer("", "");
        PaymentIntent paymentIntent = createPaymentIntent(customer, 100L);
        createSessionOneTimePayment();

    }

    private static Session createSessionOneTimePayment() throws StripeException {

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("https://example.com/success")
                            .setCancelUrl("https://example.com/cancel")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("usd")
                                                            .setUnitAmount(2000L)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("T-shirt")
                                                                            .build())
                                                            .build())
                                            .build())
                            .build();

            Session session = Session.create(params);
            session.getUrl();
            return session;
    }

    private static PaymentIntent createPaymentIntent(Customer customer, long amount) throws StripeException {
        PaymentIntentCreateParams.PaymentMethodData paymentMethodData = PaymentIntentCreateParams.PaymentMethodData.builder().build();
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCustomer(customer.getId())
                .setAmount(amount)
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;

    }

    private static Customer createCustomer(String email, String name) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(email)
                .setName(name)
                .setDescription(String.format("%s,%s","test customer for email", email))
                .build();
        Customer customer = Customer.create(params);
        return customer;
    }
}
