import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


public class ServerWorker implements Runnable {

    private Socket clientSocket;
    private Server server;
    private String userName;


    //CONSTRUCTOR

    public ServerWorker(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    //GETTERS

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getUserName() {
        return userName;
    }

    // METHODS
    public void receive() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (!clientSocket.isClosed()) {

            String messageReceived = in.readLine();
            System.out.println(messageReceived);
            if (messageReceived != null) {
                server.sendToAllClients(this.userName, messageReceived, this);
            }
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public void run() {
        try {
            receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

