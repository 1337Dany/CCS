package data;

public interface ClientManagerCallback {
    void onReceivedMessage(String message);
    void decrementConnectedClients();
    void incrementComputedRequests();
    void incrementAddOperations();
    void incrementSubOperations();
    void incrementMulOperations();
    void incrementDivOperations();
    void incrementIncorrectOperations();
    void sumResults(int result);
}
