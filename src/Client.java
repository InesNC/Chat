import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private Socket clientSocket;

    //CONSTRUCTOR
    public Client(InetAddress ip, int portNumber) throws IOException {
            clientSocket = new Socket(ip, portNumber);
            Thread receiveThread = new Thread(this::receive);
            receiveThread.start();
            send();
    }

    //METHODS
    public void receive(){

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (!clientSocket.isClosed()) {

                String messageReceived = in.readLine();
                System.out.println("Message received: " + messageReceived);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(){

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            while (!clientSocket.isClosed()) {

                String messageToBeSent = in.readLine();

                if(messageToBeSent != null){

                    out.println(messageToBeSent);
                }
            }
        } catch (IOException e){
            System.out.println("error sending");
        }
    }


    public static void main(String[] args) {

        try {

            Client clients = new Client(InetAddress.getLocalHost(), 8084);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
