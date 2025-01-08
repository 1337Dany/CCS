package data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The Data class is responsible for maintaining and updating various statistics related to client connections and operations.
 * It provides methods to increment and decrement these statistics, as well as a method to retrieve a formatted string of the current data.
 */
public class Data {
    // Overall statistics
    private static final AtomicLong connectedClients = new AtomicLong(0);
    private static final AtomicLong computedRequests = new AtomicLong(0);
    private static final AtomicLong addOperations = new AtomicLong(0);
    private static final AtomicLong subOperations = new AtomicLong(0);
    private static final AtomicLong mulOperations = new AtomicLong(0);
    private static final AtomicLong divOperations = new AtomicLong(0);
    private static final AtomicLong incorrectOperations = new AtomicLong(0);
    private static final AtomicLong sumOfResults = new AtomicLong(0);

    // Statistics for last 10 seconds
    private static final AtomicLong connectedClientsTmp = new AtomicLong(0);
    private static final AtomicLong computedRequestsTmp = new AtomicLong(0);
    private static final AtomicLong addOperationsTmp = new AtomicLong(0);
    private static final AtomicLong subOperationsTmp = new AtomicLong(0);
    private static final AtomicLong mulOperationsTmp = new AtomicLong(0);
    private static final AtomicLong divOperationsTmp = new AtomicLong(0);
    private static final AtomicLong incorrectOperationsTmp = new AtomicLong(0);
    private static final AtomicLong sumOfResultsTmp = new AtomicLong(0);

    /**
     * Increments the count of connected clients by one.
     */
    public void incrementConnectedClients() {
        connectedClients.incrementAndGet();
        connectedClientsTmp.incrementAndGet();
    }

    /**
     * Decrements the count of connected clients by one.
     */
    public void decrementConnectedClients() {
        connectedClients.decrementAndGet();
        connectedClientsTmp.decrementAndGet();
    }

    /**
     * Increments the count of computed requests by one.
     */
    public void incrementComputedRequests() {
        computedRequests.incrementAndGet();
        computedRequestsTmp.incrementAndGet();
    }

    /**
     * Increments the count of addition operations by one.
     */
    public void incrementAddOperations() {
        addOperations.incrementAndGet();
        addOperationsTmp.incrementAndGet();
    }

    /**
     * Increments the count of subtraction operations by one.
     */
    public void incrementSubOperations() {
        subOperations.incrementAndGet();
        subOperationsTmp.incrementAndGet();
    }

    /**
     * Increments the count of multiplication operations by one.
     */
    public void incrementMulOperations() {
        mulOperations.incrementAndGet();
        mulOperationsTmp.incrementAndGet();
    }

    /**
     * Increments the count of division operations by one.
     */
    public void incrementDivOperations() {
        divOperations.incrementAndGet();
        divOperationsTmp.incrementAndGet();
    }

    /**
     * Increments the count of incorrect operations by one.
     */
    public void incrementIncorrectOperations() {
        incorrectOperations.incrementAndGet();
        incorrectOperationsTmp.incrementAndGet();
    }

    /**
     * Adds the specified value to the sum of results.
     *
     * @param value the value to be added to the sum of results
     */
    public void addToSumOfResults(int value) {
        sumOfResults.addAndGet(value);
        sumOfResultsTmp.addAndGet(value);
    }

    public void updateTmpData() {
        connectedClientsTmp.set(0);
        computedRequestsTmp.set(0);
        addOperationsTmp.set(0);
        subOperationsTmp.set(0);
        mulOperationsTmp.set(0);
        divOperationsTmp.set(0);
        incorrectOperationsTmp.set(0);
        sumOfResultsTmp.set(0);
    }

    /**
     * Returns a formatted string containing the current statistics.
     *
     * @return a string representation of the current statistics
     */
    public String getData() {
        return "------ Overall Statistics ------\n" +
                "The number of newly connected clients:             " + connectedClients.get() + "\n" +
                "The number of computed requests:                   " + computedRequests.get() + "\n" +
                "The numbers of particular requested operations:" + "\n" +
                "Add operations:                                    " + addOperations.get() + "\n" +
                "Sub operations:                                    " + subOperations.get() + "\n" +
                "Mul operations:                                    " + mulOperations.get() + "\n" +
                "Div operations:                                    " + divOperations.get() + "\n" +
                "The number of incorrect operations:                " + incorrectOperations.get() + "\n" +
                "The sum of computed values:                        " + sumOfResults.get() + "\n" +
                "------ Statistics for the last 10 seconds ------\n" +
                "The number of newly connected clients:             " + connectedClientsTmp.get() + "\n" +
                "The number of computed requests:                   " + computedRequestsTmp.get() + "\n" +
                "The numbers of particular requested operations:" + "\n" +
                "Add operations:                                    " + addOperationsTmp.get() + "\n" +
                "Sub operations:                                    " + subOperationsTmp.get() + "\n" +
                "Mul operations:                                    " + mulOperationsTmp.get() + "\n" +
                "Div operations:                                    " + divOperationsTmp.get() + "\n" +
                "The number of incorrect operations:                " + incorrectOperationsTmp.get() + "\n" +
                "The sum of computed values:                        " + sumOfResultsTmp.get();
    }
}