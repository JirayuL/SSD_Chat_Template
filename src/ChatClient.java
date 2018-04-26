import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import network.Message;

public class ChatClient {

	private ClientGui gui;

	private Client client;

	public ChatClient() throws IOException {
		gui = new ClientGui(this);
		client = new Client();
		client.getKryo().register(Message.class);
		client.addListener(new ClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
		// client.connect(5000, "35.197.154.253", 54333);
	}

	class ClientListener extends Listener {
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			gui.appendMessage("Connect to Server!");
		}

		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if (o instanceof Message) {
				Message msg = (Message) o;
				gui.appendMessage(msg.text);
			}
		}
	}

	public void sendMessage(String text) {
		Message msg = new Message();
		msg.text = text;
		client.sendTCP(msg);
	}

	public static void main(String[] args) {
		try {
			new ChatClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
