package data;

import domain.ServerCallback;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements ClientManagerCallback {
    private static final Data data = new Data();
    private final ServerCallback callback;
    private final int port;
    private static final byte[] BYTE_BUFFER = new byte[1024];
    private DatagramSocket datagramSocket;
    private static final ExecutorService executorSevice = Executors.newCachedThreadPool();

    public Server(int port, ServerCallback callback) {
        this.callback = callback;
        this.port = port;
        startShowDataThread();
        start();
    }

    public void start() {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (IllegalArgumentException | SocketException e) {
            callback.onConnectionError("Incorrect input");
            return;
        }
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(BYTE_BUFFER, BYTE_BUFFER.length);
                datagramSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.startsWith("CCS DISCOVER")) {
                    data.incrementConnectedClients();
                    String acceptMessage = "CCS FOUND";
                    InetAddress address = packet.getAddress();
                    DatagramPacket acceptPacket = new DatagramPacket(
                            acceptMessage.getBytes(),
                            acceptMessage.length(),
                            address,
                            port
                    );
                    datagramSocket.send(acceptPacket);
                    executorSevice.execute(new ClientManager(packet.getAddress().getHostAddress(), port, this));
                } else {
                    callback.onConnectionError("Incorrect input " + received);
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

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
