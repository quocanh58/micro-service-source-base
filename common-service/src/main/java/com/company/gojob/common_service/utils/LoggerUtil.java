package com.company.gojob.common_service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";


    public static void error(String message, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(getCallingClass());
        logger.error(RED + "{}" + RESET, message, throwable);
    }

    public static void info(String message) {
        Logger logger = LoggerFactory.getLogger(getCallingClass());
        logger.info(GREEN + "{}" + RESET, message);
    }

    public static void debug(String message) {
        Logger logger = LoggerFactory.getLogger(getCallingClass());
        logger.debug(BLUE + "{}" + RESET, message);
    }

    public static void warning(String message) {
        Logger logger = LoggerFactory.getLogger(getCallingClass());
        logger.warn(YELLOW + "{}" + RESET, message);
    }

    private static Class<?> getCallingClass() {
        return new Throwable().getStackTrace()[2].getClassName().getClass();
    }
}
