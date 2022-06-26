package ru.netology.server;

import ru.netology.logger.Logger;

import java.io.*;
import java.net.Socket;

import static ru.netology.common.Helper.*;
public class ServerHandler implements Runnable {
    private final Logger logger = Logger.getInstance();

    private Server server;
    private PrintWriter out;
    private BufferedReader in;

    public ServerHandler(Socket socket, Server server) {
        this.server = server;
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                line = in.readLine();
                if (line.equals(EXIT_COMMAND)) {
                    logger.log(EXIT_EVENT, true);
                    break;
                }
                server.sendMessage(line);
            }
        } catch (IOException e) {
            logger.log(e.getMessage(), false);
            throw new RuntimeException(e);
        }
    }

    public void send(String msg) {
        out.println(msg);
        out.flush();
    }
}
