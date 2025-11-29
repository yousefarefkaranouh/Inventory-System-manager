import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// ================= PRODUCT CLASS =================
class Product {
    //attributes 
    private int productId;
    private String productName;             
    private double price;
    private int quantity;

    //constructor
    public Product(int productId,String productName,double price,int quantity){  
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
    
    //getter methods
    public int getProductId(){
        return productId;
    }
    public String getProductName(){
        return productName;
    }
    public double getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }

    //setter methods
    public void setProductId(int productId){
        this.productId = productId;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    // Displays product information
    public void displayProductInfo(){
        System.out.println("Product ID: " + getProductId());
        System.out.println("Product name: " + getProductName());
        System.out.println("Product price: " + getPrice());
        System.out.println("Product quantity: " + getQuantity());
    }
}


// ================= PERISHABLE PRODUCT CLASS =================
class PerishableProduct extends Product{
    private final String expiryDate;  //expiry date is fixed (unchangable)

    // perishable product constructor
    public PerishableProduct(int productId,String productName,double price,int quantity,String expiryDate){
        super(productId,productName,price,quantity);
        this.expiryDate = expiryDate;
    }
    public String getExpiryDate(){
        return expiryDate;
    }

    // Overrides displayProductInfo to include expiry date
    @Override 
    public void displayProductInfo(){
        super.displayProductInfo();
        System.out.println("Expiry date: " + getExpiryDate());
    }
}


// ================= INVENTORY MANAGER =================
class InventoryManager{
    private final Product[] productsArr = new Product[50]; //fixed array size of 50
    private int count = 0; // acts as a pointer to next free position

    //getter method for product array
    public Product[] getProductArr(){
        return productsArr;
    }

    // Adds a product to the inventory
    public void addProduct(Product p){
        if (count >= 50){
            System.out.println("Inventory is full");
        }
        else{
            productsArr[count] = p;
            count ++;
            System.out.println("Product added successfully");
        }
    }

    // Displays all products in the inventory
    public void viewProducts(){
        if (count == 0){
            System.out.println("No products in inventory");
        }
        else{
            for (int i = 0; i < count; i++){
                System.out.println("product " + (i+1));
                System.out.println("");
                productsArr[i].displayProductInfo();
                System.out.println("");
            }  
        }
    }

    // Updates a product's name, price, or quantity
    public boolean updateProduct(int productId,int choice,String newName,double newPrice,int newQuantity){
        boolean found = false;
        for (int i = 0; i < count; i++){
            if (productsArr[i].getProductId() == productId){
                found = true;
                switch (choice){
                    case 1:
                        productsArr[i].setProductName(newName);
                        System.out.println("Name updated successfully");
                        break;
                    case 2:
                        productsArr[i].setPrice(newPrice);
                        System.out.println("Price updated successfully");
                        break;
                    case 3:
                        productsArr[i].setQuantity(newQuantity);
                        System.out.println("Quantity updated successfully");
                        break;
                }
                return true;  // return true if product found and updated
            }
        } 
        if (!found){
            System.out.println("product ID not found.");
        }
        return false; // return false if product not found
    }

    // Deletes a product by ID
    public boolean deleteProduct(int productId){
        boolean found = false;
        for (int i = 0; i < count; i++){
            if (productsArr[i].getProductId() == productId){
                found = true;
                for(int j = i; j < count - 1; j++){
                    productsArr[j] = productsArr[j+1];
                }
                productsArr[count-1] = null;
                count --;
                i--;  // reason why i--
                      //When you shift the array left the next element moves into the same index.
                System.out.println("Product deleted successfully.");
                return true; // return true if deleted
            }
        }
        if (!found){
            System.out.println("Product ID not found.");
        }
        return false; // return false if not found
    }

    // Sorts the products array by product ID in ascending order (bubble sort)
    public void bubbleSortArr(Product[] productsArr){
        for (int i = 0; i < count-1; i++){  
            for (int j = 0; j < count-1-i; j++){   
                if (productsArr[j].getProductId() > productsArr[j+1].getProductId()){
                    Product temp = productsArr[j];
                    productsArr[j] = productsArr[j+1];
                    productsArr[j+1] = temp;
                }
            }
        }
    }

