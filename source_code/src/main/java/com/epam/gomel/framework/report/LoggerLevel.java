package com.epam.gomel.framework.report;

import org.apache.log4j.Level;

public class LoggerLevel extends Level {
    public static final int TEST_STARTED_INT = FATAL_INT - 1;
    public static final int TEST_FINISHED_INT = FATAL_INT - 2;
    public static final int METHOD_STARTED_INT = FATAL_INT - 3;
    public static final int METHOD_SUCCESS_INT = FATAL_INT - 4;
    public static final int METHOD_FAILED_INT = FATAL_INT - 5;
    public static final int METHOD_SKIPPED_INT = FATAL_INT - 6;
    public static final int CONF_STARTED_INT = FATAL_INT - 7;
    public static final int CONF_SUCCESS_INT = FATAL_INT - 8;
    public static final int CONF_FAILED_INT = FATAL_INT - 9;
    public static final int CONF_SKIPPED_INT = FATAL_INT - 10;
    public static final int SAVE_INT = FATAL_INT - 11;

    public static final LoggerLevel TEST_STARTED = new LoggerLevel(TEST_STARTED_INT, "TEST_START", 0);
    public static final LoggerLevel TEST_FINISHED = new LoggerLevel(TEST_FINISHED_INT, "TEST_FINISHED", 0);

    public static final LoggerLevel METHOD_STARTED = new LoggerLevel(METHOD_STARTED_INT, "METHOD_STARTED", 0);
    public static final LoggerLevel METHOD_SUCCESS = new LoggerLevel(METHOD_SUCCESS_INT, "METHOD_SUCCESS", 0);
    public static final LoggerLevel METHOD_FAILED = new LoggerLevel(METHOD_FAILED_INT, "METHOD_FAILED", 0);
    public static final LoggerLevel METHOD_SKIPPED = new LoggerLevel(METHOD_SKIPPED_INT, "METHOD_SKIPPEDT", 0);

    public static final LoggerLevel CONF_STARTED = new LoggerLevel(CONF_STARTED_INT, "CONF_STARTED", 0);
    public static final LoggerLevel CONF_SUCCESS = new LoggerLevel(CONF_SUCCESS_INT, "CONF_SUCCESS", 0);
    public static final LoggerLevel CONF_FAILED = new LoggerLevel(CONF_FAILED_INT, "CONF_FAILED", 0);
    public static final LoggerLevel CONF_SKIPPED = new LoggerLevel(CONF_SKIPPED_INT, "CONF_SKIPPED", 0);

    public static final LoggerLevel SAVE = new LoggerLevel(SAVE_INT, "SAVE", 0);

    protected LoggerLevel(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

}
