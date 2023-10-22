import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int portNumber = 8084;
    private ServerSocket serverSocket;
    public static ArrayList<ServerWorker> serverWorkersArray = new ArrayList<>();
    private ExecutorService threadPool = Executors.newCachedThreadPool();


    //CONSTRUCTOR

    public Server() throws IOException {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.getMessage();
        }
        while (serverSocket != null) {
            savingConnections(receivingClients());
        }

    }

    // METHODS

    public Socket receivingClients() throws IOException {

            System.out.println("waiting connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println("connection accepted");
            return clientSocket;

        }

    public void savingConnections(Socket clientSocket) throws IOException {
        if(clientSocket.isConnected()){
            ServerWorker serverWorker =  new ServerWorker(clientSocket,this);
            serverWorkersArray.add(serverWorker);
            creatingWorkingThreads(serverWorker);
        }
    }

    public void creatingWorkingThreads(ServerWorker serverWorker) {

            threadPool.submit(serverWorker);

    }

    public void sendToAllClients(String messageReceived) throws IOException {


        for (int i = 0; i < serverWorkersArray.size(); i++) {

                PrintWriter out = new PrintWriter(serverWorkersArray.get(i).getClientSocket().getOutputStream(), true);
                out.println(messageReceived);

        }
    }

    public static void main(String[] args) {
        try{
            Server server = new Server();

        } catch (Exception e){
            System.out.println("error z");
        }
    }
}