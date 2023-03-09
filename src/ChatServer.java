import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);

    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();

    }

    public void sendAll(Client senderClient, String message){
        for (Client client: clients) {
            if (client == senderClient){

            }else {
                client.receive(senderClient.socket.getPort(), message);
            }
        }
    }

    public void run(){

        while (true){
            System.out.println("Waiting...");

            try {
                Socket socket = serverSocket.accept();
                System.out.println("ClientConnected");

                clients.add(new Client(socket, this));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void removeClosedClient(Client client){
        clients.remove(client);
    }
}