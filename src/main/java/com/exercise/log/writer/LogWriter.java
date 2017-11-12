package com.exercise.log.writer;


import com.exercise.log.Log;

import java.util.Map;

/**
 * A LogWriter should be implemented when ever we want to specify an alternative Output for a {@link Log}
 * @see ConsoleLogWriter
 * @see DBLogWriter
 * @see FileLogWriter
 */
public interface LogWriter {

    /**
     * Method with the corresponding logic to write a {@link Log} instance
     * @param log
     */
    void write(Log log);

    /**
     * Method with the corresponding logic to write a {@link Log} instance
     * @param log
     */
    void write(Log log, Map<String, String> parameters);
}

