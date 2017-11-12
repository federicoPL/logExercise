package com.exercise.log;

import com.exercise.log.writer.LogWriter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class will be in charge of Logging our {@link Log} entity to the corresponding output.
 * The output 'type' will depend on the implementation of the {@link LogWriter} that the class receives on construction.
 */
public class JobLogger {

    private static final JobLogger instance;

    static {
        instance = new JobLogger();
    }

    private JobLogger() {}

    public static JobLogger getInstance() { return instance; }

    /**
     * Writes the {@link Log} entity to the corresponding output
     * @param logWriter
     * @param log
     */
    public void logMessage( Log log, Map<String, String> parameters, LogWriter logWriter) { logMessage(log, parameters, Collections.singletonList(logWriter)); }

    /**
     * Writes the {@link Log} entity to the corresponding output
     * @param log {@link Log} entity to be logged
     * @param parameters map with a set of parameters
     * @param logWriter Implementation
     */
    public void logMessage(Log log, Map<String, String> parameters, List<LogWriter> logWriter) { logWriter.forEach(writer -> writer.write(log, parameters)); }

}