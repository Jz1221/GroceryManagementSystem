package Grocery;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Managestaff extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtAdminpageManageStaff;
    private ArrayList<Staff> staffList;
    private JComboBox<String> staffComboBox;
    private JComboBox<String> reasonComboBox;
    private JTextField otherReasonTextField;
    private final String STAFF_FILE_PATH = "staff.txt";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Managestaff frame = new Managestaff();
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
    public Managestaff() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Initialize staff list and add example staff members
        staffList = new ArrayList<>();
        // Load staff from file
        loadStaffFromFile();

        JButton btnNewButton = new JButton("Back");
        btnNewButton.setBounds(341, 232, 85, 21);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open AdminHomePage when "Back" button is clicked
                AdminHomePage adminHomePage = new AdminHomePage();
                adminHomePage.setVisible(true);
                dispose(); // Close the current frame
            }
        });
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("List of staff");
        btnNewButton_1.setFont(new Font("Tahoma", Font.ITALIC, 18));
        btnNewButton_1.setBounds(10, 52, 146, 89);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display the list of staff
                displayStaffList();
            }
        });
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_1_1 = new JButton("Add staff");
        btnNewButton_1_1.setFont(new Font("Tahoma", Font.ITALIC, 18));
        btnNewButton_1_1.setBounds(210, 52, 146, 89);
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Example implementation to add staff member
                String name = JOptionPane.showInputDialog(null, "Enter staff name:");
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid staff name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int age;
                try {
                    age = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter staff age:"));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Combo box for selecting gender
                String[] genders = {"Male", "Female", "Other"};
                JComboBox<String> genderComboBox = new JComboBox<>(genders);
                JOptionPane.showMessageDialog(null, genderComboBox, "Select Gender", JOptionPane.QUESTION_MESSAGE);
                String gender = (String) genderComboBox.getSelectedItem();

                // Add the new staff member to the list
                Staff newStaff = new Staff(name, age, gender);
                staffList.add(newStaff);

                // Save the updated staff list to file
                saveStaffToFile();

                // Show success message
                JOptionPane.showMessageDialog(null, "Staff member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reload the staff list from file
                loadStaffFromFile();
            }

        });
        contentPane.add(btnNewButton_1_1);

        JButton btnNewButton_1_1_1 = new JButton("Remove staff");
        btnNewButton_1_1_1.setFont(new Font("Tahoma", Font.ITALIC, 18));
        btnNewButton_1_1_1.setBounds(10, 164, 146, 89);
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove staff member
                String[] staffNames = new String[staffList.size()];
                for (int i = 0; i < staffList.size(); i++) {
                    staffNames[i] = staffList.get(i).getName();
                }
                staffComboBox = new JComboBox<>(staffNames);
                reasonComboBox = new JComboBox<>(new String[]{"Resigned", "Terminated", "Other"});
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("Select Staff to Remove:"));
                panel.add(staffComboBox);
                panel.add(new JLabel("Select Reason:"));
                panel.add(reasonComboBox);
                int option = JOptionPane.showConfirmDialog(null, panel, "Remove Staff", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    String nameToRemove = (String) staffComboBox.getSelectedItem();
                    String reason = (String) reasonComboBox.getSelectedItem();
                    if (reason.equals("Other")) {
                        otherReasonTextField = new JTextField(10);
                        JPanel otherReasonPanel = new JPanel();
                        otherReasonPanel.add(new JLabel("Enter Reason:"));
                        otherReasonPanel.add(otherReasonTextField);
                        int otherOption = JOptionPane.showConfirmDialog(null, otherReasonPanel, "Other Reason", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (otherOption == JOptionPane.OK_OPTION) {
                            String otherReason = otherReasonTextField.getText();
                            removeStaff(nameToRemove, otherReason);
                        }
                    } else {
                        removeStaff(nameToRemove, reason);
                    }
                }
            }
        });
        contentPane.add(btnNewButton_1_1_1);

        txtAdminpageManageStaff = new JTextField();
        txtAdminpageManageStaff.setForeground(new Color(0, 0, 0));
        txtAdminpageManageStaff.setEditable(false);
        txtAdminpageManageStaff.setFont(new Font("Tahoma", Font.ITALIC, 17));
        txtAdminpageManageStaff.setHorizontalAlignment(SwingConstants.CENTER);
        txtAdminpageManageStaff.setText("AdminPage Manage staff");
        txtAdminpageManageStaff.setBounds(103, 10, 200, 32);
        contentPane.add(txtAdminpageManageStaff);
        txtAdminpageManageStaff.setColumns(10);
    }

    // Method to display the list of staff
    private void displayStaffList() {
        StringBuilder staffInfo = new StringBuilder("<html><body><table border='1'>");
        staffInfo.append("<tr><th>Staff Name</th><th>Age</th><th>Gender</th></tr>");
        for (Staff staff : staffList) {
            staffInfo.append("<tr><td>").append(staff.getName()).append("</td><td>")
                    .append(staff.getAge()).append("</td><td>")
                    .append(staff.getGender()).append("</td></tr>");
        }
        staffInfo.append("</table></body></html>");
        // Show staff information in a JOptionPane
        JOptionPane.showMessageDialog(null, staffInfo.toString(), "List of Staff", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to remove staff member by name
    private void removeStaff(String nameToRemove, String reason) {
        Staff staffToRemove = null;
        for (Staff staff : staffList) {
            if (staff.getName().equalsIgnoreCase(nameToRemove)) {
                staffToRemove = staff;
                break;
            }
        }
        if (staffToRemove != null) {
            staffList.remove(staffToRemove);
            // Save the updated staff list to file
            saveStaffToFile();
            JOptionPane.showMessageDialog(null, "Staff member removed successfully due to: " + reason, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Staff member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Reload the staff list from file
        loadStaffFromFile();
    }

    // Method to load staff from file
    private void loadStaffFromFile() {
        staffList.clear(); // Clear the existing staff list
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String gender = parts[2];
                    staffList.add(new Staff(name, age, gender));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save staff list to file
    private void saveStaffToFile() {
        try (FileWriter writer = new FileWriter(STAFF_FILE_PATH)) {
            for (Staff staff : staffList) {
                writer.write(staff.getName() + "," + staff.getAge() + "," + staff.getGender() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Staff {
    private String name;
    private int age;
    private String gender;

    public Staff(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
