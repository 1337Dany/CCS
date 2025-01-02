package data;

/**
 * The CalculationPrefixes enum represents the prefixes for different arithmetic operations.
 * Each enum constant corresponds to a specific arithmetic operation and holds a string prefix
 * that is used to identify the operation in client messages.
 */
public enum CalculationPrefixes {
    /**
     * Represents the addition operation with the prefix "ADD".
     */
    ADDITION("ADD"),

    /**
     * Represents the subtraction operation with the prefix "SUB".
     */
    SUBTRACTION("SUB"),

    /**
     * Represents the multiplication operation with the prefix "MUL".
     */
    MULTIPLICATION("MUL"),

    /**
     * Represents the division operation with the prefix "DIV".
     */
    DIVISION("DIV");

    private final String prefix;

    /**
     * Constructs a CalculationPrefixes enum constant with the specified prefix.
     *
     * @param prefix the string prefix for the arithmetic operation
     */
    CalculationPrefixes(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the string prefix associated with the arithmetic operation.
     *
     * @return the string prefix
     */
    public String getPrefix() {
        return prefix;
    }
}