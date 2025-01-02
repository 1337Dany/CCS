package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * The ClientManager class is responsible for managing client connections and handling
 * client requests. It implements the Runnable interface to allow execution in a separate thread.
 * The class performs various arithmetic operations based on client messages and updates
 * statistics through the ClientManagerCallback interface.
 */
public class ClientManager implements Runnable {
    private final ClientManagerCallback clientManagerCallback;
    private final int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning = true;

    /**
     * Constructs a ClientManager with the specified port and callback.
     *
     * @param port the port number on which the server will listen for client connections
     * @param clientManagerCallback the callback to handle client manager events
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public ClientManager(int port, ClientManagerCallback clientManagerCallback) throws IOException {
        this.clientManagerCallback = clientManagerCallback;
        this.port = port;
    }

    /**
     * The run method is executed when the thread is started. It sets up the streams
     * for communication with the client and processes client messages in a loop.
     */
    @Override
    public void run() {
        setupStreams();
        while (isRunning) {
            try {
                // Reading message from client
                String message = in.readLine();

                // Splitting message to get the operation and operands
                String[] receivedInfo = message.split(" ");
                if (receivedInfo.length != 3) {
                    sendMessage("ERROR");
                    clientManagerCallback.incrementIncorrectOperations();
                    continue;
                }

                // Performing the operation
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

                // Showing received message
                clientManagerCallback.onReceivedMessage(message);

                // Store statistic data
                clientManagerCallback.incrementComputedRequests();
            } catch (SocketException e) {
                clientManagerCallback.decrementConnectedClients();
                closeConnection();
            } catch (NumberFormatException | ArithmeticException e) {
                sendMessage("ERROR");
                clientManagerCallback.incrementIncorrectOperations();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets up the input and output streams for communication with the client.
     */
    private void setupStreams() {
        try (ServerSocket socket = new ServerSocket(port)) {
            clientSocket = socket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to be sent to the client
     */
    private void sendMessage(String message) {
        out.println(message);
    }

    /**
     * Closes the connection with the client and releases resources.
     */
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