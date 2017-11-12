package com.exercise.log.writer;

import com.exercise.log.Log;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Singleton implementation of {@link LogWriter}
 * This implementation will handle the logic to write a {@link Log} to a certain Database.
 */
public class DBLogWriter implements LogWriter{

    // Actually I don't know if we can use Log4j so I'm executing the statement "manually"
    //but we could use a lof4j.properties file with an appender of type: "org.apache.log4j.jdbc.JDBCAppender"
    private static final String insertStatement;
    private static final DBLogWriter instance;
    private static final BasicDataSource dataSource;
    private static final Properties dataSourceProperties;
    private static Connection connection;

    static {
        insertStatement = "INSERT INTO Log_Values(message, level) VALUES(?, ?)";
        instance = new DBLogWriter();

        dataSource = new BasicDataSource();
        dataSourceProperties = getProperties("dataSource.properties");
        dataSource.setUrl(dataSourceProperties.getProperty("url"));
        dataSource.setDriverClassName(dataSourceProperties.getProperty("driver"));
        dataSource.setUsername(dataSourceProperties.getProperty("user"));
        dataSource.setPassword(dataSourceProperties.getProperty("password"));
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBLogWriter(){}

    public static DBLogWriter getInstance() { return instance; }

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
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insertStatement);
            statement.setString(1, log.getMessage());
            statement.setInt(2, log.getLevel().intValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Helper method to retrieve and load a {@link Properties} from our resources directory
     * @param fileName
     * @return loaded instance of {@link Properties}
     */
    private static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(DBLogWriter.class.getClassLoader().getResource(fileName).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
