package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Items extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtItemName;
    private JTextArea txtDescription;
    private JTextField txtSellPrice;
    private JTextField txtBuyPrice;
    private JTextField txtQty;
    private JTable table;
    private JLabel lblItemNo;
    private DefaultTableModel model;
    
    private JButton btnAdd; // Declare btnAdd as a class-level variable
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnExit;
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public void Connect() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus","root","");
		} catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void AutoIncrementID() {
        try {
            Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(itemno) FROM item");
            rs.next();
            String maxID = rs.getString("MAX(itemno)");

            if (maxID == null) {
                lblItemNo.setText("IT001");
            } else {
                long id = Long.parseLong(maxID.substring(2));
                id++;
                lblItemNo.setText("IT" + String.format("%03d", id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to generate Item No!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Clears the input fields after user creation.
     */
    private void clearFields() {
        txtItemName.setText("");
        txtDescription.setText("");
        txtSellPrice.setText("");
        txtBuyPrice.setText("");
        txtQty.setText("");
        txtItemName.requestFocus();
        btnAdd.setEnabled(true);
        AutoIncrementID();
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Items frame = new Items();
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
	public Items() {
		
		Connect(); // Establish database connection
		
		setTitle("Medicare Plus+ Item Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Item Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setBounds(30, 10, 330, 30);
        contentPane.add(lblTitle);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 200));
        formPanel.setBounds(20, 50, 335, 340);
        formPanel.setLayout(null);
        contentPane.add(formPanel);
        
        JLabel lblTitle1 = new JLabel("Item Management");
        lblTitle1.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle1.setForeground(Color.YELLOW);
        lblTitle1.setBounds(60, 10, 200, 30);
        formPanel.add(lblTitle1);

        JLabel lblItemNoLabel = new JLabel("Item No");
        lblItemNoLabel.setForeground(Color.WHITE);
        lblItemNoLabel.setBounds(20, 50, 100, 25);
        formPanel.add(lblItemNoLabel);

        lblItemNo = new JLabel();
        lblItemNo.setForeground(Color.YELLOW);
        lblItemNo.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblItemNo.setBounds(130, 50, 100, 25);
        formPanel.add(lblItemNo);

        AutoIncrementID();

        JLabel lblName = new JLabel("Item Name");
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(20, 90, 100, 25);
        formPanel.add(lblName);

        txtItemName = new JTextField();
        txtItemName.setBounds(130, 90, 160, 25);
        formPanel.add(txtItemName);

        JLabel lblDescription = new JLabel("Description");
        lblDescription.setForeground(Color.WHITE);
        lblDescription.setBounds(20, 130, 100, 25);
        formPanel.add(lblDescription);

        txtDescription = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtDescription);
        scrollPane.setBounds(130, 130, 160, 60);
        formPanel.add(scrollPane);

        JLabel lblSellPrice = new JLabel("Sell Price" + " (Rs)");
        lblSellPrice.setForeground(Color.WHITE);
        lblSellPrice.setBounds(20, 200, 100, 25);
        formPanel.add(lblSellPrice);

        txtSellPrice = new JTextField();
        txtSellPrice.setBounds(130, 200, 160, 25);
        formPanel.add(txtSellPrice);
        
        // Restrict Sell Price input to numbers and decimals
        txtSellPrice.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Allow only digits and one decimal point
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                } else if (c == '.' && txtSellPrice.getText().contains(".")) {
                    e.consume();
                }
            }
        });

        JLabel lblBuyPrice = new JLabel("Buy Price" + " (Rs)");
        lblBuyPrice.setForeground(Color.WHITE);
        lblBuyPrice.setBounds(20, 240, 100, 25);
        formPanel.add(lblBuyPrice);

        txtBuyPrice = new JTextField();
        txtBuyPrice.setBounds(130, 240, 160, 25);
        formPanel.add(txtBuyPrice);
        
        // Restrict Buy Price input to numbers and decimals
        txtBuyPrice.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Allow only digits and one decimal point
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                } else if (c == '.' && txtBuyPrice.getText().contains(".")) {
                    e.consume();
                }
            }
        });

        JLabel lblQty = new JLabel("Quantity");
        lblQty.setForeground(Color.WHITE);
        lblQty.setBounds(20, 280, 100, 25);
        formPanel.add(lblQty);

        txtQty = new JTextField();
        txtQty.setBounds(130, 280, 160, 25);
        formPanel.add(txtQty);
        
        // Restrict phone input to integers only
        txtQty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
             // Allow only digits and limit to 5 characters
                if (!Character.isDigit(c) || txtQty.getText().length() >= 5) {
                    e.consume();
                }
            }
        });
        
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(50, 400, 70, 30);
        contentPane.add(btnAdd);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnUpdate.setBounds(130, 400, 90, 30); // Align next to Add button
        contentPane.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBounds(230, 400, 80, 30); // Align next to Update button
        contentPane.add(btnDelete);

        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(320, 400, 70, 30); // Align next to Delete button
        contentPane.add(btnExit);

		
		
		// Table
        String[] columns = {"Item No", "Item Name", "Description", "Sell Price", "Buy Price", "Qty"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(370, 50, 600, 340);
        contentPane.add(tableScroll);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    lblItemNo.setText(model.getValueAt(selectedRow, 0).toString());
                    txtItemName.setText(model.getValueAt(selectedRow, 1).toString());
                    txtDescription.setText(model.getValueAt(selectedRow, 2).toString());
                    txtSellPrice.setText(model.getValueAt(selectedRow, 3).toString());
                    txtBuyPrice.setText(model.getValueAt(selectedRow, 4).toString());
                    txtQty.setText(model.getValueAt(selectedRow, 5).toString());
                    btnAdd.setEnabled(false);
                }
            }
        });
		
		// Add Button Action
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addItem();
            }
        });
        
        // Update Button Action
        btnUpdate.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   updateItem();
               }
           });
           
        // Delete Button Action
        btnDelete.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   deleteItem();
               }
           });

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current window
                Items.this.setVisible(false);
            }
        });
        
        loadItemTable();
        setLocationRelativeTo(null); //center the item window
	}
	
	private void addItem() {
		 String itemNo = lblItemNo.getText();
		    String itemName = txtItemName.getText();
		    String description = txtDescription.getText();
		    String sellPrice = txtSellPrice.getText();
		    String buyPrice = txtBuyPrice.getText();
		    String quantity = txtQty.getText();

		    if (itemName.isEmpty() || description.isEmpty() || sellPrice.isEmpty() || buyPrice.isEmpty() || quantity.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    try {
		    	// Check if an item with the same name already exists
		        pst = con.prepareStatement("SELECT COUNT(*) FROM item WHERE itemname = ?");
		        pst.setString(1, itemName);
		        ResultSet rs = pst.executeQuery();
		        if (rs.next() && rs.getInt(1) > 0) {
		            JOptionPane.showMessageDialog(this, "An item with the same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Insert the new item
		        pst = con.prepareStatement("INSERT INTO item (itemno, itemname, description, sellprice, buyprice, qty) VALUES (?, ?, ?, ?, ?, ?)");
		        pst.setString(1, itemNo);
		        pst.setString(2, itemName);
		        pst.setString(3, description);
		        pst.setString(4, sellPrice);
		        pst.setString(5, buyPrice);
		        pst.setString(6, quantity);
		        int rowsAffected = pst.executeUpdate();

		        if (rowsAffected > 0) {
		            JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		            clearFields();
		            loadItemTable();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Failed to add Item!", "Error", JOptionPane.ERROR_MESSAGE);
		    }
   }

	private void updateItem() {
    	int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an Item to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String itemNo = lblItemNo.getText();
        String itemName = txtItemName.getText();
        String description = txtDescription.getText();
        String sellPrice = txtSellPrice.getText();
        String buyPrice = txtBuyPrice.getText();
        String quantity = txtQty.getText();

        if (itemName.isEmpty() || description.isEmpty() || sellPrice.isEmpty() || buyPrice.isEmpty() || quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            pst = con.prepareStatement("UPDATE item SET itemname = ?, description = ?, sellprice = ?, buyprice = ?, qty = ? WHERE itemno = ?");
            pst.setString(1, itemName);
            pst.setString(2, description);
            pst.setString(3, sellPrice);
            pst.setString(4, buyPrice);
            pst.setString(5, quantity);
            pst.setString(6, itemNo);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadItemTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update Item!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void deleteItem() {
    	int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a Item to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String patientNo = model.getValueAt(selectedRow, 0).toString();

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            pst = con.prepareStatement("DELETE FROM item WHERE itemno = ?");
            pst.setString(1, patientNo);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                model.removeRow(selectedRow);
                clearFields();
                AutoIncrementID(); // Optional: Recalculate patient numbers
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete Item!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void loadItemTable() {
    	try {
    		
			pst = con.prepareStatement("select * from item");
			rs = pst.executeQuery();
	    	ResultSetMetaData Rsm = rs.getMetaData();
	    	
	    	int columnCount = Rsm.getColumnCount();
	    	 // Clear the existing data in the table model
	        model.setRowCount(0);

	        // Populate the table model with data from the ResultSet
	        while (rs.next()) {
	            Object[] rowData = new Object[columnCount];
	            for (int i = 0; i < columnCount; i++) {
	                rowData[i] = rs.getObject(i + 1);
	            }
	            model.addRow(rowData);
	        }
	    	
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading Item data!", "Error", JOptionPane.ERROR_MESSAGE);
		}
    	
    	
    }
}
