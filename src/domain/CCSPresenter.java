package domain;

import data.Server;
import ui.View;

public class CCSPresenter implements ServerCallback {
    private static View consoleView;

    public CCSPresenter(int port, View view) {
        consoleView = view;
        configure(port);
    }

    private void configure(int port) {
        Server server = new Server(port, this);
    }


    @Override
    public void onConnectionMessage(String message) {
        consoleView.printErrorMessage(message);
    }

    @Override
    public void onConnectionError(String message) {
        consoleView.printErrorMessage(message);
    }

    @Override
    public void statisticsReport(String message) {
        consoleView.statisticsReprot(message);
    }
}
