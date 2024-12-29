package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;

public class ClientManager implements Runnable {
    private final ClientManagerCallback clientManagerCallback;
    private final int port;
    private final String ip;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientManager(String ip, int port, ClientManagerCallback clientManagerCallback) throws IOException {
        this.clientManagerCallback = clientManagerCallback;
        this.ip = ip;
        this.port = port;

        setupConnection();
    }

    @Override
    public void run() {
        setupStreams();

        while (true) {
            try {
                //  Reading message from client
                String message = in.readLine();

                //  Splitting message to get the operation and operands
                String[] receivedInfo = message.split(" ");

                //  Performing the operation
                if (receivedInfo[0].equals(CalculationPrefixes.ADDITION.getPrefix())) {
                    sendMessage(String.valueOf(Integer.parseInt(receivedInfo[1]) + Integer.parseInt(receivedInfo[2])));
                } else if (receivedInfo[0].equals(CalculationPrefixes.SUBTRACTION.getPrefix())) {
                    sendMessage(String.valueOf(Integer.parseInt(receivedInfo[1]) - Integer.parseInt(receivedInfo[2])));
                } else if (receivedInfo[0].equals(CalculationPrefixes.MULTIPLICATION.getPrefix())) {
                    sendMessage(String.valueOf(Integer.parseInt(receivedInfo[1]) * Integer.parseInt(receivedInfo[2])));
                } else if (receivedInfo[0].equals(CalculationPrefixes.DIVISION.getPrefix())) {
                    sendMessage(String.valueOf(Integer.parseInt(receivedInfo[1]) / Integer.parseInt(receivedInfo[2])));
                } else {
                    sendMessage("ERROR");
                }

                //  Showing received message
                clientManagerCallback.onReceivedMessage(message);

                //  Store statistic data

            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (NumberFormatException e) {
                sendMessage("ERROR");
            }
        }
    }

    private void setupConnection() throws IOException {
        socket = new Socket(ip, port);
    }

    private void setupStreams() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        out.println(message);
    }
}
