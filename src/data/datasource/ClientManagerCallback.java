package data.datasource;

/**
 * The ClientManagerCallback interface defines the methods that must be implemented
 * to handle various events and operations related to client management.
 * Implementing classes will provide the logic for handling received messages,
 * updating client connection counts, and tracking different types of operations.
 */
public interface ClientManagerCallback {
    /**
     * Decrements the count of connected clients by one.
     */
    void decrementConnectedClients();

    /**
     * Increments the count of computed requests by one.
     */
    void incrementComputedRequests();

    /**
     * Increments the count of addition operations by one.
     */
    void incrementAddOperations();

    /**
     * Increments the count of subtraction operations by one.
     */
    void incrementSubOperations();

    /**
     * Increments the count of multiplication operations by one.
     */
    void incrementMulOperations();

    /**
     * Increments the count of division operations by one.
     */
    void incrementDivOperations();

    /**
     * Increments the count of incorrect operations by one.
     */
    void incrementIncorrectOperations();

    /**
     * Adds the specified result to the sum of computed values.
     *
     * @param result the result to be added to the sum of computed values
     */
    void sumResults(int result);
}