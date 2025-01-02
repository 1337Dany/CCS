package domain;

/**
 * The ServerCallback interface defines the methods that must be implemented
 * to handle server events and callbacks.
 */
public interface ServerCallback {
    void onConnectionMessage(String message);

    void onConnectionError(String message);

    void statisticsReport(String message);
}
