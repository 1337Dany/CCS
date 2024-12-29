package data;

public class Data {
    private static int connectedClients = 0;
    private static int computedRequests = 0;
    private static int addOperations = 0;
    private static int subOperations = 0;
    private static int mulOperations = 0;
    private static int divOperations = 0;
    private static int incorrectOperations = 0;
    private static int sumOfResults = 0;

    public void incrementConnectedClients() {
        connectedClients++;
    }

    public void decrementConnectedClients() {
        connectedClients--;
    }


    public void incrementComputedRequests() {
        computedRequests++;
    }

    public void incrementAddOperations() {
        addOperations++;
    }


    public void incrementSubOperations() {
        subOperations++;
    }


    public void incrementMulOperations() {
        mulOperations++;
    }


    public void incrementDivOperations() {
        divOperations++;
    }


    public void incrementIncorrectOperations() {
        incorrectOperations++;
    }


    public void addToSumOfResults(int value) {
        sumOfResults += value;
    }

    public String getData() {
        return "The number of newly connected clients:             " + connectedClients + "\n" +
                "The number of computed requests:                   " + computedRequests + "\n" +
                "The numbers of particular requested operations: " + "\n" +
                "Add operations:                                    " + addOperations + "\n" +
                "Sub operations:                                    " + subOperations + "\n" +
                "Mul operations:                                    " + mulOperations + "\n" +
                "Div operations:                                    " + divOperations + "\n" +
                "The number of incorrect operations:                " + incorrectOperations + "\n" + "The sum of computed values:                        " + sumOfResults;
    }
}
