package Grocery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GroceryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double price;
    private int quantity;

    public GroceryItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


public class AdminHomePage extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final String FILENAME = "grocery_items.ser";
    private Map<String, List<GroceryItem>> groceryItems;
    private DefaultTableModel tableModel;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AdminHomePage adminHomePage = new AdminHomePage();
                adminHomePage.displayItems();
            }
        });
    }

    public AdminHomePage() {
        this.groceryItems = new HashMap<>();
        this.groceryItems.put("wet", new ArrayList<>());
        this.groceryItems.put("dry", new ArrayList<>());

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        tableModel.addColumn("Category");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Price (RM)");
        tableModel.addColumn("Quantity");

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        buttonPanel.add(deleteButton);

        JButton updateButton = new JButton("Update Quantity");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuantity();
            }
        });
        buttonPanel.add(updateButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the AdminHomePage window
                new Login().setVisible(true); // Open the Login window
            }
        });

        JButton manageStaffButton = new JButton("Manage Staff");
        manageStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageStaffWindow();
            }
        });
        buttonPanel.add(manageStaffButton);

        buttonPanel.add(logoutButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Admin Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        loadGroceryItems();
    }

    public void addItem() {
        String name = JOptionPane.showInputDialog("Enter item name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String priceInput = JOptionPane.showInputDialog("Enter item price:");
        if (priceInput == null || priceInput.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item price cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String quantityInput = JOptionPane.showInputDialog("Enter item quantity:");
        if (quantityInput == null || quantityInput.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item quantity cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid quantity format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String category = JOptionPane.showInputDialog("Enter item category (wet/dry):");
        if (category == null || category.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item category cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!category.equals("wet") && !category.equals("dry")) {
            JOptionPane.showMessageDialog(null, "Invalid category entered. Please enter 'wet' or 'dry'.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GroceryItem item = new GroceryItem(name, price, quantity);
        addGroceryItem(category, item);

        displayItems();
        saveGroceryItems(); // Save the grocery items after adding the new item
    }


    public void deleteItem() {
        String category = JOptionPane.showInputDialog("Enter item category to delete from (wet/dry):");
        List<GroceryItem> items = groceryItems.get(category);

        if (items == null || items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items found in the selected category.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder itemList = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            itemList.append(i + 1).append(". ").append(items.get(i).getName()).append("\n");
        }

        int selectedItemIndex = Integer.parseInt(JOptionPane.showInputDialog("Select the item to delete:\n" + itemList.toString())) - 1;

        if (selectedItemIndex < 0 || selectedItemIndex >= items.size()) {
            JOptionPane.showMessageDialog(null, "Invalid item selection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        items.remove(selectedItemIndex);

        displayItems();
        saveGroceryItems();
    }


    public void updateQuantity() {
        String category = JOptionPane.showInputDialog("Enter item category to update quantity (wet/dry):");
        List<GroceryItem> items = groceryItems.get(category);

        if (items == null || items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items found in the selected category.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder itemList = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            itemList.append(i + 1).append(". ").append(items.get(i).getName()).append("\n");
        }

        int selectedItemIndex = Integer.parseInt(JOptionPane.showInputDialog("Select the item to update quantity:\n" + itemList.toString())) - 1;

        if (selectedItemIndex < 0 || selectedItemIndex >= items.size()) {
            JOptionPane.showMessageDialog(null, "Invalid item selection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity:"));
        GroceryItem updatedItem = items.get(selectedItemIndex);
        updatedItem.setQuantity(newQuantity);

        displayItems();
        saveGroceryItems();

        // Update the quantity in the Inventory class
        Inventory inventory = new Inventory(); // Instantiate the Inventory class
        inventory.updateItemQuantity(updatedItem.getName(), newQuantity); // Call the updateQuantity method in Inventory
    }
    public void populateGroceryItems() {
        // Clear existing items
        groceryItems.get("wet").clear();
        groceryItems.get("dry").clear();

        displayItems(); // Display the items in the table
    }


    private void addGroceryItem(String category, GroceryItem item) {
        List<GroceryItem> items = groceryItems.get(category);
        items.add(item);
    }


    private void displayItems() {
        // Clear the existing table data
        tableModel.setRowCount(0);

        // Display wet food items
        displayItems("wet");

        // Display dry food items
        displayItems("dry");
    }

    private void displayItems(String category) {
        List<GroceryItem> items = groceryItems.get(category);
        if (items != null) {
            for (GroceryItem item : items) {
                // Format the price to two decimal places
                String formattedPrice = String.format("%.2f", item.getPrice());
                
                // Add items to the respective table based on the category
                if (category.equals("wet")) {
                    tableModel.addRow(new Object[]{"Wet Food", item.getName(), formattedPrice, item.getQuantity()});
                } else if (category.equals("dry")) {
                    tableModel.addRow(new Object[]{"Dry Food", item.getName(), formattedPrice, item.getQuantity()});
                }
            }
        }
    }



    private void saveGroceryItems() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(groceryItems);
            JOptionPane.showMessageDialog(null, "Grocery items saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving grocery items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    private void loadGroceryItems() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                groceryItems = (Map<String, List<GroceryItem>>) obj;
                displayItems();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openManageStaffWindow() {
        dispose(); // Close the AdminHomePage window
        new Managestaff().setVisible(true);
    }

	public void setUpdateQuantityVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

    

}
