package com.serzh.logging;

import com.serzh.logging.model2.RequestTrackFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;

import java.util.UUID;

import static com.serzh.logging.Constants.X_SERZH_TRACKING_ID;

//git clone https://bitbucket.org/randriyanov/java-logging.git
public class MainLogging {

    public static final Logger logger = LogManager.getLogger(MainLogging.class);

    public static void main(String[] args) {
       /* String username = "admin";
        String sessionID = UUID.randomUUID().toString();

        ThreadContext.put("username", username);
        ThreadContext.put("sessionID", sessionID);

        logger.info("Login successful");

        ThreadContext.clearMap();

        logger.info("Login empty");*/
        String sessionID = UUID.randomUUID().toString();
        ThreadContext.put(X_SERZH_TRACKING_ID, sessionID);

        logger.info("Login successful");

        ThreadContext.clearMap();

        logger.info("Login empty");

//        MDC.put(X_SERZH_TRACKING_ID, RequestTrackFilter.getId());
        MDC.put(X_SERZH_TRACKING_ID, sessionID);
        logger.info("Before");
        MDC.clear();
        logger.info("After");

    }
}
