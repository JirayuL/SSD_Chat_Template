import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import network.Message;

public class ChatServer {
	
	private Server server;
	private List<Connection> connections;
	private List<Message> messages;
	
	public ChatServer() throws IOException {
		server = new Server();
		connections = new ArrayList<Connection>();
		messages = new ArrayList<Message>();
		server.addListener(new ServerListener());
		
		server.getKryo().register(Message.class);
		server.getKryo().register(ChatServer.class);
		
		server.start();
		server.bind(54333);
		System.out.println("Server started");
	}
	
	class ServerListener extends Listener{
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			connections.add(connection);
			System.out.println("New client connected!");
			for (Message msg : messages) {
				connection.sendTCP(msg);
			}
		}
		
		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			connections.remove(connection);
			System.out.println("Client disconnected!");
		}
		
		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if (o instanceof Message) {
				Message msg = (Message) o;
				for (Connection c : connections) {
					c.sendTCP(msg);
				}
				messages.add(msg);
			}
		}
	}

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer();
    }

}
