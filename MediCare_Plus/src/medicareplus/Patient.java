package medicareplus;

import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Patient extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPatientName;
    private JTextField txtPhone;
    private JTextArea txtAddress;
    private JTable table;
    private JLabel lblPatientNo;
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
			rs = s.executeQuery("select MAX(patientno) from patient");
			rs.next();
			String maxID =rs.getString("MAX(patientno)");
			
			if(maxID == null) {
				lblPatientNo.setText("PS001");
				System.out.println("Auto-Generated Patient ID: PS001"); // Log to console
			}else {
//				long id = Long.parseLong(rs.getString(maxID.substring(2,maxID.length())));
//				System.out.println("Auto-Generated Patient ID: " + id); // Log to console
				
				long id = Long.parseLong(maxID.substring(2)); // Extract numeric part of ID
	            id++; // Increment the numeric part
	            lblPatientNo.setText("PS" + String.format("%03d", id)); // Format with leading zeros
	            System.out.println("Auto-Generated Patient ID: " + "PS" + String.format("%03d", id)); // Log to console
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to generate Patient No!", "Error", JOptionPane.ERROR_MESSAGE);
		}
    	
    }
    
    /**
     * Clears the input fields after user creation.
     */
    private void clearFields() {
    	// Clear input fields
        txtPatientName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtPatientName.requestFocus();
        
     // Re-enable the Add button
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
                    Patient frame = new Patient();
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
    public Patient() {
    	// Call the Connect method
        Connect();
        
        // Generate the next auto-incremented patient ID
       // AutoIncrementID();
        
        setTitle("Medicare Plus+  Patient Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Title Label for Patient Registration
        JLabel lblTitle1 = new JLabel("Patient Registration");
        lblTitle1.setFont(new Font("Tahoma", Font.BOLD, 18)); 
        lblTitle1.setForeground(Color.YELLOW); 
        lblTitle1.setBounds(30, 0, 330, 30); 
        contentPane.add(lblTitle1);

        // Left Panel for Form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 200)); // Blue color
        formPanel.setBounds(20, 40, 330, 340);
        formPanel.setLayout(null);
        contentPane.add(formPanel);

        JLabel lblTitle = new JLabel("Patient Registration");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setBounds(60, 10, 200, 30);
        formPanel.add(lblTitle);

        JLabel lblNo = new JLabel("Patient No");
        lblNo.setForeground(Color.WHITE);
        lblNo.setBounds(20, 50, 80, 25);
        formPanel.add(lblNo);

        lblPatientNo = new JLabel(); // Dynamic value from AutoIncrementID
        lblPatientNo.setForeground(Color.YELLOW);
        lblPatientNo.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPatientNo.setBounds(140, 50, 100, 25);
        formPanel.add(lblPatientNo);
        
        // Call AutoIncrementID to set Patient No
        AutoIncrementID();

        JLabel lblName = new JLabel("Patient Name");
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(20, 90, 100, 25);
        formPanel.add(lblName);

        txtPatientName = new JTextField();
        txtPatientName.setBounds(140, 90, 160, 25);
        formPanel.add(txtPatientName);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setForeground(Color.WHITE);
        lblPhone.setBounds(20, 130, 100, 25);
        formPanel.add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(140, 130, 160, 25);
        formPanel.add(txtPhone);

        // Restrict phone input to integers only
        txtPhone.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
             // Allow only digits and limit to 10 characters
                if (!Character.isDigit(c) || txtPhone.getText().length() >= 9) {
                    e.consume();
                }
            }
        });

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setForeground(Color.WHITE);
        lblAddress.setBounds(20, 170, 100, 25);
        formPanel.add(lblAddress);

        txtAddress = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtAddress);
        scrollPane.setBounds(140, 170, 160, 80);
        formPanel.add(scrollPane);

        // Buttons
        
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(50, 400, 70, 30); // Adjust X, Y, Width, and Height
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
        String[] columns = {"Patient No", "Patient Name", "Phone", "Address"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(370, 40, 390, 340);
        contentPane.add(tableScroll);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    lblPatientNo.setText(model.getValueAt(selectedRow, 0).toString());
                    txtPatientName.setText(model.getValueAt(selectedRow, 1).toString());
                    txtPhone.setText(model.getValueAt(selectedRow, 2).toString());
                    txtAddress.setText(model.getValueAt(selectedRow, 3).toString());
                    
                 // Disable the Add button
                    btnAdd.setEnabled(false);
                }
            }
        });

        // Add Button Action
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });
        
        // Update Button Action
        btnUpdate.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   updatePatient();
               }
           });
           
        // Delete Button Action
        btnDelete.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   deletePatient();
               }
           });

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current window
                Patient.this.setVisible(false);
            }
        });
        
        patient_table(); // Load initial table data
        setLocationRelativeTo(null); // This centers the Patient window
    }

    private void addPatient() {
        String patientNo = lblPatientNo.getText();
        String name = txtPatientName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        // Validate phone number length
        if (phone.length() != 9) {
            JOptionPane.showMessageDialog(this, "Phone number must Contain 9 digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // insert into the database here
        try {
            pst = con.prepareStatement("INSERT INTO patient(patientno, name, phone, address) VALUES (?, ?, ?, ?)");
            pst.setString(1, patientNo);
            pst.setString(2, name);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Patient Inserted Successfully", "Success",JOptionPane.INFORMATION_MESSAGE);
	         
            //Add the new patient to the table
            //model.addRow(new Object[]{patientNo, name, phone, address});
            
	         patient_table();
	         
            // Update patient number for the next patient
            AutoIncrementID();
            
            // Clear input fields
            clearFields();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save patient data!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void updatePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String patientNo = model.getValueAt(selectedRow, 0).toString();
        String name = txtPatientName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number length
        if (phone.length() != 9) {
            JOptionPane.showMessageDialog(this, "Phone number must contain 9 digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            pst = con.prepareStatement("UPDATE patient SET name = ?, phone = ?, address = ? WHERE patientno = ?");
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, address);
            pst.setString(4, patientNo);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Patient updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                model.setValueAt(name, selectedRow, 1);
                model.setValueAt(phone, selectedRow, 2);
                model.setValueAt(address, selectedRow, 3);
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update patient data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String patientNo = model.getValueAt(selectedRow, 0).toString();

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            pst = con.prepareStatement("DELETE FROM patient WHERE patientno = ?");
            pst.setString(1, patientNo);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                model.removeRow(selectedRow);
                clearFields();
                AutoIncrementID(); // Optional: Recalculate patient numbers
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete patient!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void patient_table() {
    	
    	try {
    		
			pst = con.prepareStatement("select * from patient");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading patient data!", "Error", JOptionPane.ERROR_MESSAGE);
		}
    	
    	
    }

  
}
