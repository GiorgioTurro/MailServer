package mailserver;


import javax.swing.*;
import java.awt.*;

public class MailServerView extends JFrame {
    private JTextArea logTA;

    public MailServerView(){
        logTA = new JTextArea();

        add(new JLabel("Log:"), BorderLayout.NORTH);
        add(logTA, BorderLayout.CENTER);

        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void append(String s){
        logTA.append(s);
    }

}
