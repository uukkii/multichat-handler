package ru.netology.common;

import ru.netology.logger.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Helper {
    // Common
    public static final String EXIT_COMMAND = "/exit";
    public static final String SETTING_PATH = "src/main/resources/setting.cfg";
    public static final String HOST = "localhost";
    public static final String IS_CLOSED = " is closed!";

    // Server package
    public static final String PORT_IS_GOTTEN = "Port is gotten from 'setting.cfg'";
    public static final String SERVER_START = "Server is listening on port: ";
    public static final String NEW_HANDLER = "New handler is ran for client: ";
    public static final String EXIT_EVENT = "User quited the chat";

    // Client package
    public static final String NEW_THREAD = "New thread is ran for client: ";
    public static final String ENTER_NAME = "Enter your name: ";
    public static final String WELCOME = "Welcome to ConsoleMultiChat, ";
    public static final String NEW_USER = " has entered the chat!";
    public static final String NEW_SOCKET = "Socket is running for ";
    public static final String USER_LEFT = " is disconnected!";

    public static int getPortFromSettings(Logger logger) {
        try (BufferedReader in = new BufferedReader(new FileReader(SETTING_PATH))) {
            String[] parse = in.readLine().split("=");
            logger.log(PORT_IS_GOTTEN, false);
            return Integer.parseInt(parse[1]);
        } catch (IOException e) {
            logger.log(e.getMessage(), false);
            throw new RuntimeException(e);
        }
    }

}
