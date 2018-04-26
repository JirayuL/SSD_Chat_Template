import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGui extends JFrame implements ActionListener {

    private JTextArea chatWindow;
    private JTextArea input;
    private JButton sendButton;

    private ChatClient chatClient;

    public ClientGui(ChatClient chatClient) {
        super("Chat Client");
        this.chatClient = chatClient;
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initLayout();
        pack();
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        chatWindow = new JTextArea();
        chatWindow.setPreferredSize(new Dimension(200, 200));
        chatWindow.setEditable(false);
        input = new JTextArea();
        input.setPreferredSize(new Dimension(100, 30));
        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(100, 30));
        sendButton.addActionListener(this);
        add(chatWindow, BorderLayout.CENTER);
        add(new JPanel() {
            {
                setLayout(new FlowLayout(FlowLayout.CENTER));
                add(input);
                add(sendButton);
            }
        }, BorderLayout.SOUTH);
    }

    public void appendMessage(String message) {
        chatWindow.append(message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chatClient.sendMessage(input.getText());
        input.setText("");
    }
}
