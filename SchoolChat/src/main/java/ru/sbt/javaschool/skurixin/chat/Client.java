package ru.sbt.javaschool.skurixin.chat;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by скурихин on 08.10.2016.
 */
public class Client {
    // Инициализация логера
    private static final Logger LOGGER = Logger.getLogger(Client.class);
    public static final String COMMAND_DISCONNECT = "disconnect";
    public static final String COMMAND_ACTIVEUSERS = "activeusers";
    public static final String COMMAND_GETMESSAGES = "getmessages";
    private String login;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private BufferedReader consoleReader;


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

    public static void main(String[] args) throws IOException {
        new Client().go();
    }

    private void go() throws IOException {
        LOGGER.info("Write your login: ");
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
        LOGGER.info("Close connection");
    }

    //вводим команды к серверу или новое сообщение
    private void getMessagesFromConsole(PrintWriter writer) throws IOException {
        LOGGER.info("Write one of comands:");
        LOGGER.info("\t" + COMMAND_GETMESSAGES);
        LOGGER.info("\t" + COMMAND_ACTIVEUSERS);
        LOGGER.info("\t" + COMMAND_DISCONNECT);
        String message;
        while ((message = consoleReader.readLine()) != null) {
            message = parseConsoleString(message);
            if (message != null) {
                writer.println(message);
                writer.flush();
            }
            if (COMMAND_DISCONNECT.equals(message)) {
                break;
            }
        }
    }

    private String parseConsoleString(String message) {
        String result;
        switch (message.toLowerCase().replaceAll(" ", "")) {
            case COMMAND_GETMESSAGES:
                result = COMMAND_GETMESSAGES;
                break;
            case COMMAND_ACTIVEUSERS:
                result = COMMAND_ACTIVEUSERS;
                break;
            case COMMAND_DISCONNECT:
                result = COMMAND_DISCONNECT;
                break;
            default:
                result = prepareMessage(message);
                break;
        }
        return result;
    }

    private String prepareMessage(String message) {
        String result;
        String[] splitResult = message.split(">>");
        if (splitResult.length < 2) {
            LOGGER.info("Для отправления сообщения введите \"Имя пользователя\">>\"сообщение\"");
            result = null;
        } else {
            result = message.replaceAll(splitResult[0], splitResult[0].replaceAll(" ", ""));
        }
        return result;
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
                    LOGGER.info(message);
                }
            } catch (IOException e) {
                LOGGER.error("Reading from server finished: " + e);
            }
        }
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
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        if (login == null || socket == null ||
                ((Client) obj).login == null ||
                ((Client) obj).socket == null)
            return false;
        return login.equals(((Client) obj).login) &&
                socket.equals(((Client) obj).socket);
    }
}
