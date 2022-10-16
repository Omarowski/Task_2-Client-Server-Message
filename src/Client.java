import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class Client {
    Random random = new Random();
    SocketChannel channel;
    String server = "127.0.0.1";
    int port = 8511;
    boolean continueConversation = true;

    Client() throws IOException, InterruptedException {
        try {
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(server, port));
            System.out.print("Connecting ...");
            while (!channel.finishConnect()) {
                System.out.println("Waiting for connection");
            }
        } catch (UnknownHostException exc) {
            System.err.println("Unknown host " + server);

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        System.out.println("\nConnected");
        while (continueConversation) {
            writeMessage();
            Thread.sleep(2000);
        }
    }

    public void writeMessage() throws IOException {

        int number = random.nextInt(100);
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        String message = "Add "+ number + " " + number1 + " " + number2 ;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes());
        buffer.flip();
        System.out.println("1. Sending message: " + message);
        channel.write(buffer);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();
        String response = new String(buffer.array()).trim();
        System.out.println("2. Response: " + response);
        buffer.clear();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client();
    }
}
