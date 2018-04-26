import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import network.Message;

import java.io.IOException;

public class ChatClient {

    private ClientGui gui;

    private Client client;

    public ChatClient() throws IOException {
        gui = new ClientGui(this);
        client = new Client();
        Kryo kryo = client.getKryo();
        kryo.register(Message.class);
        client.addListener(new ClientListener());
        client.start();
        client.connect(5000, "127.0.0.1", 54333);
    }

    public void sendMessage(String text) {
        Message m = new Message();
        m.text = text;
        client.sendTCP(m);
    }

    class ClientListener extends Listener {
        @Override
        public void connected(Connection connection) {
            super.connected(connection);
            gui.appendMessage("Connected to server");
        }

        @Override
        public void received(Connection connection, Object o) {
            super.received(connection, o);
            if (o instanceof Message) {
                Message message = (Message) o;
                gui.appendMessage(message.text);
            }
        }

        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
            System.out.println("Disconnected from server");
        }
    }

    public static void main(String[] args) {
        try {
            new ChatClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
