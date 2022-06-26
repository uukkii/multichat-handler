package ru.netology.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy hh:mm:ss] ");
    private static Logger instance;
    private static final String LOG_PATH = "src/main/resources/log.ini";
    private File logFile = new File(LOG_PATH);

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
            return instance;
        }
        return null;
    }

    public void log(String message, boolean inConsole) {
        Date date = new Date();
        try (FileWriter writer = new FileWriter(logFile, true)) {
            String newLog = dateFormat.format(date) + message + "\n";
            writer.write(newLog);
            writer.flush();
            if (inConsole) {
                System.out.println(newLog);
            }
        } catch (IOException e) {
            log(e.getMessage(), false);
            throw new RuntimeException(e);
        }
    }

}
