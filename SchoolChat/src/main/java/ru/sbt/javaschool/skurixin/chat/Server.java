package ru.sbt.javaschool.skurixin.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by скурихин on 07.10.2016.
 */
public class Server {
    private static final int MAX_COUNT_OF_CLIENTS = 10;
    public static final int PORT = 1234;
    private static Map<Client, ArrayList<String>> messagesMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            ExecutorService service = Executors.newFixedThreadPool(MAX_COUNT_OF_CLIENTS);
            while (true) {
                try {
                    Socket clientSocket = server.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String login = reader.readLine();
                    notifyAboutNewClient(login);
                    Client client = new Client(login, clientSocket, new PrintWriter(clientSocket.getOutputStream()), reader);
                    messagesMap.put(client, new ArrayList<>());
                    System.out.println(login + " connected");
                    service.submit(new Notification(client));
                } catch (Exception e) {
                    System.err.println("Exception!");
                }
            }
        }
    }

    private static void notifyAboutNewClient(String login) {
        Client system = new Client("SYSTEM");
        for (Client client : messagesMap.keySet()) {
            sendToUser(system, client, login + " entered to school chat!");
        }
    }

    private static void sendToUser(Client from, Client to, String message) {
        to.getPrintWriter().println(from.getLogin() + " >> " + message);
        to.getPrintWriter().flush();
//        try {
//            PrintWriter printWriter = new PrintWriter(to.getSocket().getOutputStream());
//            printWriter.write(from.getLogin() + " >> " + message);
//            printWriter.flush();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
    }

    private static class Notification implements Runnable {
        private final BufferedReader reader;
        private final Client client;

        public Notification(Client client) {
            this.reader = client.getBufferedReader();
            this.client = client;

        }

        @Override
        public void run() {
            String s;
            try {
                System.out.println("Listening");
                while ((s = reader.readLine()) != null) {
                    String[] splitResult = s.split(">>", 2);
                    System.out.println("From " + client.getLogin() + " to " + splitResult[0] + ":\n\t" + splitResult[1]);
                    Client clientTo = getClientByLogin(splitResult[0]);
                    messagesMap.get(clientTo).add(splitResult[1]);
                    sendToUser(client, clientTo, splitResult[1]);
                }
            } catch (IOException e) {
                System.err.println("Exception " + client.getLogin());
                System.err.println(e.getMessage());
            }
        }

        public static Client getClientByLogin(String s) {
            for (Client client : messagesMap.keySet()) {
                if (client.getLogin().equals(s)) {
                    return client;
                }
            }
            return null;
        }
    }


}
