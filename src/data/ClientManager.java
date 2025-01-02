package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ClientManager implements Runnable {
    private final ClientManagerCallback clientManagerCallback;
    private final int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning = true;

    public ClientManager(int port, ClientManagerCallback clientManagerCallback) throws IOException {
        this.clientManagerCallback = clientManagerCallback;
        this.port = port;
    }

    @Override
    public void run() {
        setupStreams();
        while (isRunning) {
            try {
                //  Reading message from client
                String message = in.readLine();

                //  Splitting message to get the operation and operands
                String[] receivedInfo = message.split(" ");
                if (receivedInfo.length != 3) {
                    sendMessage("ERROR");
                    clientManagerCallback.incrementIncorrectOperations();
                    continue;
                }

                //  Performing the operation
                if (receivedInfo[0].equals(CalculationPrefixes.ADDITION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) + Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    clientManagerCallback.incrementAddOperations();
                    clientManagerCallback.sumResults(result);
                } else if (receivedInfo[0].equals(CalculationPrefixes.SUBTRACTION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) - Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    clientManagerCallback.incrementSubOperations();
                    clientManagerCallback.sumResults(result);
                } else if (receivedInfo[0].equals(CalculationPrefixes.MULTIPLICATION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) * Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    clientManagerCallback.incrementMulOperations();
                    clientManagerCallback.sumResults(result);
                } else if (receivedInfo[0].equals(CalculationPrefixes.DIVISION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) / Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    clientManagerCallback.incrementDivOperations();
                    clientManagerCallback.sumResults(result);
                } else {
                    sendMessage("ERROR");
                    clientManagerCallback.incrementIncorrectOperations();
                }

                //  Showing received message
                clientManagerCallback.onReceivedMessage(message);

                //  Store statistic data
                clientManagerCallback.incrementComputedRequests();
            } catch (SocketException e) {
                clientManagerCallback.decrementConnectedClients();
                closeConnection();
            } catch (NumberFormatException | ArithmeticException e) {
                sendMessage("ERROR");
                clientManagerCallback.incrementIncorrectOperations();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupStreams() {
        try (ServerSocket socket = new ServerSocket(port)) {
            clientSocket = socket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        out.println(message);
    }

    private void closeConnection() {
        try {
            isRunning = false;
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
