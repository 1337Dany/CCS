package data.datasource;

import data.Data;
import domain.ServerCallback;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The TCPServer class handles incoming client connections and processes their requests using TCP.
 * It listens for client connections and delegates the handling of each client to a separate thread.
 */
public class TCPServer implements ClientManagerCallback {
    private final Data data;
    private final int port;
    private final ServerCallback serverCallback;
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Constructs a TCPServer with the specified port, callback, and data.
     *
     * @param port           the port number on which the server will listen for connections
     * @param serverCallback the callback to handle server events
     * @param data           the data object to maintain server statistics
     */
    public TCPServer(int port, ServerCallback serverCallback, Data data) {
        this.port = port;
        this.serverCallback = serverCallback;
        this.data = data;

        start();
    }

    /**
     * Starts the server to listen for client connections.
     * For each client connection, it increments the connected clients count and delegates the handling to a ClientManager.
     */
    private void start() {
        try (ServerSocket socket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = socket.accept();
                data.incrementConnectedClients();
                executorService.execute(new ClientManager(clientSocket, this));
            }
        } catch (IOException | NullPointerException e) {
            serverCallback.onConnectionError("ERROR");
            e.printStackTrace();
        }
    }

    /**
     * Decrements the count of connected clients by one.
     */
    @Override
    public void decrementConnectedClients() {
        data.decrementConnectedClients();
    }

    /**
     * Increments the count of computed requests by one.
     */
    @Override
    public void incrementComputedRequests() {
        data.incrementComputedRequests();
    }

    /**
     * Increments the count of addition operations by one.
     */
    @Override
    public void incrementAddOperations() {
        data.incrementAddOperations();
    }

    /**
     * Increments the count of subtraction operations by one.
     */
    @Override
    public void incrementSubOperations() {
        data.incrementSubOperations();
    }

    /**
     * Increments the count of multiplication operations by one.
     */
    @Override
    public void incrementMulOperations() {
        data.incrementMulOperations();
    }

    /**
     * Increments the count of division operations by one.
     */
    @Override
    public void incrementDivOperations() {
        data.incrementDivOperations();
    }

    /**
     * Increments the count of incorrect operations by one.
     */
    @Override
    public void incrementIncorrectOperations() {
        data.incrementIncorrectOperations();
    }

    /**
     * Adds the specified result to the sum of results.
     *
     * @param result the result to be added to the sum of results
     */
    @Override
    public void sumResults(int result) {
        data.addToSumOfResults(result);
    }
}