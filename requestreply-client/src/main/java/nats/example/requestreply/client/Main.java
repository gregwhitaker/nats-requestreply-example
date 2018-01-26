package nats.example.requestreply.client;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts the NATS Example Request-Reply Client.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        LOGGER.info("Starting NATS Example Request-Reply Client");

        Connection conn = Nats.connect("nats://localhost:4222");

        for (int i = 0; i < 10; i++) {
            String msg = "Hello";

            LOGGER.info("SENT: " + msg);

            Message message = conn.request("example-service", msg.getBytes());

            LOGGER.info("RECEIVED: " + new String(message.getData()));
        }

        conn.close();

        LOGGER.info("Stopping NATS Example Request-Reply Client");
    }
}
