package Grocery;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import java.awt.Color;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtWelcomeToGreengrocery;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JTextField txtSelectRole;
    private JFormattedTextField formattedTextField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 568, 424);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(220, 255, 206));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtWelcomeToGreengrocery = new JTextField();
        txtWelcomeToGreengrocery.setForeground(new Color(7, 98, 0));
        txtWelcomeToGreengrocery.setEditable(false);
        txtWelcomeToGreengrocery.setBackground(new Color(0, 255, 128));
        txtWelcomeToGreengrocery.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtWelcomeToGreengrocery.setText("Welcome to GreenGrocery Store");
        txtWelcomeToGreengrocery.setHorizontalAlignment(SwingConstants.CENTER);
        txtWelcomeToGreengrocery.setBounds(135, 31, 270, 47);
        contentPane.add(txtWelcomeToGreengrocery);
        txtWelcomeToGreengrocery.setColumns(10);

        txtUsername = new JTextField();
        txtUsername.setEditable(false);
        txtUsername.setBackground(new Color(128, 255, 0));
        txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
        txtUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtUsername.setText("Username:");
        txtUsername.setBounds(161, 116, 223, 19);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        txtPassword = new JTextField();
        txtPassword.setEditable(false);
        txtPassword.setBackground(new Color(128, 255, 0));
        txtPassword.setText("Password:");
        txtPassword.setHorizontalAlignment(SwingConstants.LEFT);
        txtPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtPassword.setBounds(161, 174, 223, 19);
        contentPane.add(txtPassword);
        txtPassword.setColumns(10);

        txtSelectRole = new JTextField();
        txtSelectRole.setForeground(new Color(0, 0, 0));
        txtSelectRole.setEditable(false);
        txtSelectRole.setBackground(new Color(255, 255, 255));
        txtSelectRole.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtSelectRole.setText("Select role:");
        txtSelectRole.setBounds(161, 235, 118, 19);
        contentPane.add(txtSelectRole);
        txtSelectRole.setColumns(10);

        formattedTextField = new JFormattedTextField();
        formattedTextField.setBounds(161, 134, 223, 19);
        contentPane.add(formattedTextField);

        passwordField = new JPasswordField();
        passwordField.setBounds(161, 192, 223, 19);
        contentPane.add(passwordField);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setBackground(new Color(128, 243, 22));
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton.setBounds(299, 235, 85, 38);
        contentPane.add(btnNewButton);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.addItem("Admin");
        comboBox.addItem("Staff");
        comboBox.setBounds(161, 253, 118, 20);
        contentPane.add(comboBox);

        // Adding ActionListener to the Login button
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = formattedTextField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) comboBox.getSelectedItem();

                // Check if username, password, and role match
                if (username.equals("admin") && role.equals("Admin")) {
                    // If admin
                    if (password.equals("admin")) {
                        // Admin login successful
                        JOptionPane.showMessageDialog(contentPane, "Admin login successful");
                        // Close the current login window
                        dispose();
                        // Open the AdminHomePage
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new AdminHomePage();
                            }
                        });
                    } else {
                        // Wrong password for admin
                        JOptionPane.showMessageDialog(contentPane, "Incorrect password for Admin. Please try again.");
                    }
                } else if (username.equals("staff") && role.equals("Staff")) {
                    // If user
                    if (password.equals("staff")) {
                        // Staff login successful
                        JOptionPane.showMessageDialog(contentPane, "Staff login successful");
                        // Close the current login window
                        dispose();
                        // Open the StaffHomePage
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                            	 new StaffHomePage().setVisible(true); // Show StaffHomePage
                            }
                        });
                    } else {
                        // Wrong password for staff
                        JOptionPane.showMessageDialog(contentPane, "Incorrect password for Staff. Please try again.");
                    }
                } else {
                    // If login fails
                    JOptionPane.showMessageDialog(contentPane, "Invalid username or role. Please log in as Staff or Admin.");
                }
            }
        });

    }	
}
