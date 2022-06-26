package ru.netology.client;

import ru.netology.logger.Logger;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static ru.netology.common.Helper.*;
public class Client {
    private final Logger logger = Logger.getInstance();
    private final Scanner console = new Scanner(System.in);


    public Client() {
        System.out.println(ENTER_NAME);
        String name = console.nextLine();
        System.out.println(WELCOME + name);
        logger.log(name + NEW_USER, false);

        try {
            int port = getPortFromSettings(logger);
            final Socket socket = new Socket(HOST, port);
            logger.log(NEW_SOCKET + name, false);

            new Thread(new ClientHandler(this, socket)).start();
            logger.log(NEW_THREAD + name, false);

            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                String line;
                while (true) {
                    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    line = console.nextLine();
                    if (EXIT_COMMAND.equals(line)) {
                        out.println(line);
                        logger.log(name + USER_LEFT, true);
                        break;
                    }
                    out.printf("%s %s: %s\n", time, name, line);
                    logger.log(name + ": " + line, false);
                }
            } catch (IOException e) {
                logger.log(e.getMessage(), false);
                throw new RuntimeException(e);
            } finally {
                console.close();
                socket.close();
                logger.log(socket + IS_CLOSED, false);
            }
        } catch (IOException | RuntimeException e) {
            logger.log(e.getMessage(), false);
            throw new RuntimeException(e);
        }
    }

    protected void printMessage(String message) {
        System.out.println(message);
    }

    public static class ClientHandler implements Runnable {
        private final Logger logger = Logger.getInstance();
        private Client client;
        private BufferedReader reader;

        public ClientHandler(Client client, Socket socket) {
            this.client = client;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                logger.log(e.getMessage(), false);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                String line;
                while (true) {
                    line = reader.readLine();
                    client.printMessage(line);
                }
            } catch (IOException e) {
                logger.log(e.getMessage(), false);
                throw new RuntimeException(e);
            }
        }
    }
}
