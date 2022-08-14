package com.handlertest.demo;

import com.stripe.Stripe;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;

@Component
public class EvenHandler {


//    Stripe.apiKey = "sk_test_51KvI9eFR8eNchoCRhu66maqcesQSNYUjL3Ia2LxcpCH4Q9n9817FR29X5Rr7whMp3a9xsEUQpkNH5XqGDjzDugwc00XIKNMbhk";

    // Using the Spark framework (http://sparkjava.com)
    public Object handle(Request request, Response response) {
        String payload = request.body();

        System.out.println("Got payload: " + payload);

        response.status(200);
        return "";
    }
}
