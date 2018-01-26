package nats.example.requestreply.service;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * Starts the NATS Example Request-Reply Service.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        String serviceId = UUID.randomUUID().toString();

        LOGGER.info("Starting NATS Example Request-Reply Service " + serviceId);

        Connection conn = Nats.connect("nats://localhost:4222");

        conn.subscribe("example-service", message -> {
            String replyTo = message.getReplyTo();
            String msg = new String(message.getData());

            LOGGER.info(String.format("Received Message From %s: %s", replyTo, message));

            try {
                if (msg.equalsIgnoreCase("hello")) {
                    conn.publish("example-service", replyTo, "World!".getBytes());
                } else {
                    conn.publish("example-service", replyTo, "I don't understand you!".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
