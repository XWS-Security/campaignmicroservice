package org.nistagram.campaignmicroservice.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerServiceImpl implements LoggerService {
    private final Logger logger;

    public LoggerServiceImpl(Class<?> parentClass) {
        this.logger = LoggerFactory.getLogger(parentClass);
    }

    @Override
    public void logTokenException(String message) {
        logger.error("Token issue: {message: {} }", message);
    }

}
