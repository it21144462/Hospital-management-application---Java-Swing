//package medicareplus;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//
//public class Inventory extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
//    private JTextField txtDrugCode;
//    private JTextField txtDrugName;
//    private JTextField txtTotalCost;
//    private JLabel txtPrescriptionID;
//    private JTextField txtPay;
//    private JTextField txtBalance;
//    private JSpinner spinnerQty;
//    private JTable table;
//    private DefaultTableModel tableModel;
//    
//    Connection con;
//    PreparedStatement pst;
//    ResultSet rs;
//    
//    public void Connect() {
//    	try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus","root","");
//		} catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//    
//    // New fields for prescriptionNo
//    private String prescriptionNo1;
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                Inventory frame = new Inventory("PC001");
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    /**
//     * Create the frame.
//     */
//    public Inventory(String prescriptionNo) {
//    	
//    	prescriptionNo1 = prescriptionNo;
//    	
//    	setTitle("Medicare Plus+ Inventory Management");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 800, 600);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(null);
//        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
//        setContentPane(contentPane);
//        
//        // Title
////        JLabel lblTitle = new JLabel("Inventory Management");
//        JLabel lblTitle = new JLabel("Sales Management");
//        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
//        lblTitle.setForeground(Color.YELLOW);
//        lblTitle.setBounds(300, 10, 250, 30);
//        contentPane.add(lblTitle);
//        
////     // Horizontal Line (Separator)
////        JSeparator separator = new JSeparator();
////        separator.setForeground(Color.YELLOW); // Line color
////        separator.setBounds(20, 40, 740, 2); // Adjust position and width
////        contentPane.add(separator);
//
//     // Labels and text fields
//        JLabel lblPrescriptionID = new JLabel("Prescription ID");
//        lblPrescriptionID.setForeground(Color.YELLOW);
//        lblPrescriptionID.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblPrescriptionID.setBounds(20, 70, 120, 25); // Moved down by 20
//        contentPane.add(lblPrescriptionID);
//
//        txtPrescriptionID = new JLabel(prescriptionNo);
//        txtPrescriptionID.setBounds(150, 70, 150, 25); // Moved down by 20
//        txtPrescriptionID.setForeground(Color.YELLOW);
//        txtPrescriptionID.setFont(new Font("Tahoma", Font.BOLD, 12));
//        contentPane.add(txtPrescriptionID);
//
//        JLabel lblDrugCode = new JLabel("Drug Code");
//        lblDrugCode.setForeground(Color.YELLOW);
//        lblDrugCode.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblDrugCode.setBounds(20, 110, 120, 25); // Moved down by 20
//        contentPane.add(lblDrugCode);
//
//        txtDrugCode = new JTextField();
//        txtDrugCode.setBounds(150, 110, 150, 25); // Moved down by 20
//        contentPane.add(txtDrugCode);
//
//        JLabel lblDrugName = new JLabel("Drug Name");
//        lblDrugName.setForeground(Color.YELLOW);
//        lblDrugName.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblDrugName.setBounds(20, 150, 120, 25); // Moved down by 20
//        contentPane.add(lblDrugName);
//
//        txtDrugName = new JTextField();
//        txtDrugName.setBounds(150, 150, 150, 25); // Moved down by 20
//        contentPane.add(txtDrugName);
//
//        JLabel lblQty = new JLabel("Qty");
//        lblQty.setForeground(Color.YELLOW);
//        lblQty.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblQty.setBounds(20, 190, 120, 25); // Moved down by 20
//        contentPane.add(lblQty);
//
//        spinnerQty = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
//        spinnerQty.setBounds(150, 190, 50, 25); // Moved down by 20
//        contentPane.add(spinnerQty);
//
//        JButton btnAdd = new JButton("Add");
//        btnAdd.setBounds(220, 230, 80, 25); // Moved down by 20
//        contentPane.add(btnAdd);
//
//        JLabel lblTotalCost = new JLabel("TotalCost");
//        lblTotalCost.setForeground(Color.YELLOW);
//        lblTotalCost.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblTotalCost.setBounds(400, 70, 120, 25); // Moved down by 20
//        contentPane.add(lblTotalCost);
//
//        txtTotalCost = new JTextField();
//        txtTotalCost.setBounds(500, 70, 150, 25); // Moved down by 20
//        txtTotalCost.setEditable(false);
//        contentPane.add(txtTotalCost);
//
//        JLabel lblPay = new JLabel("Pay");
//        lblPay.setForeground(Color.YELLOW);
//        lblPay.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblPay.setBounds(400, 110, 120, 25); // Moved down by 20
//        contentPane.add(lblPay);
//
//        txtPay = new JTextField();
//        txtPay.setBounds(500, 110, 150, 25); // Moved down by 20
//        contentPane.add(txtPay);
//
//        JLabel lblBalance = new JLabel("Balance");
//        lblBalance.setForeground(Color.YELLOW);
//        lblBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
//        lblBalance.setBounds(400, 150, 120, 25); // Moved down by 20
//        contentPane.add(lblBalance);
//
//        txtBalance = new JTextField();
//        txtBalance.setBounds(500, 150, 150, 25); // Moved down by 20
//        txtBalance.setEditable(false);
//        contentPane.add(txtBalance);
//
//        JButton btnSalesUpdate = new JButton("Sales Update");
//        btnSalesUpdate.setBounds(500, 230, 120, 30); // Moved down by 20
//        contentPane.add(btnSalesUpdate);
//
//        // Table
//        tableModel = new DefaultTableModel(new Object[]{"Prescription ID", "Drug Code", "Drug Name", "Qty", "Price", "Total"}, 0);
//        table = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBounds(20, 270, 740, 250); // Moved down by 20
//        contentPane.add(scrollPane);
//
//        // Add Button Action
//        btnAdd.addActionListener(new ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//          	addintn();
//          }
//        });
//
//        // Add ActionListener for SalesUp button
//        btnSalesUpdate.addActionListener(new ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//          	addsalesupdate();
//          }
//        });
//        
//        setLocationRelativeTo(null); // This centers the Inventory window
//    }
//    
//    private void addintn() {
//    	String prescriptionID = txtPrescriptionID.getText();
//        String drugCode = txtDrugCode.getText();
//        String drugName = txtDrugName.getText();
//        int qty = (int) spinnerQty.getValue();
//        double price = 50.00; // Sample price for testing
//        double total = qty * price;
//
//        // Add row to the table
//        tableModel.addRow(new Object[]{prescriptionID, drugCode, drugName, qty, price, total});
//
//        // Update total cost
//        double totalCost = Double.parseDouble(txtTotalCost.getText().isEmpty() ? "0" : txtTotalCost.getText());
//        totalCost += total;
//        txtTotalCost.setText(String.valueOf(totalCost));
//    }
//    
//    private void addsalesupdate() {
//    	double totalCost = Double.parseDouble(txtTotalCost.getText().isEmpty() ? "0" : txtTotalCost.getText());
//        double pay = Double.parseDouble(txtPay.getText().isEmpty() ? "0" : txtPay.getText());
//        double balance = pay - totalCost;
//        txtBalance.setText(String.valueOf(balance));
//    }
//}
package medicareplus;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Inventory extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtDrugCode;
    private JTextField txtDrugName;
    private JTextField txtTotalCost;
    private JLabel txtPrescriptionID;
    private JTextField txtPay;
    private JTextField txtBalance;
    private JSpinner spinnerQty;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSalesUpdate; // Declare the Sales Update button
    private JButton btnAdd; // Declare the Sales Update button
    private JButton btnExit;
    private JButton btnBrowse;



    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    private String prescriptionNo1;
    
    /**
     * Clears the input fields after adding a drug.
     */
    private void clearFields() {
        txtDrugCode.setText("");
        txtDrugName.setText("");
        spinnerQty.setValue(0);
        txtDrugCode.requestFocus(); // Set focus back to Drug Code field
       
		btnAdd.setEnabled(false);   // Disable Add button
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Inventory frame = new Inventory("PC001");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Inventory(String prescriptionNo) {
        prescriptionNo1 = prescriptionNo;

        setTitle("Medicare Plus+ Inventory Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(30, 30, 30));
        setContentPane(contentPane);

        // Title
        JLabel lblTitle = new JLabel("Sales Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setBounds(300, 10, 250, 30);
        contentPane.add(lblTitle);

        // Labels and text fields
        JLabel lblPrescriptionID = new JLabel("Prescription ID");
        lblPrescriptionID.setForeground(Color.YELLOW);
        lblPrescriptionID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPrescriptionID.setBounds(20, 70, 120, 25);
        contentPane.add(lblPrescriptionID);

        txtPrescriptionID = new JLabel(prescriptionNo);
        txtPrescriptionID.setBounds(150, 70, 150, 25);
        txtPrescriptionID.setForeground(Color.YELLOW);
        txtPrescriptionID.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(txtPrescriptionID);

        JLabel lblDrugCode = new JLabel("Drug Code");
        lblDrugCode.setForeground(Color.YELLOW);
        lblDrugCode.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDrugCode.setBounds(20, 110, 120, 25);
        contentPane.add(lblDrugCode);

//        txtDrugCode = new JTextField();
//        txtDrugCode.setBounds(150, 110, 150, 25);
//        contentPane.add(txtDrugCode);
     // JTextField for Drug Code
        txtDrugCode = new JTextField();
        txtDrugCode.setBounds(150, 110, 150, 25);

        // Make input uppercase and restrict to alphanumeric
        txtDrugCode.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isLetterOrDigit(c))) {
                    e.consume();  // Ignore non-alphanumeric characters
                }
                // Convert input to uppercase
                txtDrugCode.setText(txtDrugCode.getText().toUpperCase());
            }
        });

        contentPane.add(txtDrugCode);

        JLabel lblDrugName = new JLabel("Drug Name");
        lblDrugName.setForeground(Color.YELLOW);
        lblDrugName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDrugName.setBounds(20, 150, 120, 25);
        contentPane.add(lblDrugName);

        txtDrugName = new JTextField();
        txtDrugName.setBounds(150, 150, 150, 25);
        txtDrugName.setEditable(false);
        contentPane.add(txtDrugName);

        JLabel lblQty = new JLabel("Qty");
        lblQty.setForeground(Color.YELLOW);
        lblQty.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblQty.setBounds(20, 190, 120, 25);
        contentPane.add(lblQty);

        spinnerQty = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        spinnerQty.setBounds(150, 190, 50, 25);
        spinnerQty.setEnabled(false);
        contentPane.add(spinnerQty);
        
        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(130, 230, 80, 25);
        contentPane.add(btnBrowse);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(220, 230, 80, 25);
        btnAdd.setEnabled(false); // Disable Add button by default
        contentPane.add(btnAdd);

        JLabel lblTotalCost = new JLabel("Total Cost");
        lblTotalCost.setForeground(Color.YELLOW);
        lblTotalCost.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotalCost.setBounds(400, 70, 120, 25);
        contentPane.add(lblTotalCost);

        txtTotalCost = new JTextField();
        txtTotalCost.setBounds(500, 70, 150, 25);
        txtTotalCost.setEditable(false);
        contentPane.add(txtTotalCost);

        JLabel lblPay = new JLabel("Pay");
        lblPay.setForeground(Color.YELLOW);
        lblPay.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPay.setBounds(400, 110, 120, 25);
        contentPane.add(lblPay);

        txtPay = new JTextField();
        txtPay.setBounds(500, 110, 150, 25);
        contentPane.add(txtPay);
        
        txtPay.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateSalesButtonState();
            }
        });


        JLabel lblBalance = new JLabel("Balance");
        lblBalance.setForeground(Color.YELLOW);
        lblBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBalance.setBounds(400, 150, 120, 25);
        contentPane.add(lblBalance);

        txtBalance = new JTextField();
        txtBalance.setBounds(500, 150, 150, 25);
        txtBalance.setEditable(false);
        contentPane.add(txtBalance);

        btnSalesUpdate = new JButton("Sales Update");
        btnSalesUpdate.setBounds(500, 230, 120, 30);
        btnSalesUpdate.setEnabled(false); // Disable by default
        contentPane.add(btnSalesUpdate);
        
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(630, 230, 70, 30); // Align next to Delete button
        contentPane.add(btnExit);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Prescription ID", "Drug Code", "Drug Name", "Qty", "Price", "Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 270, 740, 250);
        contentPane.add(scrollPane);

        // Key listener for Drug Code
        txtDrugCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String drugCode = txtDrugCode.getText().trim();
                    if (drugCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid Drug Code!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try {
                        pst = con.prepareStatement("SELECT itemname, qty FROM item WHERE itemno = ?");
                        pst.setString(1, drugCode);
                        rs = pst.executeQuery();

                        if (rs.next()) {
                            String drugName = rs.getString("itemname");
                            int availableQty = rs.getInt("qty");

                            txtDrugName.setText(drugName);
                            btnAdd.setEnabled(true); // Enable Add button
                            JOptionPane.showMessageDialog(null, "Drug loaded \nDrug: " + drugName + "\nAvailable Quantity: " + availableQty);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Drug Code! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            txtDrugCode.setText("");
                            txtDrugName.setText("");
                            btnAdd.setEnabled(false); // Disable Add button
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error fetching drug details from database!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add Button Action
        btnAdd.addActionListener(e -> {
            String drugCode = txtDrugCode.getText();
            String drugName = txtDrugName.getText();
            int qty = (int) spinnerQty.getValue();

            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                pst = con.prepareStatement("SELECT sellprice, qty FROM item WHERE itemno = ?");
                pst.setString(1, drugCode);
                rs = pst.executeQuery();

                if (rs.next()) {
                    double price = rs.getDouble("sellprice");
                    int availableQty = rs.getInt("qty");

                    if (qty > availableQty) {
                        JOptionPane.showMessageDialog(this, "Insufficient quantity! \nAvailable: " + availableQty, "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double total = qty * price;
                    tableModel.addRow(new Object[]{txtPrescriptionID.getText(), drugCode, drugName, qty, price, total});

                    double totalCost = Double.parseDouble(txtTotalCost.getText().isEmpty() ? "0" : txtTotalCost.getText());
                    totalCost += total;
                    txtTotalCost.setText(String.valueOf(totalCost));
                    updateSalesButtonState();
                    // Clear input fields
                    clearFields();
                    
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching price from database!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnSalesUpdate.addActionListener(e -> {
            try {
                // Check if the table has rows
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No items added to update sales!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse payment and cost details
                double totalCost = Double.parseDouble(txtTotalCost.getText().isEmpty() ? "0" : txtTotalCost.getText());
                double pay = Double.parseDouble(txtPay.getText().isEmpty() ? "0" : txtPay.getText());
                double balance = pay - totalCost;

                if (balance < 0) {
                    JOptionPane.showMessageDialog(this, "Insufficient payment! Please check the amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
             // Display balance in the text box
                txtBalance.setText(String.format("%.2f", balance));
                
//                pst.setString(4, prescriptionNo);
//                JOptionPane.showMessageDialog(this, "prescriptionNo"+prescriptionNo, "Error", JOptionPane.ERROR_MESSAGE);
//                JOptionPane.showMessageDialog(this, "prescriptionNo1"+prescriptionNo1, "Error", JOptionPane.ERROR_MESSAGE);
                
                String Prescription_No = prescriptionNo1;
                // Insert record into `sales` table
                String salesQuery = "INSERT INTO sales(date, subtotal, pay, balance,prescriptionNo) VALUES (CURRENT_DATE(), ?, ?, ?,?)";
                pst = con.prepareStatement(salesQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setDouble(1, totalCost);
                pst.setDouble(2, pay);
                pst.setDouble(3, balance);
                pst.setString(4, Prescription_No);
                
                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    // Get generated `sales_id`
                    ResultSet generatedKeys = pst.getGeneratedKeys();
                    int salesId = 0;
                    if (generatedKeys.next()) {
                        salesId = generatedKeys.getInt(1);
                    }

                    // Insert items into `salesproduct` table
                    String salesProductQuery = "INSERT INTO salesproduct(sales_id, product_id, sellprice, qty, total) VALUES (?, ?, ?, ?, ?)";
                    pst = con.prepareStatement(salesProductQuery);

                    // Update inventory quantities
                    String updateInventoryQuery = "UPDATE item SET qty = qty - ? WHERE itemno = ?";
                    PreparedStatement updateInventoryStmt = con.prepareStatement(updateInventoryQuery);

                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String productId = tableModel.getValueAt(i, 1).toString();
                        double sellPrice = Double.parseDouble(tableModel.getValueAt(i, 4).toString());
                        int qty = Integer.parseInt(tableModel.getValueAt(i, 3).toString());
                        double total = Double.parseDouble(tableModel.getValueAt(i, 5).toString());

                        // Insert into `salesproduct`
                        pst.setInt(1, salesId);
                        pst.setString(2, productId);
                        pst.setDouble(3, sellPrice);
                        pst.setInt(4, qty);
                        pst.setDouble(5, total);
                        pst.addBatch();

                        // Update inventory
                        updateInventoryStmt.setInt(1, qty);
                        updateInventoryStmt.setString(2, productId);
                        updateInventoryStmt.addBatch();
                    }

                    pst.executeBatch();
                    updateInventoryStmt.executeBatch();

                    // Display success message
                    JOptionPane.showMessageDialog(this, "Sales updated successfully and inventory adjusted!\nYour Balance is:Rs "+balance, "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear table and reset fields
                    tableModel.setRowCount(0);
                    txtTotalCost.setText("");
                    txtPay.setText("");
                    txtBalance.setText("");
                    updateSalesButtonState();
                    
                    // Disable 'Add' button
                    btnAdd.setEnabled(false);
//                    
//                    Inventory.this.setVisible(false);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update sales. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while updating sales! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        txtDrugCode.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loadDrugDetails();
                }
            }
        });
        
        
        // Browse Button Action
        btnBrowse.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   BrowseDrugs browseDrugsFrame = new BrowseDrugs();
            	   browseDrugsFrame.setVisible(true);
               }
           });
        
        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current window
                Inventory.this.setVisible(false);
            }
        });

        Connect(); // Establish database connection
        updateSalesButtonState();
        setLocationRelativeTo(null);
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDrugDetails() {
        String drugCode = txtDrugCode.getText();

        if (drugCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Drug Code!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            pst = con.prepareStatement("SELECT itemname, qty, sellprice FROM item WHERE itemno = ?");
            pst.setString(1, drugCode);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtDrugName.setText(rs.getString("itemname"));
                spinnerQty.setValue(0);
                spinnerQty.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Drug Code not found!", "Error", JOptionPane.ERROR_MESSAGE);
                txtDrugName.setText("");
                spinnerQty.setValue(0);
                spinnerQty.setEnabled(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching drug details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkQuantity(int requestedQty) {
        try {
            String drugCode = txtDrugCode.getText();
            pst = con.prepareStatement("SELECT qty FROM item WHERE itemno = ?");
            pst.setString(1, drugCode);
            rs = pst.executeQuery();

            if (rs.next()) {
                int availableQty = rs.getInt("qty");
                if (requestedQty > availableQty) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock! \nAvailable quantity: " + availableQty, "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Drug Code not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking quantity!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    private void updateSalesButtonState() {
//    	boolean state = (tableModel.getRowCount() > 0);
////    	JOptionPane.showMessageDialog(this, "state :"+ state, "Error", JOptionPane.ERROR_MESSAGE);
//    	System.out.print("state :"+ state);
//        btnSalesUpdate.setEnabled(tableModel.getRowCount() > 0);
    	
    	 // Check if table has rows
        boolean hasRows = tableModel.getRowCount() > 0;

        // Check if Pay field is not empty and contains a valid number
        String payText = txtPay.getText().trim();
        boolean isPayValid = !payText.isEmpty() && payText.matches("\\d+(\\.\\d+)?");

        // Check if Pay amount is greater than or equal to Total Cost
        double totalCost = Double.parseDouble(txtTotalCost.getText().isEmpty() ? "0" : txtTotalCost.getText());
        double pay = isPayValid ? Double.parseDouble(payText) : 0;
        
        // Check if Pay amount is less than Total Cost
//        if (isPayValid && pay < totalCost) {
//            JOptionPane.showMessageDialog(this, "The Pay amount is less than the Total Cost. Please enter a valid amount!", 
//                                          "Insufficient Payment", JOptionPane.WARNING_MESSAGE);
//        }

        // Enable button if there are rows, Pay field is valid, and Pay >= Total Cost
        btnSalesUpdate.setEnabled(hasRows && isPayValid && pay >= totalCost);
    }

}
