package src.routing;

import javax.swing.*;
import java.awt.*;

public class Graphics extends JPanel {
    
    private JTextField inputField;
    private JButton submitButton;

    public Graphics() {
        setLayout(new BorderLayout());
        inputField = new JTextField();
        submitButton = new JButton("Submit");

        add(inputField, BorderLayout.CENTER);
        add(submitButton, BorderLayout.EAST);

        submitButton.addActionListener(e -> {
            String input = inputField.getText();
            // do something with the input
            System.out.println("User input: " + input);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Input Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Graphics());
        frame.pack();
        frame.setSize(300, 100);
        frame.setVisible(true);
    
    }

}



