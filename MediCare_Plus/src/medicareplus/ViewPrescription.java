package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ViewPrescription extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
    private DefaultTableModel model;
    private JButton btnExit;
    private JButton btnInventory;
    
    // New JLabel field for total count
    private JLabel lblTotalCount;
    
	Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    // New fields for userId and userType
    private int userId1;
    private String userType1;
    
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPrescription frame = new ViewPrescription(404, "Test Doctor");
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
	public ViewPrescription(int userId, String userType) {
		
		userId1 = userId;
        userType1 = userType;

        Connect(); // Establish database connection
        
        setTitle("Medicare Plus+ Prescription Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600); // Adjusted window size
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title Label for View Channel
        JLabel lblTitle = new JLabel("All Prescriptions - Prescription Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);

        // Adjusting the bounds for centered alignment
        int frameWidth = 900; // Width of the frame
        int titleWidth = 400; // Estimated width for the new title text
        lblTitle.setBounds((frameWidth - titleWidth) / 2, 10, titleWidth, 30);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Align text to center
        contentPane.add(lblTitle);


        // Table setup
        String[] columns = {"prescription No", "Channel No", "Doctor Name", "Disease Type", "Description"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 800, 400); // Centered table
        contentPane.add(scrollPane);
        
        // Total Count Label
        lblTotalCount = new JLabel("Total Prescription: 0"); // Default text
        lblTotalCount.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotalCount.setForeground(Color.WHITE);
        lblTotalCount.setBounds(50, 470, 200, 30); // Positioned below the table
        contentPane.add(lblTotalCount);
        
        // Inventory Button
        btnInventory = new JButton("Inventory");
        btnInventory.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnInventory.setBounds((900 - 360) / 2, 520, 120, 30); // Positioned to the left of Exit button
        contentPane.add(btnInventory);
        
        // Set visibility based on userType1
        btnInventory.setVisible(userType1.equals("Pharmacist"));

        // Exit Button
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds((900 - 100) / 2, 520, 100, 30); // Horizontally centered
        contentPane.add(btnExit);
        
        // Inventory Button Action
        btnInventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addInventory();
            }
        });

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ViewPrescription.this.setVisible(false);
            }
        });

        // Load data into the table
        loadPrescriptionData(); // Load prescriptions data into table
        adjustColumnWidths();   // Adjust column widths for better display
        loadPrescriptionDataCounts(); // Load total prescriptions count based of user type
        setLocationRelativeTo(null); // This centers the ViewPrescription window
	}
	
	private void addInventory() {
	    try {
	        // Check if the logged-in user is a Pharmacist        
	        if (userType1.equals("Pharmacist")) { // Ensure proper equality check
	            // Step 1: Ensure a row is selected in the table
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(ViewPrescription.this, "Please select a Prescription from the table!", "Error", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            // Step 2: Retrieve the Prescription No from the selected row
	            // Assuming the first column (index 0) contains the Prescription No
	            String prescriptionNo = model.getValueAt(selectedRow, 0).toString();

	            // Step 3: Check if the Prescription No exists in the `sales` table
	            String query = "SELECT COUNT(*) FROM sales WHERE prescriptionNo = ?";
	            try (PreparedStatement pst = con.prepareStatement(query)) {
	                pst.setString(1, prescriptionNo);
	                try (ResultSet rs = pst.executeQuery()) {
	                    if (rs.next() && rs.getInt(1) > 0) {
	                        JOptionPane.showMessageDialog(this, "Drugs for prescription " + prescriptionNo + " are already given!", "Error", JOptionPane.WARNING_MESSAGE);
	                        return;
	                    }
	                }
	            }

	            // Step 4: Pass the selected Prescription No to the Inventory JFrame
	            Inventory inventoryFrame = new Inventory(prescriptionNo);
	            inventoryFrame.setVisible(true);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error in opening Inventory", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	private void loadPrescriptionData() {
	    try {
	    	
	        // Step 1: Retrieve the doctor's name using userId1	        
	        if (userType1.equals("Doctor")) { // Ensure proper equality check
	            try {
	                // Query to retrieve prescription details with names instead of IDs
	                String query = "SELECT p.presno, p.channelid AS channel_id, d.name AS doctor_name, p.diseasetype, p.description " +
		                       "FROM prescription p " +
		                       "JOIN doctor d ON p.doctorname = d.doctorno "+
		                       "JOIN user u ON u.name = d.name "+
		                       "WHERE u.id = ? ";
	                
	                pst = con.prepareStatement(query);
	                pst.setInt(1, userId1); // Bind the logged-in user's ID
	                rs = pst.executeQuery();

	                ResultSetMetaData Rsm = rs.getMetaData();
	                int columnCount = Rsm.getColumnCount();

	                // Clear the existing data in the table model
	                model.setRowCount(0);

	                // Populate the table model with data from the ResultSet
	                while (rs.next()) {
	                    Object[] rowData = new Object[columnCount];
	                    for (int i = 0; i < columnCount; i++) {
	                        rowData[i] = rs.getObject(i + 1); // Map column data dynamically
	                    }
	                    model.addRow(rowData);
	                }

	                // Adjust table width to fit content
	                table.setPreferredScrollableViewportSize(table.getPreferredSize());
	                
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Error retrieving prescription details!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }


	        else if(userType1 =="Admin" || userType1 =="Pharmacist") {
        		
        		//if user type Admin, displays all
        		String query = "SELECT p.presno, p.channelid AS channel_id, d.name AS doctor_name, p.diseasetype, p.description " +
		                       "FROM prescription p " +
		                       "JOIN doctor d ON p.doctorname = d.doctorno ";
	        pst = con.prepareStatement(query);
              
        	}

            rs = pst.executeQuery();

	        ResultSetMetaData Rsm = rs.getMetaData();
	        int columnCount = Rsm.getColumnCount();

	        // Clear existing rows
	        model.setRowCount(0);

	        // Add rows from the database
	        while (rs.next()) {
	            Object[] rowData = new Object[columnCount];
	            for (int i = 0; i < columnCount; i++) {
	                rowData[i] = rs.getObject(i + 1);
	            }
	            model.addRow(rowData);
	        }
	        
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading prescription data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	private void loadPrescriptionDataCounts() {
	    try {
	        int totalPrescriptions = 0;

	        // Prepare the appropriate count query based on userType1
	        String countQuery;
	        if (userType1.equals("Doctor")) {
	            countQuery = "SELECT COUNT(*) AS total_prescriptions " +
		            		 "FROM prescription p " +
	                         "JOIN doctor d ON p.doctorname = d.doctorno "+
	                         "JOIN user u ON d.name = u.name " +
	                         "WHERE u.id = ? ";
	            pst = con.prepareStatement(countQuery);
	            pst.setInt(1, userId1); // Bind the logged-in user's ID
	            
	        } else if (userType1.equals("Admin") || userType1.equals("Pharmacist")) {
	            countQuery = "SELECT COUNT(*) AS total_prescriptions FROM prescription";
	            pst = con.prepareStatement(countQuery);
	            
	        } else {
	            JOptionPane.showMessageDialog(this, "Invalid user type!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Execute the count query
	        rs = pst.executeQuery();
	        if (rs.next()) {
	        	totalPrescriptions = rs.getInt("total_prescriptions");
	        }

	        // Update the total Prescription count label
	        lblTotalCount.setText("Total Prescriptions: " + totalPrescriptions);

	        // Load Prescription data into the table
	        loadPrescriptionData();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading Prescription data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void adjustColumnWidths() {
	    TableColumnModel columnModel = table.getColumnModel();

	    // Adjust column widths
	    columnModel.getColumn(0).setPreferredWidth(100); // 'Prescription No'
	    columnModel.getColumn(1).setPreferredWidth(100); // 'Channel No'
	    columnModel.getColumn(2).setPreferredWidth(150); // 'Doctor Name'
	    columnModel.getColumn(3).setPreferredWidth(150); // 'Disease Type'
	    columnModel.getColumn(4).setPreferredWidth(300); // 'Description'
	}

}
