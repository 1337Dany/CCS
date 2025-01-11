package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test implements Runnable {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        // Change number of clients
        int AMOUNT_OF_SIMULTANEOUS_CLIENTS = 100;

        // Список Future объектов
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_SIMULTANEOUS_CLIENTS; i++) {
            futures.add(executor.submit(new Test()));
        }

        // Проверяем, завершились ли задачи
        for (Future<?> future : futures) {
            try {
                future.get(); // Блокирует выполнение до завершения задачи
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Все задачи завершены.");
        executor.shutdown();
    }

    @Override
    public void run() {
        // change port
        int port = 12345;


        // change number of cycles
        int CYCLES = 100;

        for (int i = 0; i < CYCLES; i++) {
            // UDP
            DatagramSocket socket_UDP = null;
            try {
                socket_UDP = new DatagramSocket();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

            // send UDP
            String discoveryMessage = "CCS DISCOVER";
            byte[] sendData = discoveryMessage.getBytes();

            DatagramPacket send_packet = null;
            try {
                send_packet = new DatagramPacket(
                        sendData, sendData.length,
                        InetAddress.getByName("localhost"), port
                );
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            try {
                socket_UDP.send(send_packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // response UDP
            DatagramPacket receive_packet = new DatagramPacket(new byte[9], 9);
            try {
                socket_UDP.receive(receive_packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(new String(receive_packet.getData()));

            // address
            InetAddress server_address = receive_packet.getAddress();
            socket_UDP.close();


            // TCP
            Socket socket_TCP = null;
            try {
                socket_TCP = new Socket(server_address, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(socket_TCP.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintWriter out = null;
            try {
                out = new PrintWriter(socket_TCP.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            out.println("ADD 2 1");
            String add1 = null;
            try {
                add1 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ADD result: " + add1); // 3

            out.println("SUB 2 1");
            String sub1 = null;
            try {
                sub1 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("SUB result: " + sub1); // 1

            out.println("MUL 2 1");
            String mul1 = null;
            try {
                mul1 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("MUL result: " + mul1); // 2

            out.println("DIV 20 10");
            String div1 = null;
            try {
                div1 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("DIV result: " + div1); // 2

            out.println("HI 1000 1000");
            String hi0 = null;
            try {
                hi0 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("HI result: " + hi0); // ERROR

            String div100 = null;
            out.println(div100);
            try {
                div100 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("DIV result: " + div100); // ERROR


            out.println("ADD 100 100 100");
            String add3 = null;
            try {
                add3 = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ADD result: " + add3); // ERROR

            try {
                socket_UDP.close();
                socket_TCP.close();
                in.close();
                out.close();
            } catch (IOException ignored) {
            }

            // Sum: 8. Errors: 3. (per ONE cycle)
        }
    }
}
// Good luck!