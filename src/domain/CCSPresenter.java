package domain;

import data.Data;
import data.datasource.TCPServer;
import data.datasource.UDPServer;
import ui.ConsoleView;
import ui.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The CCSPresenter class acts as a presenter in the Model-View-Presenter (MVP) pattern.
 * It handles the communication between the Server and the View.
 * It implements the ServerCallback interface to receive server events and update the view accordingly.
 */
public class CCSPresenter implements ServerCallback {
    private static final ExecutorService serverExecutor = Executors.newCachedThreadPool();
    private static ConsoleView consoleView;
    private final Data statistics = new Data();

    /**
     * Constructs a CCSPresenter with the specified port and view.
     *
     * @param port the port number on which the server will listen for connections
     * @param view the view that will display messages and statistics
     */
    public CCSPresenter(int port, ConsoleView view) {
        consoleView = view;
        configure(port);
    }

    /**
     * Configures the server with the specified port and sets this presenter as the callback.
     *
     * @param port the port number on which the server will listen for connections
     */
    private void configure(int port) {
        serverExecutor.execute(() -> new UDPServer(port, this));
        serverExecutor.execute(() -> new TCPServer(port, this, statistics));
        serverExecutor.execute(this::startShowDataThread);
    }

    /**
     * Starts a thread to report server statistics every 10 seconds.
     */
    private void startShowDataThread() {
        while (true) {
            try {
                Thread.sleep(10000);
                statisticsReport(statistics.getData());
                statistics.updateTmpData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Called when a connection message is received from the server.
     * Displays the message in the view.
     *
     * @param message the connection message received from the server
     */
    @Override
    public void onConnectionMessage(String message) {
        consoleView.printErrorMessage(message);
    }

    /**
     * Called when a connection error occurs in the server.
     * Displays the error message in the view.
     *
     * @param message the error message received from the server
     */
    @Override
    public void onConnectionError(String message) {
        consoleView.printErrorMessage(message);
    }

    /**
     * Called periodically to report server statistics.
     * Displays the statistics in the view.
     *
     * @param message the statistics report received from the server
     */
    public void statisticsReport(String message) {
        consoleView.statisticsReprot(message);
    }
}