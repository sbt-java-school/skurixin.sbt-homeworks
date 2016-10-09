package ru.sbt.javaschool.skurixin.chat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by скурихин on 08.10.2016.
 */
public class Client {
    private String login;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private BufferedReader consoleReader;

    public static void main(String[] args) throws IOException {
        new Client().go();
    }

    private void go() throws IOException {
        System.out.println("Write your login: ");
        String currentLogin = consoleReader.readLine().replaceAll(" ", "");
        try (Socket server = new Socket(InetAddress.getLocalHost(), 1234);
             BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
             PrintWriter writer = new PrintWriter(server.getOutputStream())) {

            //отправляем логин на сервер
            writer.println(currentLogin);
            writer.flush();

            //слушаем сообщения от сервера
            new Thread(new ReaderRunnable(reader)).start();
            getMessagesFromConsole(writer);
        }
        System.out.println("Close connection");
    }

    //вводим команды к серверу или новое сообщение
    private void getMessagesFromConsole(PrintWriter writer) throws IOException {
        System.out.println("Write one of comands:");
        System.out.println("\tgetMessages");
        System.out.println("\tactiveUsers");
        System.out.println("\tdisconnect");
        String message;
        while ((message = consoleReader.readLine()) != null) {
            message = parseConsoleString(message);
            if (message != null) {
                writer.println(message);
                writer.flush();
            }
            if (message.equals("disconnect")) {
                break;
            }
        }
    }

    private String parseConsoleString(String message) {
        switch (message.toLowerCase().replaceAll(" ", "")) {
            case "getmessages":
                message = "getmessages";
                break;
            case "activeusers":
                message = "activeusers";
                break;
            case "disconnect":
                message = "disconnect";
                break;
            default:
                String[] splitResult = message.split(">>");
                if (splitResult.length < 2) {
                    System.out.println("Для отправления сообщения введите \"Имя пользователя\">>\"сообщение\"");
                    message = null;
                } else {
                    message = message.replaceAll(splitResult[0], splitResult[0].replaceAll(" ", ""));
                }
                break;
        }
        return message;
    }

    private class ReaderRunnable implements Runnable {
        private final BufferedReader reader;

        public ReaderRunnable(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Reading from server finished");
            }
        }
    }

    public Client(String login) {
        this.login = login;
    }

    public Client(String login, Socket socket, PrintWriter printWriter, BufferedReader bufferedReader) {
        this.login = login;
        this.socket = socket;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
    }

    public Client() {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public int hashCode() {
        return login.hashCode() + socket.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Client &&
                login.equals(((Client) obj).login) &&
                socket.equals(((Client) obj).socket));
    }
}
