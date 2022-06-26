package ru.netology;

import ru.netology.server.Server;

public class StartServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
