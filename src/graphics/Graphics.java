package src.graphics;

import javax.swing.*;

import src.data.InputParser;

import java.awt.*;
import java.awt.event.*;

public class Graphics extends JFrame {

    private static JTextArea textArea;
    private final JTextField INPUT_FIELDS;
    private final static int LINES_TO_KEEP = 30;

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
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);

        // info button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.add(Box.createVerticalGlue());
        JButton infoButton = new JButton("INFO");
        infoButton.setPreferredSize(new Dimension(60, 30));
        infoButton.setMinimumSize(new Dimension(60, 30));
        infoButton.setMaximumSize(new Dimension(60, 30));
        buttonPanel.add(infoButton);
        buttonPanel.add(Box.createVerticalGlue());
        add(buttonPanel, BorderLayout.EAST);
        infoButton.addActionListener(e -> info());
        

        textArea.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        INPUT_FIELDS = new JTextField();
        INPUT_FIELDS.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String input = INPUT_FIELDS.getText();
                    textArea.append(input + "\n");
                    INPUT_FIELDS.setText("");

                    // send to parser
                    InputParser.parse(input);

                    int lines = textArea.getLineCount();
                    if (lines > LINES_TO_KEEP) {
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
        mainPanel.add(INPUT_FIELDS, BorderLayout.SOUTH);

        add(mainPanel);

    }

    public static void info() {
        JFrame infoBox = new JFrame("INFO");
        infoBox.setSize(960, 550);
        infoBox.setLocation(0, 300);
        infoBox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoBox.add(infoPanel);

        JTextArea textAreaInfo = new JTextArea();
        textAreaInfo.setEditable(false);
        textAreaInfo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JScrollPane scrollPane = new JScrollPane(textAreaInfo);
        infoPanel.add(scrollPane, BorderLayout.CENTER);
        textAreaInfo.setBackground(Color.DARK_GRAY);
        textAreaInfo.setForeground(Color.WHITE);
        textAreaInfo.append("Hello. I want to go to sleep. My name is john, clemons. I live on 4956 Racquetball new mexico...." + "\n");

        infoBox.setVisible(true);

    }

    public static void log(String act, Object... args) {
        int lines = textArea.getLineCount();
        if (lines > LINES_TO_KEEP) {
            try {
                int start = textArea.getLineStartOffset(0);
                int end = textArea.getLineEndOffset(0);
                textArea.replaceRange("", start, end);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String s = String.format(act, args);
        textArea.append(s + "\n");
    }

    public static void main(String[] args) {
        Graphics window = new Graphics();
        window.setVisible(true);
        //info();
    }
    
}
