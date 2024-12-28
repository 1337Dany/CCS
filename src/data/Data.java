package data;

public class Data {
    //the number of newly connected clients,
    //• the number of computed requests,
    //• the numbers of particular requested operations,
    //• the number of incorrect operations,
    //• the sum of computed values.

    private static int connectedClients = 0;
    private static int computedRequests = 0;
    private static int addOperations = 0;
    private static int subOperations = 0;
    private static int mulOperations = 0;
    private static int divOperations = 0;
    private static int incorrectOperations = 0;
    private static int sumOfResults = 0;

    // Геттеры и сеттеры
    public int getConnectedClients() {
        return connectedClients;
    }

    public void incrementConnectedClients() {
        connectedClients++;
    }

    public int getComputedRequests() {
        return computedRequests;
    }

    public void incrementComputedRequests() {
        computedRequests++;
    }

    public int getAddOperations() {
        return addOperations;
    }

    public void incrementAddOperations() {
        addOperations++;
    }

    public int getSubOperations() {
        return subOperations;
    }

    public void incrementSubOperations() {
        subOperations++;
    }

    public int getMulOperations() {
        return mulOperations;
    }

    public void incrementMulOperations() {
        mulOperations++;
    }

    public int getDivOperations() {
        return divOperations;
    }

    public void incrementDivOperations() {
        divOperations++;
    }

    public int getIncorrectOperations() {
        return incorrectOperations;
    }

    public void incrementIncorrectOperations() {
        incorrectOperations++;
    }

    public int getSumOfResults() {
        return sumOfResults;
    }

    public void addToSumOfResults(int value) {
        sumOfResults += value;
    }

    public String getData(){
        return "The number of newly connected clients: " + connectedClients + "\n" +
                "The number of computed requests: " + computedRequests + "\n" +
                "The numbers of particular requested operations: " + "\n" +
                "Add operations: " + addOperations + "\n" +
                "Sub operations: " + subOperations + "\n" +
                "Mul operations: " + mulOperations + "\n" +
                "Div operations: " + divOperations + "\n" +
                "The number of incorrect operations: " + incorrectOperations + "\n" +
                "The sum of computed values: " + sumOfResults;
    }
}
