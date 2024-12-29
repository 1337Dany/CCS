package data;

public enum CalculationPrefixes {
    ADDITION("ADD"),
    SUBTRACTION("SUB"),
    MULTIPLICATION("MUL"),
    DIVISION("DIV");

    private final String prefix;

    CalculationPrefixes(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
