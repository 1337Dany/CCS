package data.datasource;

import data.Data;
import domain.ServerCallback;

import java.io.IOException;
import java.net.*;

/**
 * The Server class handles incoming client connections and processes their requests.
 * It uses a DatagramSocket to listen for client discovery messages and spawns ClientManager
 * threads to handle individual client connections.
 */
public class UDPServer {
    private final ServerCallback callback;
    private final int port;
    private static final byte[] BYTE_BUFFER = new byte[1024];

    /**
     * Constructs a Server with the specified port and callback.
     *
     * @param port           the port number on which the server will listen for connections
     * @param serverCallback the callback to handle server events
     */
    public UDPServer(int port, ServerCallback serverCallback) {
        this.callback = serverCallback;
        this.port = port;
        start();
    }

    /**
     * Starts the server to listen for client connections.
     * It listens for "CCS DISCOVER" messages and responds with "CCS FOUND".
     * Spawns a ClientManager thread for each client connection.
     */
    public void start() {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            while (true) {
                DatagramPacket packet = new DatagramPacket(BYTE_BUFFER, BYTE_BUFFER.length);
                datagramSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.startsWith("CCS DISCOVER")) {
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
                } else {
                    callback.onConnectionError("Incorrect input " + received);
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

}