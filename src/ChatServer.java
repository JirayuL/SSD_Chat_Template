import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import network.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private Server server;
    private List<Connection> connections;

    public ChatServer(int port) throws IOException {
        connections = new ArrayList<Connection>();
        server = new Server();
        Kryo kryo = server.getKryo();
        kryo.register(Message.class);
        server.addListener(new ServerListener());
        server.start();
        server.bind(port);
        System.out.println("Server Started.");
    }

    class ServerListener extends Listener {
        @Override
        public void connected(Connection connection) {
            super.connected(connection);
            connections.add(connection);
            System.out.println("New client connected!");
        }

        @Override
        public void received(Connection connection, Object o) {
            super.received(connection, o);
            if (o instanceof Message) {
                Message message = (Message) o;
                System.out.println("Receive: " + message.text);
                for(Connection c : connections) {
                    c.sendTCP(message);
                }
            }
        }

        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
            System.out.println("A client disconnected");
        }
    }

    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer(54333);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
