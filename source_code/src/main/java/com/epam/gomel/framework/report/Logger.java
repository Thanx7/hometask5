package com.epam.gomel.framework.report;

import org.apache.log4j.Priority;

public class Logger {

    public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("com.epam.gomel");

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void fatal(String message, Throwable t) {
        logger.fatal(message, t);
    }

    public static void trace(String message) {
        logger.trace(message);
    }

    public static void trace(String message, Throwable t) {
        logger.trace(message, t);
    }

    public static void save(String pathToFile) {
        Logger.log(LoggerLevel.SAVE, pathToFile);
    }

    public static void log(Priority priority, String message, Throwable t) {
        logger.log(priority, message, t);
    }

    public static void log(Priority priority, String message) {
        logger.log(priority, message);
    }

}
