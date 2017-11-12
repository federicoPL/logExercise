package com.exercise.log;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Log Entity
 * This class will represent a unique Log
 */
public class Log {

    private final UUID id;
    private final LocalDateTime dateTime;
    private final Level level;
    private final String message;

    public Log(Level level, String message) {
        this.level = level;
        this.message = Objects.requireNonNull(message);
        this.id = UUID.randomUUID();
        this.dateTime = LocalDateTime.now();
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
