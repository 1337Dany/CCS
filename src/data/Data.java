package data;

/**
 * The Data class is responsible for maintaining and updating various statistics related to client connections and operations.
 * It provides methods to increment and decrement these statistics, as well as a method to retrieve a formatted string of the current data.
 */
public class Data {
    private static int connectedClients = 0;
    private static int computedRequests = 0;
    private static int addOperations = 0;
    private static int subOperations = 0;
    private static int mulOperations = 0;
    private static int divOperations = 0;
    private static int incorrectOperations = 0;
    private static int sumOfResults = 0;

    /**
     * Increments the count of connected clients by one.
     */
    public void incrementConnectedClients() {
        connectedClients++;
    }

    /**
     * Decrements the count of connected clients by one.
     */
    public void decrementConnectedClients() {
        connectedClients--;
    }

    /**
     * Increments the count of computed requests by one.
     */
    public void incrementComputedRequests() {
        computedRequests++;
    }

    /**
     * Increments the count of addition operations by one.
     */
    public void incrementAddOperations() {
        addOperations++;
    }

    /**
     * Increments the count of subtraction operations by one.
     */
    public void incrementSubOperations() {
        subOperations++;
    }

    /**
     * Increments the count of multiplication operations by one.
     */
    public void incrementMulOperations() {
        mulOperations++;
    }

    /**
     * Increments the count of division operations by one.
     */
    public void incrementDivOperations() {
        divOperations++;
    }

    /**
     * Increments the count of incorrect operations by one.
     */
    public void incrementIncorrectOperations() {
        incorrectOperations++;
    }

    /**
     * Adds the specified value to the sum of results.
     *
     * @param value the value to be added to the sum of results
     */
    public void addToSumOfResults(int value) {
        sumOfResults += value;
    }

    /**
     * Returns a formatted string containing the current statistics.
     *
     * @return a string representation of the current statistics
     */
    public String getData() {
        return "The number of newly connected clients:             " + connectedClients + "\n" +
                "The number of computed requests:                   " + computedRequests + "\n" +
                "The numbers of particular requested operations:" + "\n" +
                "Add operations:                                    " + addOperations + "\n" +
                "Sub operations:                                    " + subOperations + "\n" +
                "Mul operations:                                    " + mulOperations + "\n" +
                "Div operations:                                    " + divOperations + "\n" +
                "The number of incorrect operations:                " + incorrectOperations + "\n" +
                "The sum of computed values:                        " + sumOfResults;
    }
}