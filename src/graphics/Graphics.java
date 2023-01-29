package src.graphics;

import javax.swing.*;

import src.data.InputParser;

import java.awt.*;
import java.awt.event.*;
import src.data.InputParser;

public class Graphics extends JFrame {

    private static JTextArea textArea;
    private JTextField inputField;
    private int linesToKeep = 30;

    public Graphics() {
        super("Terminal Window");
        setSize(960, 550);
        setLocation(0, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        textArea.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String input = inputField.getText();
                    textArea.append(input + "\n");
                    inputField.setText("");

                    // send to parser
                    InputParser.parse(input);

                    int lines = textArea.getLineCount();
                    if (lines > linesToKeep) {
                        try {
                            int start = textArea.getLineStartOffset(0);
                            int end = textArea.getLineEndOffset(0);
                            textArea.replaceRange("", start, end);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        });
        mainPanel.add(inputField, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void log(String act, Object... args) {
        String s = String.format(act, args);
        textArea.append(s + "\n");
    }

    public static void main(String[] args) {
        Graphics window = new Graphics();
        window.setVisible(true);
    }
}

