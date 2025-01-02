package data;

import domain.ServerCallback;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server class handles incoming client connections and processes their requests.
 * It uses a DatagramSocket to listen for client discovery messages and spawns ClientManager
 * threads to handle individual client connections.
 */
public class Server implements ClientManagerCallback {
    private final Data data = new Data();
    private final ServerCallback callback;
    private final int port;
    private static final byte[] BYTE_BUFFER = new byte[1024];
    private DatagramSocket datagramSocket;
    private static final ExecutorService executorSevice = Executors.newCachedThreadPool();

    /**
     * Constructs a Server with the specified port and callback.
     *
     * @param port     the port number on which the server will listen for connections
     * @param callback the callback to handle server events
     */
    public Server(int port, ServerCallback callback) {
        this.callback = callback;
        this.port = port;
        startShowDataThread();
        start();
    }

    /**
     * Starts the server to listen for client connections.
     * It listens for "CCS DISCOVER" messages and responds with "CCS FOUND".
     * Spawns a ClientManager thread for each client connection.
     */
    public void start() {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (IllegalArgumentException | SocketException e) {
            callback.onConnectionError("Incorrect input");
            return;
        }
        try (ServerSocket socket = new ServerSocket(port)) {
            while (true) {
                DatagramPacket packet = new DatagramPacket(BYTE_BUFFER, BYTE_BUFFER.length);
                datagramSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.startsWith("CCS DISCOVER")) {
                    data.incrementConnectedClients();
                    String acceptMessage = "CCS FOUND";
                    InetAddress address = packet.getAddress();
                    int senderPort = packet.getPort();
                    DatagramPacket acceptPacket = new DatagramPacket(
                            acceptMessage.getBytes(),
                            acceptMessage.length(),
                            address,
                            senderPort
                    );
                    datagramSocket.send(acceptPacket);
                    Socket clientSocket = socket.accept();
                    executorSevice.execute(new ClientManager(clientSocket, this));
                } else {
                    callback.onConnectionError("Incorrect input " + received);
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a thread to report server statistics every 10 seconds.
     */
    private void startShowDataThread() {
        executorSevice.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                    callback.statisticsReport(data.getData());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onReceivedMessage(String message) {
        callback.onConnectionMessage(message);
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