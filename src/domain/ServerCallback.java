package domain;

/**
 * The ServerCallback interface defines the methods that must be implemented
 * to handle server events and callbacks.
 */
public interface ServerCallback {
    /**
     * Called when a connection message is received.
     *
     * @param message the connection message
     */
    void onConnectionMessage(String message);

    /**
     * Called when a connection error occurs.
     *
     * @param message the error message
     */
    void onConnectionError(String message);
}