    // Performs binary search on a sorted Product array by product ID
    // Returns the index of the product if found, otherwise -1
    public int binarySearch(Product[] productsArr, int key){
        int low = 0;
        int high = count-1;
        while (low <= high){
            int mid = (high+low) / 2;
            if (productsArr[mid].getProductId() == key){
                return mid;
            }
            else if (productsArr[mid].getProductId() < key){
                low = mid + 1;
            }
            else{
                high = mid - 1;
            }
        }
        return -1;
    }
}


// ================= DISPLAY CLASS =================
class Display{
    // Displays main menu options
    public static void displayMenu(){
        System.out.println("====== Inventory Management System ======");
        System.out.println("");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Search Product by ID");
        System.out.println("6. Exit");
    }
}


// ================= INPUT HELPER =================
class InputHelper{
    // Safe integer input
    public static int getIntSafe(String msg , Scanner keyboard){
        while (true){
            try {
                System.out.println(msg);
                int value = keyboard.nextInt();
                keyboard.nextLine();
                return value;
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter an integer.");
                keyboard.nextLine();
            }
        }
    }

    // Safe double input
    public static double getDoubleSafe(String msg , Scanner keyboard){
        while (true){
            try {
                System.out.println(msg);
                double value = keyboard.nextDouble();
                keyboard.nextLine();
                return value;
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                keyboard.nextLine();
            }
        }
    }

    // Safe string (cannot be empty)
    public static String getStringSafe(String msg , Scanner keyboard){
        while (true){
            System.out.println(msg);
            String input = keyboard.nextLine();
            if (!input.isEmpty()){
                return input;
            }
            else{
                System.out.println("Input cannot be empty.");
            }
        }
    }

