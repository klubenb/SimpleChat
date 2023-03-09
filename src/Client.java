import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    Socket socket;
    ChatServer server;

    Scanner in;
    PrintStream out;

    public Client(Socket socket,ChatServer server) {
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }



    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Hello to chat " + socket.getPort());
            String input = in.nextLine();
            while (!input.equals("bye")){
                server.sendAll(this, input);
                input = in.nextLine();
            }
            socket.close();
            removeClientFromArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println();
    }

    public void receive(int port, String message){
        out.println(port + ": " + message);
    }

    public void removeClientFromArray(){
        server.removeClosedClient(this);
    }
}
