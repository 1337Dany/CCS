import domain.CCSPresenter;
import ui.View;

public class CCS {
    public CCS(int port) {
        View view = new View();
        try {
            new CCSPresenter(port, view);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            view.printErrorMessage("Incorrect input");
        }
    }

    public static void main(String[] args) {
        new CCS(Integer.parseInt(args[0]));
    }
}
