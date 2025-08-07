package Grocery;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Splashscreen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private JLabel copyrightLabel;
    private JTextField txtWelcomeToGreen;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Splashscreen frame = new Splashscreen();
                    frame.setVisible(true);
                    frame.startProgressBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Splashscreen() {
    	setBackground(new Color(192, 192, 192));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        progressBar = new JProgressBar();
        progressBar.setForeground(new Color(128, 255, 255));
        progressBar.setBounds(60, 120, 300, 30);
        contentPane.add(progressBar);

        progressLabel = new JLabel("0%");
        progressLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        progressLabel.setBounds(210, 160, 50, 20);
        contentPane.add(progressLabel);

        copyrightLabel = new JLabel("Green Grocery Store 2024 Copyright by Group 4");
        copyrightLabel.setBackground(new Color(192, 192, 192));
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        copyrightLabel.setForeground(new Color(128, 128, 128));
        copyrightLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        copyrightLabel.setBounds(44, 219, 338, 20);
        contentPane.add(copyrightLabel);
        
        txtWelcomeToGreen = new JTextField();
        txtWelcomeToGreen.setBackground(new Color(128, 255, 255));
        txtWelcomeToGreen.setForeground(new Color(0, 0, 0));
        txtWelcomeToGreen.setFont(new Font("Tahoma", Font.ITALIC, 18));
        txtWelcomeToGreen.setHorizontalAlignment(SwingConstants.CENTER);
        txtWelcomeToGreen.setText("Welcome to Green Grocery Store");
        txtWelcomeToGreen.setBounds(60, 43, 300, 30);
        contentPane.add(txtWelcomeToGreen);
        txtWelcomeToGreen.setColumns(10);
    }

    public void startProgressBar() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 1; i <= 100; i++) {
                        Thread.sleep(25); 
                        progressBar.setValue(i);
                        progressLabel.setText(i + "%");
                        if (i == 100) {
                            // Open the login window when the progress reaches 100%
                            openLoginWindow();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void openLoginWindow() {
        // Close the splash screen
        dispose();
        
        // Open the login window
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login loginWindow = new Login();
                    loginWindow.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
