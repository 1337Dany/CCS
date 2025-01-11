package data.datasource;

import domain.ServerCallback;

import java.io.IOException;
import java.net.*;

/**
 * The UDPServer class handles incoming client connections and processes their requests using UDP.
 * It listens for client discovery messages and responds accordingly.
 */
public class UDPServer {
    private final ServerCallback callback;
    private final int port;
    private static final byte[] BYTE_BUFFER = new byte[1024];

    /**
     * Constructs a UDPServer with the specified port and callback.
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
     * If an incorrect message is received, it triggers the callback with an error message.
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