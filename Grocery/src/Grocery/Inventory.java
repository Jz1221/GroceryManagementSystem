package Grocery;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Inventory extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable wetFoodTable;
    private JTable dryFoodTable;
    private JButton btnBackToHomePage;

    // Singleton pattern for Inventory
    private static Inventory instance;

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                instance = new Inventory();
                instance.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Inventory() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Sample data for demonstration
        Object[][] wetFoodData = {
                {"Lettuce", 10, 2.5, "Wet Food"},
                {"Tomatoes", 15, 3.0, "Wet Food"},
                {"Eggs", 20, 2.0, "Wet Food"},
                {"Chicken", 50, 6.0, "Wet Food"},
                {"Yogurt", 25, 3.0, "Wet Food"},
                {"Salmon", 20, 10.0, "Wet Food"},
                {"Apple", 20, 5.8, "Wet Food"},
                {"Steak", 10, 145.0, "Wet Food"},
                {"Cheese", 25, 11.0, "Wet Food"}
        };

        Object[][] dryFoodData = {
                {"Cookies", 30, 1.5, "Dry Food"},
                {"Bread", 15, 4.3, "Dry Food"},
                {"Pasta", 15, 36.0, "Dry Food"},
                {"Flour", 35, 3.5, "Dry Food"},
                {"Sugar", 40, 4.8, "Dry Food"},
                {"Peanuts", 20, 18.0, "Dry Food"},
                {"Black beans", 15, 6.5, "Dry Food"},
                {"Chips", 50, 5.09, "Dry Food"}
        };

        
        String[] columnNames = {"Food Item", "Quantity", "Price", "Food Type"};

        
        DefaultTableModel wetFoodModel = new DefaultTableModel(wetFoodData, columnNames);
        DefaultTableModel dryFoodModel = new DefaultTableModel(dryFoodData, columnNames);

        
        wetFoodTable = new JTable(wetFoodModel);
        dryFoodTable = new JTable(dryFoodModel);

        
        wetFoodTable.getColumnModel().getColumn(2).setCellRenderer(new DecimalFormatRenderer());
        dryFoodTable.getColumnModel().getColumn(2).setCellRenderer(new DecimalFormatRenderer());

        
        JScrollPane wetScrollPane = new JScrollPane(wetFoodTable);
        wetScrollPane.setBounds(30, 40, 500, 300);
        contentPane.add(wetScrollPane);

        
        JScrollPane dryScrollPane = new JScrollPane(dryFoodTable);
        dryScrollPane.setBounds(600, 40, 500, 300);
        contentPane.add(dryScrollPane);

        btnBackToHomePage = new JButton("Back to Home Page");
        btnBackToHomePage.setBounds(30, 9, 150, 21);
        contentPane.add(btnBackToHomePage);

        // Add action listener to the "Back" button
        btnBackToHomePage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close current frame
                dispose();
                // Open staff home page frame
                StaffHomePage staffHomePage = new StaffHomePage();
                staffHomePage.setVisible(true);
            }
        });
    }

    // Custom cell renderer for formatting decimal values and center-aligning the content
    private class DecimalFormatRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        private static final DecimalFormat formatter = new DecimalFormat("#,##0.00");

        public DecimalFormatRenderer() {
            setHorizontalAlignment(JLabel.CENTER); // Center alignment
        }

        public void setValue(Object value) {
            if ((value != null) && (value instanceof Number)) {
                value = formatter.format(value);
            }
            setText((String) value);
        }
    }

    // Method to update quantity of an item in the inventory
    public void updateItemQuantity(String itemName, int newQuantity) {
        DefaultTableModel wetModel = (DefaultTableModel) wetFoodTable.getModel();
        DefaultTableModel dryModel = (DefaultTableModel) dryFoodTable.getModel();

        for (int i = 0; i < wetModel.getRowCount(); i++) {
            if (wetModel.getValueAt(i, 0).equals(itemName)) {
                wetModel.setValueAt(newQuantity, i, 1);
                return;
            }
        }

        for (int i = 0; i < dryModel.getRowCount(); i++) {
            if (dryModel.getValueAt(i, 0).equals(itemName)) {
                dryModel.setValueAt(newQuantity, i, 1);
                return;
            }
        }
    }

	public void createAndShowGUI() {
		// TODO Auto-generated method stub
		
	}

    
    
}
