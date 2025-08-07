package Grocery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;

public class PrintReceipt extends JFrame implements Printable {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> foodTypeComboBox;
    private JComboBox<String> foodNameComboBox;
    private JTextField quantityField;
    private JComboBox<String> paymentMethodComboBox;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalLabel;
    private double totalPrice = 0.0;

    private final String[] WET_FOODS = {"Chicken", "Yogurt", "Eggs", "Salmon", "Apple", "Steak", "Cheese"};
    private final double[] WET_FOOD_PRICES = {6.0, 3.0, 20.0, 10.0, 5.8, 145.0, 11.0};
    @SuppressWarnings("unused")
    private final int[] WET_FOOD_QUANTITIES = {20, 25, 20, 20, 20, 10, 25};

    private final String[] DRY_FOODS = {"Cookies", "Bread", "Pasta", "Flour", "Sugar", "Peanuts", "Black beans", "Chips"};
    private final double[] DRY_FOOD_PRICES = {1.5, 4.3, 36.0, 3.5, 4.8, 18.0, 6.5, 5.09};
    @SuppressWarnings("unused")
    private final int[] DRY_FOOD_QUANTITIES = {30, 15, 15, 35, 40, 20, 15, 50};

    // Define tax rate
    private final double TAX_RATE = 0.06; // 6% tax rate

    public PrintReceipt() {
        setTitle("Grocery Receipt");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel foodTypeLabel = new JLabel("Food Type:");
        panel.add(foodTypeLabel);

        String[] foodTypes = {"Wet Food", "Dry Food"};
        foodTypeComboBox = new JComboBox<>(foodTypes);
        panel.add(foodTypeComboBox);

        JLabel foodNameLabel = new JLabel("Food Name:");
        panel.add(foodNameLabel);

        foodNameComboBox = new JComboBox<>();
        panel.add(foodNameComboBox);

        foodTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) foodTypeComboBox.getSelectedItem();
                if (selectedType.equals("Wet Food")) {
                    foodNameComboBox.setModel(new DefaultComboBoxModel<>(WET_FOODS));
                } else if (selectedType.equals("Dry Food")) {
                    foodNameComboBox.setModel(new DefaultComboBoxModel<>(DRY_FOODS));
                }
            }
        });

        JLabel quantityLabel = new JLabel("Quantity:");
        panel.add(quantityLabel);

        quantityField = new JTextField();
        panel.add(quantityField);

        JLabel paymentLabel = new JLabel("Payment Method:");
        panel.add(paymentLabel);

        String[] paymentMethods = {"Cash", "Credit Card", "Debit Card", "TNG e-wallet"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        panel.add(paymentMethodComboBox);

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foodType = (String) foodTypeComboBox.getSelectedItem();
                String foodName = (String) foodNameComboBox.getSelectedItem();
                String quantity = quantityField.getText();
                String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();

                if (foodName.isEmpty() || quantity.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Pleaseenter a quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double price = 0.0;
                // Determine the price based on food type and name
                if (foodType.equals("Wet Food")) {
                    int index = findIndex(WET_FOODS, foodName);
                    price = WET_FOOD_PRICES[index];
                } else if (foodType.equals("Dry Food")) {
                    int index = findIndex(DRY_FOODS, foodName);
                    price = DRY_FOOD_PRICES[index];
                }

                int qty = Integer.parseInt(quantity);
                double itemTotalPrice = qty * price;
                totalPrice += itemTotalPrice;

                Object[] row = {foodType, foodName, quantity, price, itemTotalPrice, paymentMethod};
                tableModel.addRow(row);
            }
        });
        panel.add(addItemButton);

        // Initialize the table
        String[] columnNames = {"Food Type", "Food Name", "Quantity", "Price (per item)", "Total Price", "Payment Method"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton printButton = new JButton("Print Receipt");
        getContentPane().add(printButton, BorderLayout.SOUTH);
        printButton.addActionListener(e -> {
            try {
                boolean complete = table.print();
                if (complete) {
                    JOptionPane.showMessageDialog(this, "Printing Successful!", "Print Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Printing Cancelled!", "Print Status", JOptionPane.ERROR_MESSAGE);
                }
            } catch (PrinterException pe) {
                JOptionPane.showMessageDialog(this, "Printing Failed: " + pe.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton deleteButton = new JButton("Delete Receipt");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0); // Clear all rows from the table
                totalPrice = 0.0; // Reset total price
                updateTotalPrice();
            }
        });
        panel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                StaffHomePage staffHomePage = new StaffHomePage(); // Create an instance of StaffHomePage
                staffHomePage.setVisible(true); // Show the StaffHomePage
            }
        });
        panel.add(backButton);

        JButton calculateTotalButton = new JButton("Calculate Total Price");
        calculateTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalPrice();
            }
        });
        panel.add(calculateTotalButton);

        totalLabel = new JLabel();
        panel.add(totalLabel);
    }

    private void updateTotalPrice() {
        // Calculate and display total price with tax
        double taxAmount = totalPrice * TAX_RATE;
        double totalPriceWithTax = totalPrice + taxAmount;
        totalLabel.setText("Total Price (incl. tax): RM" + totalPriceWithTax);
    }

    private void calculateTotalPrice() {
        totalPrice = 0.0;
        for (int i = 0; i < table.getRowCount(); i++) {
            double itemTotalPrice = (double) table.getValueAt(i, 4);
            totalPrice += itemTotalPrice;
        }
        updateTotalPrice();
    }

    private int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1; // If not found
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        table.print(graphics);

        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrintReceipt printReceipt = new PrintReceipt();
            printReceipt.setVisible(true);
        });
    }
}
