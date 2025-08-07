package Grocery;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class StaffHomePage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        StaffHomePage staffHomePage = new StaffHomePage();
        staffHomePage.setVisible(true);
    }

    public StaffHomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcomeStaff = new JLabel("Welcome Staff!");
        lblWelcomeStaff.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWelcomeStaff.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcomeStaff.setBounds(10, 0, 250, 50);
        contentPane.add(lblWelcomeStaff);
        
        JButton btnLogOut = new JButton("Log out");
        btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnLogOut.setBounds(296, 232, 130, 21);
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle logout process here
                // For example, closing current window and opening login window
                dispose(); // Close current window
                Login loginPage = new Login();
                loginPage.setVisible(true); // Open login window
            }
        });
        contentPane.add(btnLogOut);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 60, 381, 161);
        contentPane.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        
        JButton btnCheckInventory = new JButton("Check inventory");
        btnCheckInventory.setFont(new Font("Tahoma", Font.ITALIC, 18));
        btnCheckInventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open inventory page
                dispose(); // Close current window
                Inventory inventoryPage = new Inventory();
                inventoryPage.setVisible(true);
            }
        });
        buttonPanel.add(btnCheckInventory);
        
        JButton btnPrintReceipt = new JButton("Print receipt");
        btnPrintReceipt.setFont(new Font("Tahoma", Font.ITALIC, 18));
        btnPrintReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open print receipt page
                dispose(); // Close current window
                PrintReceipt printReceiptPage = new PrintReceipt();
                printReceiptPage.setVisible(true);
            }
        });
        buttonPanel.add(btnPrintReceipt);
        
        setTitle("Staff Home Page");
    }
}
