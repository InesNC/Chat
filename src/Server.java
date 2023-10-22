import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int portNumber = 8089;
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
        if (clientSocket.isConnected()) {
            ServerWorker serverWorker = new ServerWorker(clientSocket, this);
            serverWorkersArray.add(serverWorker);
            serverWorker.setUserName(sendPromptForLogin(clientSocket));
            creatingWorkingThreads(serverWorker);
        }
    }

    public String sendPromptForLogin(Socket clientSocket) throws IOException {

        if (clientSocket != null) {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Enter your name: ");

            String name = in.readLine();

            out.println("Hello, " + name + "!");
            out.println("Welcome to WhatsGoingOn.");

            return name;
        }
        return null;
    }

    public void creatingWorkingThreads(ServerWorker serverWorker) {

        threadPool.submit(serverWorker);

    }

    public void sendToAllClients(String username, String messageReceived, ServerWorker serverWorker) throws IOException {

        for (int i = 0; i < serverWorkersArray.size(); i++) {

            if (serverWorker.getUserName() == serverWorkersArray.get(i).getUserName()) {
                continue;
            }

            PrintWriter out = new PrintWriter(serverWorkersArray.get(i).getClientSocket().getOutputStream(), true);
            out.println(username + ": " + messageReceived);
        }
    }


    public static void main(String[] args) {
        try {
            Server server = new Server();

        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
