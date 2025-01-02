import domain.CCSPresenter;
import ui.View;

/**
 * The CCS class is the entry point of the application.
 * It initializes the CCSPresenter with the specified port and a View instance.
 */
public class CCS {
    /**
     * Constructor for CCS.
     *
     * @param port The port number to be used by the server.
     */
    public CCS(int port) {
        View view = new View();
        try {
            new CCSPresenter(port, view);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            view.printErrorMessage("Incorrect input");
        }
    }

    /**
     * The main method to start the application.
     *
     * @param args Command line arguments, where args[0] is the port number.
     */
    public static void main(String[] args) {
        new CCS(Integer.parseInt(args[0]));
    }
}