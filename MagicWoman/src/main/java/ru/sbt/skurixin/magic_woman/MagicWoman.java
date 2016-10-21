package ru.sbt.skurixin.magic_woman;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by скурихин on 14.10.2016.
 */
public class MagicWoman {
    public static final int MAX_MAGIC_NUMBER = 15;
    public static final int PORT = 1234;
    public static final int N_USERS = 10;
    private static final Random random = new Random();
    //private static Map<Socket, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(N_USERS);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try{
                    Socket client = serverSocket.accept();
                    service.submit(new Guessing(client, random.nextInt(MAX_MAGIC_NUMBER)));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static class Guessing implements Runnable {

        private int magicNumber;
        private final Socket client;

        public Guessing(Socket client, int i) {
            this.client = client;
            this.magicNumber = i;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter writer = new PrintWriter(client.getOutputStream())) {
                writer.println("Попробуй угадай!");
                writer.flush();
                String line;
                while ((line = reader.readLine()) != null) {
                    int userNumber = Integer.parseInt(line);
                    if (userNumber == magicNumber) {
                        writer.println("Excellent! You are right!");
                        writer.println("Try one more time");
                        magicNumber=random.nextInt(MAX_MAGIC_NUMBER);
                    } else {
                        if (userNumber < magicNumber) {
                            writer.println("Too few");
                        } else {
                            writer.println("Too much");
                        }
                    }
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
