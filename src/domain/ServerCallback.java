package domain;

public interface ServerCallback {
    void onConnectionMessage(String message);
    void onConnectionError(String message);
    void statisticsReport(String message);
}