    // Safe yes/no input
    public static String getYesNoSafe(String msg , Scanner keyboard){
        while (true){
            System.out.println(msg);
            String input = keyboard.nextLine();
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")){
                return input;
            }
            else{
                System.out.println("Invalid input. Enter (y/n).");
            }
        }
    }
}


 //GUI for the inventory system.

 
 public class Main {
 
     private InventoryManager store;  
 
     public Main(InventoryManager store) {
 
         this.store = store;
 
         // ---------------- MAIN WINDOW ----------------
         JFrame frame = new JFrame("Inventory Management System");
         frame.setSize(700, 500);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLocationRelativeTo(null); // center the window on screen
 
         // Soft blue color I picked for the background
         Color bg = new Color(235, 240, 255);
         frame.getContentPane().setBackground(bg);
 
         // Using GridBagLayout to make centering easier
         frame.setLayout(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10); // spacing around components
 



         // ---------------- DISPLAY AREA AT THE TOP ----------------
         // This is where I show results after button clicks
         JTextArea displayArea = new JTextArea(10, 50);
         displayArea.setEditable(false);
         displayArea.setBackground(bg);
         displayArea.setForeground(Color.BLACK);
         displayArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
 
         // Scroll so long text won’t overflow
         JScrollPane scroll = new JScrollPane(displayArea);
         scroll.getViewport().setBackground(bg);
         scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 230)));
 
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.fill = GridBagConstraints.BOTH;
         gbc.weightx = 1.0;
         gbc.weighty = 0.7; // give most of the space to the display area
         frame.add(scroll, gbc);
 





         // ---------------- BUTTON SECTION (CENTERED) ----------------
         // Panel that holds the buttons
         JPanel buttonPanel = new JPanel();
         buttonPanel.setBackground(bg);
         buttonPanel.setLayout(new GridLayout(5, 1, 0, 10)); // 5 buttons vertically 
 
         // Creating the buttons with helper method
         JButton addBtn = createButton("Add Product");
         JButton viewBtn = createButton("View Products");
         JButton updateBtn = createButton("Update Product");
         JButton deleteBtn = createButton("Delete Product");
         JButton searchBtn = createButton("Search Product");
 
         // Put buttons inside the panel
         JButton[] allButtons = {addBtn, viewBtn, updateBtn, deleteBtn, searchBtn};
         for (JButton b : allButtons) buttonPanel.add(b);
 
         // Place the button panel in the center below the display area
         gbc.gridx = 0;
         gbc.gridy = 1;
         gbc.fill = GridBagConstraints.NONE;
         gbc.anchor = GridBagConstraints.CENTER;
         gbc.weighty = 0.3;
         frame.add(buttonPanel, gbc);
 





         // ================= BUTTON ACTIONS ==================
 
         // ADD PRODUCT
         addBtn.addActionListener(e -> {
             try {
                 int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Product ID:"));
                 String name = JOptionPane.showInputDialog(frame, "Enter Product Name:");
                 double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter Product Price:"));
                 int qty = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Product Quantity:"));
 
                 // asking if it's perishable
                 int perishable = JOptionPane.showConfirmDialog(frame, "Is this a perishable product?", "Perishable", JOptionPane.YES_NO_OPTION);
 
                 if (perishable == JOptionPane.YES_OPTION) {
                     String expiry = JOptionPane.showInputDialog(frame, "Enter Expiry Date:");
                     store.addProduct(new PerishableProduct(id, name, price, qty, expiry));
                 } else {
                     store.addProduct(new Product(id, name, price, qty));
                 }
 
                 displayArea.setText("Product added successfully!");
             } 
             catch (Exception ex) {
                 JOptionPane.showMessageDialog(frame, "Invalid input (please enter a number)");
             }
         });
 



         // VIEW PRODUCTS
         viewBtn.addActionListener(e -> {
             StringBuilder sb = new StringBuilder();
             Product[] arr = store.getProductArr();
             boolean empty = true;
 
             // loop through the array to display everything
             for (int i = 0; i < 50; i++) {
                 if (arr[i] != null) {
                     empty = false;
                     sb.append("Product ").append(i + 1).append("\n");
                     sb.append("ID: ").append(arr[i].getProductId()).append("\n");
                     sb.append("Name: ").append(arr[i].getProductName()).append("\n");
                     sb.append("Price: ").append(arr[i].getPrice()).append("\n");
                     sb.append("Quantity: ").append(arr[i].getQuantity()).append("\n");
 
                     // If it has an expiry date show it
                     if (arr[i] instanceof PerishableProduct) {
                         sb.append("Expiry: ").append(((PerishableProduct) arr[i]).getExpiryDate()).append("\n");
                     }
 
                     sb.append("\n");
                 }
             }
 
             if (empty) sb.append("No products found.");
             displayArea.setText(sb.toString());
         });
 



         // UPDATE PRODUCT
         updateBtn.addActionListener(e -> {
             try {
                 int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Product ID to update:"));
                 String[] choices = {"Name", "Price", "Quantity"};
                 int op = JOptionPane.showOptionDialog(frame, "Choose what you want to update:", "Update",
                         JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[0]);
 
                 boolean success = false;

                 switch (op) {
                     case 0:
                         String newName = JOptionPane.showInputDialog(frame, "Enter new name:");
                         success = store.updateProduct(id, 1, newName, 0, 0);
                         break;
                     case 1:
                         double newPrice = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter new price:"));
                         success = store.updateProduct(id, 2, null, newPrice, 0);
                         break;
                     case 2:
                         int newQty = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter new quantity:"));
                         success = store.updateProduct(id, 3, null, 0, newQty);
                         break;
                 }

                 if (success)
                     displayArea.setText("Product updated successfully.");
                 else
                     displayArea.setText("Product ID not found.");

             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(frame, "Something went wrong.");
             }
         });


         // DELETE PRODUCT
         deleteBtn.addActionListener(e -> {
             try {
                 int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter ID to delete:"));
                 boolean success = store.deleteProduct(id);
                 if (success)
                     displayArea.setText("Product deleted successfully.");
                 else
                     displayArea.setText("Product ID not found.");
             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(frame, "Invalid ID.");
             }
         });


         // SEARCH PRODUCT
         searchBtn.addActionListener(e -> {
             try {
                 int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Product ID to search:"));
                 store.bubbleSortArr(store.getProductArr());
                 int pos = store.binarySearch(store.getProductArr(), id);
 
                 if (pos == -1) displayArea.setText("Product not found.");
                 else displayArea.setText("Product found at index: " + pos);
             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(frame, "Invalid input.");
             }
         });



         // finally show the framee
         frame.setVisible(true);
     }



     
     // ---------------------------------------------
     // helper method to make the buttons look similar
     // ---------------------------------------------
     private JButton createButton(String text) {
         JButton btn = new JButton(text);
 
         // just a simple style I like
         btn.setBackground(new Color(180, 190, 230));
         btn.setForeground(Color.BLACK);
         btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
         btn.setBorder(BorderFactory.createLineBorder(new Color(130, 140, 180)));
 
         return btn;
     }
 
     // ------------------ MAIN METHOD ------------------
     public static void main(String[] args) {
         InventoryManager manager = new InventoryManager();
         SwingUtilities.invokeLater(() -> new Main(manager));
     }
 }
