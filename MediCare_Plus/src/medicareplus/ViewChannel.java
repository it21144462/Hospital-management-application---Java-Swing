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

public class ViewChannel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
    private DefaultTableModel model;
    private JButton btnExit;
    private JButton btnPrescription;
    
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
					ViewChannel frame = new ViewChannel(404, "Test Doctor");
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
	public ViewChannel(int userId, String userType) {
		
		userId1 = userId;
        userType1 = userType;

        Connect(); // Establish database connection

        setTitle("Medicare Plus+ Channel Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600); // Adjusted window size
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title Label for View Channel
        JLabel lblTitle = new JLabel("All Channels - Channel Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);

        // Adjusting the bounds for centered alignment
        int frameWidth = 900; // Width of the frame
        int titleWidth = 400; // Estimated width for the new title text
        lblTitle.setBounds((frameWidth - titleWidth) / 2, 10, titleWidth, 30);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Align text to center
        contentPane.add(lblTitle);


        // Table setup
        String[] columns = {"Channel No", "Patient Name", "Doctor Name", "Room No", "Date"};
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
        lblTotalCount = new JLabel("Total Channels: 0"); // Default text
        lblTotalCount.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotalCount.setForeground(Color.WHITE);
        lblTotalCount.setBounds(50, 470, 200, 30); // Positioned below the table
        contentPane.add(lblTotalCount);
        
        // Prescription Button
        btnPrescription = new JButton("Prescription");
        btnPrescription.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnPrescription.setBounds((900 - 360) / 2, 520, 120, 30); // Positioned to the left of Exit button
        contentPane.add(btnPrescription);
        
        // Set visibility based on userType1
        btnPrescription.setVisible(userType1.equals("Doctor"));

        // Exit Button
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds((900 - 100) / 2, 520, 100, 30); // Horizontally centered
        contentPane.add(btnExit);
        
        // Prescription Button Action
        btnPrescription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addPrescription();
            }
        });

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewChannel.this.setVisible(false);
            }
        });

        // Load data into the table
        loadChannelData(); // Load channel data into table
        loadChannelDataCounts(); // Load total channel count based of user type
        setLocationRelativeTo(null); // This centers the ViewChannel window
	        
	}
	
	private void addPrescription() {
	    try {
	    	
	    	// Check if the logged-in user is a doctor        
	        if (userType1.equals("Doctor")) { // Ensure proper equality check
	            // Step 1: Ensure a row is selected in the table
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
				    JOptionPane.showMessageDialog(ViewChannel.this, "Please select a channel from the table!", "Error", JOptionPane.WARNING_MESSAGE);
				    return;
				}

				// Step 2: Retrieve the Channel No from the selected row
				// Assuming the first column (index 0) contains the Channel No
				String pchannelNo = model.getValueAt(selectedRow, 0).toString();
				String pdoctorname = model.getValueAt(selectedRow, 2).toString();
				
				/// Check if channel no already exists in prescription table
		        pst = con.prepareStatement("SELECT COUNT(*) FROM prescription WHERE channelid = ?");
		        pst.setString(1, pchannelNo);
		        ResultSet rs = pst.executeQuery();
		        if (rs.next() && rs.getInt(1) > 0) {
		            JOptionPane.showMessageDialog(this, "Prescription for this channel already given!", "Record Availble", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

				// Step 3: Open the Prescription JFrame
				// Pass the selected Channel No, userId1, and userType1 to the Prescription JFrame
				//Prescription prescriptionFrame = new Prescription(channelNo, userId1, userType1);
				Prescription prescriptionFrame = new Prescription(pchannelNo,pdoctorname);
				JOptionPane.showMessageDialog(ViewChannel.this, "Channel No:"+pchannelNo+"\nDoctor :"+pdoctorname+"\n\nPreparing for add prescription", "Prescribe a patient", JOptionPane.INFORMATION_MESSAGE);
				
				prescriptionFrame.setVisible(true);
	        }


	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error addPrescription data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void loadChannelData() {
	    try {
	    	
	        // Step 1: Retrieve the doctor's name using userId1	        
	        if (userType1.equals("Doctor")) { // Ensure proper equality check
	            try {
	                // Query to retrieve channel details with names instead of IDs
	                String query = "SELECT c.channel_no, p.name AS patient_name, d.name AS doctor_name, c.room_no, c.date " +
	                               "FROM channel c " +
	                               "JOIN patient p ON c.patientname = p.patientno " +
	                               "JOIN doctor d ON c.doctorname = d.doctorno " +
	                               "JOIN user u ON d.name = u.name " +
	                               "WHERE u.id = ? " +
	                               "ORDER BY c.channel_no ASC";
	                
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
	                JOptionPane.showMessageDialog(this, "Error retrieving channel details!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }


	        else if(userType1 =="Admin" || userType1 =="Receptionist") {
        		
        		//if user type Admin, displays all
        		String query = "SELECT c.channel_no, p.name AS patient_name, d.name AS doctor_name, c.room_no, c.date " +
	                       "FROM channel c " +
	                       "JOIN patient p ON c.patientname = p.patientno " +
	                       "JOIN doctor d ON c.doctorname = d.doctorno " +
	                       "ORDER BY c.channel_no ASC";
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
	        JOptionPane.showMessageDialog(this, "Error loading channel data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	private void loadChannelDataCounts() {
	    try {
	        int totalChannels = 0;

	        // Prepare the appropriate count query based on userType1
	        String countQuery;
	        if (userType1.equals("Doctor")) {
	            countQuery = "SELECT COUNT(*) AS total_channels " +
	                         "FROM channel c " +
	                         "JOIN doctor d ON c.doctorname = d.doctorno " +
	                         "JOIN user u ON d.name = u.name " +
	                         "WHERE u.id = ?";
	            pst = con.prepareStatement(countQuery);
	            pst.setInt(1, userId1); // Bind the logged-in user's ID
	            
	        } else if (userType1.equals("Admin") || userType1.equals("Receptionist")) {
	            countQuery = "SELECT COUNT(*) AS total_channels FROM channel";
	            pst = con.prepareStatement(countQuery);
	            
	        } else {
	            JOptionPane.showMessageDialog(this, "Invalid user type!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Execute the count query
	        rs = pst.executeQuery();
	        if (rs.next()) {
	            totalChannels = rs.getInt("total_channels");
	        }

	        // Update the total channel count label
	        lblTotalCount.setText("Total Channels: " + totalChannels);

	        // Load channel data into the table
	        loadChannelData();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading channel data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}



}

