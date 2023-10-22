import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker implements Runnable{

    private Socket clientSocket;
    private Server server;


    //CONSTRUCTOR

    public ServerWorker(Socket clientSocket, Server server){
        this.clientSocket = clientSocket;
        this.server = server;
    }

    //GETTERS

    public Socket getClientSocket() {
        return clientSocket;
    }

    // METHODS
    public void receive() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (!clientSocket.isClosed()) {

                String messageReceived = in.readLine();
                System.out.println("Message received: " + messageReceived);
                if(messageReceived != null){
                    server.sendToAllClients(messageReceived);
                }
        }
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
