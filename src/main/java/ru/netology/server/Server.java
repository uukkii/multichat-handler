package ru.netology.server;

import ru.netology.logger.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static ru.netology.common.Helper.*;

public class Server {
    private final Logger logger = Logger.getInstance();
    private Set<ServerHandler> clients = new HashSet<>();


    public void start() {
        int port = getPortFromSettings(logger);
        try {
            final ServerSocket serverSocket = new ServerSocket(port);
            logger.log(SERVER_START + port, true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerHandler handler = new ServerHandler(clientSocket, this);
                clients.add(handler);

                new Thread(handler).start();
                logger.log(NEW_HANDLER + serverSocket, true);
            }
        } catch (IOException e) {
            logger.log(e.getMessage(), false);
            e.getStackTrace();
        }
    }

    protected synchronized void sendMessage(String message) {
        for (ServerHandler client : clients) {
            client.send(message);
        }
    }

    protected Set<ServerHandler> getClients() {
        return clients;
    }
}
