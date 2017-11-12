package com.exercise.log.writer;

import com.exercise.log.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogWriter implements LogWriter {

    private static final FileLogWriter instance;
    private static final Logger logger;

    static {
        instance = new FileLogWriter();
        logger = Logger.getLogger(FileLogWriter.class.getName());
    }

    private FileLogWriter() {}

    public static FileLogWriter getInstance() { return instance; }

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
        FileHandler fileHandler = null;
        SimpleFormatter formatter = new SimpleFormatter();

        if ( !(new File(parameters.get("filePath"))).exists() ) {
            try {
                new File(parameters.get("filePath")).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fileHandler = new FileHandler(parameters.get("filePath"), true);
            fileHandler.setLevel(log.getLevel());
            fileHandler.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setLevel(log.getLevel());
        logger.addHandler(fileHandler);
        logger.log(log.getLevel(), log.getMessage());
    }
}
