package data.datasource;

import data.Data;
import domain.ServerCallback;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements ClientManagerCallback {
    private final Data data;
    private final int port;
    private final ServerCallback serverCallback;
    private static final ExecutorService executorSevice = Executors.newCachedThreadPool();

    public TCPServer(int port, ServerCallback serverCallback, Data data) {
        this.port = port;
        this.serverCallback = serverCallback;
        this.data = data;

        start();
    }

    private void start() {

        try (ServerSocket socket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = socket.accept();
                data.incrementConnectedClients();
                executorSevice.execute(new ClientManager(clientSocket, this));
            }
        } catch (IOException | NullPointerException e) {
            serverCallback.onConnectionError("ERROR");
            e.printStackTrace();
        }
    }

    @Override
    public void decrementConnectedClients() {
        data.decrementConnectedClients();
    }

    @Override
    public void incrementComputedRequests() {
        data.incrementComputedRequests();
    }

    @Override
    public void incrementAddOperations() {
        data.incrementAddOperations();
    }

    @Override
    public void incrementSubOperations() {
        data.incrementSubOperations();
    }

    @Override
    public void incrementMulOperations() {
        data.incrementMulOperations();
    }

    @Override
    public void incrementDivOperations() {
        data.incrementDivOperations();
    }

    @Override
    public void incrementIncorrectOperations() {
        data.incrementIncorrectOperations();
    }

    @Override
    public void sumResults(int result) {
        data.addToSumOfResults(result);
    }

}
