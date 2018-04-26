public class ChatClient {

    private ClientGui gui;

    public ChatClient() {
        gui = new ClientGui(this);
    }

    public void sendMessage(String text) {

    }

    public static void main(String[] args) {
        new ChatClient();
    }

}
