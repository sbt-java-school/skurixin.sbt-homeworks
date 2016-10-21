package ru.sbt.skurixin.magic_woman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by скурихин on 14.10.2016.
 */
public class Visitor {

    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            System.out.println("Welcome to our MagicPlace");
            new Thread(new Listener(reader)).start();
            ReadCommands(writer);
        }
    }

    private static void ReadCommands(PrintWriter writer) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = consoleReader.readLine()) != null) {
            writer.println(line);
            writer.flush();
        }
    }

    private static class Listener implements Runnable {

        private final BufferedReader reader;

        public Listener(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
