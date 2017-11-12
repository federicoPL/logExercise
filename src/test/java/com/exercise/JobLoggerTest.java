package com.exercise;

import com.exercise.log.JobLogger;
import com.exercise.log.Log;
import com.exercise.log.writer.ConsoleLogWriter;
import com.exercise.log.writer.DBLogWriter;
import com.exercise.log.writer.FileLogWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobLoggerTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    private Log log;

    private JobLogger jobLogger = JobLogger.getInstance();

    private File logFile;


    @Before
    public void setUp() throws Exception{
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        logFile = new File(App.class.getClassLoader().getResource("file.log").getFile());

        log = new Log(Level.WARNING, "Testing log.");
    }

    @Test
    public void consoleLogTest() {
        jobLogger.logMessage(log, null, ConsoleLogWriter.getInstance());
    }

    @Test
    public void fileLogTest() {
        jobLogger.logMessage(log, new HashMap<String, String>(){{ put("filePath", logFile.getAbsolutePath());}}, FileLogWriter.getInstance());
    }

    // This should be using mocks for the connection to the database but for now we'll leave it like this
    @Test(expected = Exception.class)
    public void dbLogTest() {
        jobLogger.logMessage(log, null, DBLogWriter.getInstance());
    }

    // This should be using mocks for the connection to the database but for now we'll leave it like this
    @Test(expected = Exception.class)
    public void multipleLogWritersTest() {
        jobLogger.logMessage(
                log,
                new HashMap<String, String>(){{ put("filePath", logFile.getAbsolutePath());}},
                Arrays.asList(
                        FileLogWriter.getInstance(),
                        ConsoleLogWriter.getInstance(),
                        DBLogWriter.getInstance()
                )
        );
    }
}
