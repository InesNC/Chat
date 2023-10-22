import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String[] args) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 8084);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
