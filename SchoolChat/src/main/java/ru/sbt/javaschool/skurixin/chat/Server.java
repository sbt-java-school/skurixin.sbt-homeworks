package ru.sbt.javaschool.skurixin.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Created by скурихин on 07.10.2016.
 */
public class Server {
    private static final int MAX_COUNT_OF_CLIENTS = 10;
    public static final int PORT = 1234;
    private static Map<Client, ArrayList<Object>> messagesMap = new ConcurrentHashMap<>();
    private static final Client SYSTEM = new Client("SYSTEM");

    public static void main(String[] args) throws IOException {
        System.out.println("Chat started!");
        try (ServerSocket server = new ServerSocket(PORT)) {
            ExecutorService service = Executors.newFixedThreadPool(MAX_COUNT_OF_CLIENTS);
            while (true) {
                try {
                    Socket clientSocket = server.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String login = reader.readLine();
                    Client client = new Client(login, clientSocket, new PrintWriter(clientSocket.getOutputStream()), reader);

                    checkSameLogins(login, client);

                    //уведомляем текущих пользователей о присоединении нового пользователя
                    notifyClients(client.getLogin() + " has entered to school chat!");
                    //создаем для нового пользователя историю сообщений
                    messagesMap.put(client, new ArrayList<>());
                    System.out.println(client.getLogin() + " connected");

                    //добавляем прослушку команд от клиента
                    service.submit(new Notification(client));
                } catch (Exception e) {
                    System.err.println("Exception!");
                }
            }
        }
    }

    //Проверяем логин нового пользователя на совпадение с уже существующими
    //если совпадение, то добавляем к его логину "[%d]"
    private static void checkSameLogins(String login, Client client) {
        boolean anyMatch = messagesMap.entrySet().stream()
                .anyMatch(p -> p.getKey().getLogin().equals(client.getLogin()));
        if (anyMatch) {
            Pattern compile = Pattern.compile("^" + login + "\\[\\d+\\]$");
            long count = messagesMap.entrySet().stream()
                    .filter(p -> compile.matcher(p.getKey().getLogin()).matches()).count();

            client.setLogin(client.getLogin() + "[" + (count + 1) + "]");
            sendToUser(SYSTEM, client,
                    "Your login is same with another client in SchoolChat. Your new login is "
                            + client.getLogin());
        }
    }

    //метод уведомления всех клиентов о каком-либо событии
    private static void notifyClients(String message) {
        for (Client client : messagesMap.keySet()) {
            sendToUser(SYSTEM, client, message);
        }
    }

    //метод отправляющий сообщение пользователю to
    private static void sendToUser(Client from, Client to, String message) {
        to.getPrintWriter().println(from.getLogin() + " >> " + message);
        to.getPrintWriter().flush();
    }

    //класс, слушающий сообщения от клиента
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
                while ((s = reader.readLine()) != null) {
                    switch (s) {
                        case "getmessages":
                            System.out.println(client.getLogin() + " required his messages");
                            String answerToClient = messagesMap.get(client).toString();
                            System.out.println("\t" + answerToClient);
                            sendToUser(SYSTEM, client, answerToClient);
                            break;
                        case "activeusers":
                            System.out.println(client.getLogin() + " required active users");
                            String answerToClient1 = messagesMap.keySet().toString();
                            System.out.println("\t" + answerToClient1);
                            sendToUser(SYSTEM, client, answerToClient1);
                            break;
                        case "disconnect":
                            messagesMap.remove(client);
                            client.getSocket().close();
                            String disconnectMessage = client.getLogin() + " has disconnected from SchoolChat";
                            System.out.println(disconnectMessage);
                            notifyClients(disconnectMessage);
                            break;
                        //отправляем сообщение
                        default:
                            String[] splitResult = s.split(">>", 2);
                            System.out.println("From " + client.getLogin() + " to " + splitResult[0] + ":\n\t" + splitResult[1]);
                            //Client clientTo = getClientByLogin(splitResult[0]);
                            Optional<Client> first = messagesMap.keySet().stream()
                                    .filter(p -> p.getLogin().equals(splitResult[0]))
                                    .findFirst();
                            if (first.isPresent()) {
                                messagesMap.get(first.get()).add(client.getLogin() + " >> " + splitResult[1]);
                                sendToUser(client, first.get(), splitResult[1]);
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                System.err.println(client.getLogin() + e.getMessage());
            }
        }
    }


}
