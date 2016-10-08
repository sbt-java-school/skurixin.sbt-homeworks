package ru.sbt.javaschool.skurixin.chat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.SocketHandler;

/**
 * Created by скурихин on 08.10.2016.
 */
public class Client {
    private String login;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Client(String login) {
        this.login = login;
    }

    public Client(String login, Socket socket, PrintWriter printWriter, BufferedReader bufferedReader) {
        this.login = login;
        this.socket = socket;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Write your login: ");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String currentLogin = consoleReader.readLine().replaceAll(" ","");
        try (Socket server = new Socket(InetAddress.getLocalHost(), 1234);
             BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
             PrintWriter writer = new PrintWriter(server.getOutputStream())) {
            writer.println(currentLogin);
            writer.flush();
            new Thread(new ReaderRunnable(reader)).start();
            System.out.println("Write your comands:");
            String message;
            while ((message = consoleReader.readLine()) != null) {
                if(message.toLowerCase().replaceAll(" ","").equals("getmessages")) {
                    message="getmessages";
                }
                else {
                    String[] splitResult = message.split(">>");
                    if (splitResult.length < 2) {
                        System.out.println("Для отправления сообщения введите \"Имя пользователя\">>\"сообщение\"");
                        continue;
                    }
                    message=message.replaceAll(splitResult[0],splitResult[0].replaceAll(" ",""));
                }
                writer.println(message);
                writer.flush();
            }
        }
    }

    private static class ReaderRunnable implements Runnable {
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
                e.printStackTrace();
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

}
