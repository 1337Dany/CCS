package data;

import domain.ClientManager;
import domain.ServerCallback;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
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
                    executorSevice.execute(new ClientManager());
                    String acceptMessage = "CCS FOUND";
                    InetAddress localHost = InetAddress.getByName("localhost");
                    DatagramPacket acceptPacket = new DatagramPacket(acceptMessage.getBytes(), acceptMessage.length(), localHost, port);
                    datagramSocket.send(acceptPacket);
                } else {
                    callback.onConnectionError("Incorrect input " + received);
                }
            }
        } catch (IOException | NullPointerException e) {
            callback.onConnectionError("Incorrect input");
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
}
