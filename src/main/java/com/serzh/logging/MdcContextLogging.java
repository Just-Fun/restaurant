package logging.extended.mdc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

public class MdcContextLogging {

    private static final Logger logger = LogManager.getLogger(MdcContextLogging.class);

    public static void main(String[] args) {
        String username = "admin";
        String sessionID = UUID.randomUUID().toString();

        ThreadContext.put("username", username);
        ThreadContext.put("sessionID", sessionID);

        logger.info("Login successful");

        ThreadContext.clearMap();

        logger.info("Login empty");
    }
}
