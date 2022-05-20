package Server;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Serializable {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    public static Configurations configurations;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
        try {
            this.configurations = Configurations.getInstance();
        } catch (IOException e) {
        }
        this.threadPool = Executors.newFixedThreadPool( Integer.parseInt(configurations.getProperties("threadPoolSize")) );
    }

    public void start() {
        threadPool.execute(()->runServer());
    }

    private void runServer(){
        try {
            ServerSocket serverSocket;
            synchronized (this) {
                serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(listeningIntervalMS);
            }
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.submit(() -> { handleClient(clientSocket); });
                } catch (SocketTimeoutException e) {
                }
            }
            serverSocket.close();
            threadPool.shutdownNow();
        } catch (IOException e) {

        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.ServerStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e){
        }
    }
    public void stop(){
        stop = true;
    }
}
