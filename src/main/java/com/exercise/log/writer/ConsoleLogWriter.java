package com.exercise.log.writer;

import com.exercise.log.Log;

import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConsoleLogWriter implements LogWriter {

    private static final ConsoleLogWriter instance;
    private static final SimpleFormatter formatter;
    private static final Logger logger;

    static {
        instance = new ConsoleLogWriter();
        formatter = new SimpleFormatter();
        logger = Logger.getLogger(ConsoleLogWriter.class.getName());
    }

    private ConsoleLogWriter() {}

    public static ConsoleLogWriter getInstance() { return instance; }

    /**
     * {@inheritDoc}
     * @param log
     */
    @Override
    public void write(Log log) {
        write(log, null);
    }

    /**
     * {@inheritDoc}
     * @param log
     * @param parameters
     */
    @Override
    public void write(Log log, Map<String, String> parameters) {
        logger.setLevel(log.getLevel());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        handler.setLevel(log.getLevel());
        logger.addHandler(handler);
        logger.log(log.getLevel(), log.getMessage());
    }
}
