package medicareplus;

import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Doctor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtDoctorName, txtSpecialization, txtQualification, txtChannelFee, txtPhone;
    private JSpinner spnRoomNo;
    private JTable table;
    private JLabel lblDoctorNo;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete, btnExit;

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

    public void AutoIncrementID() {
        try {
            Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(doctorno) FROM doctor");
            rs.next();
            String maxID = rs.getString(1);
            if (maxID == null) {
                lblDoctorNo.setText("D001");
            } else {
                long id = Long.parseLong(maxID.substring(1)) + 1;
                lblDoctorNo.setText("D" + String.format("%03d", id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to generate Doctor No!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
    /**
     * Clears the input fields after user creation.
     */
    private void clearFields() {
    	lblDoctorNo.setText("");
        txtDoctorName.setText("");
        txtSpecialization.setText("");
        txtQualification.setText("");
        txtChannelFee.setText("");
        txtPhone.setText("");
        spnRoomNo.setValue(1);
        txtDoctorName.requestFocus();

        // Re-enable the Add button
        btnAdd.setEnabled(true);

        // Reset AutoIncrementID
        AutoIncrementID();
        
        
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doctor frame = new Doctor(404, "Test Doctor");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param userType 
	 * @param userId 
	 */
public Doctor(int userId, String userType) {
    	
        Connect();
        userId1=userId;
        userType1=userType;
        
        setTitle("Medicare Plus+  Doctor Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title Label for Doctor Registration
        JLabel lblTitle1 = new JLabel("Doctor Registration");
        lblTitle1.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle1.setForeground(Color.YELLOW);
        lblTitle1.setBounds(30, 0, 330, 30);
        contentPane.add(lblTitle1);

        // Left Panel for Form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 200));
        formPanel.setBounds(20, 40, 330, 340);
        formPanel.setLayout(null);
        contentPane.add(formPanel);
        
        JLabel lblTitle = new JLabel("Doctor Registration");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setBounds(60, 10, 200, 30);
        formPanel.add(lblTitle);

        JLabel lblNo = new JLabel("Doctor No");
        lblNo.setForeground(Color.WHITE);
        lblNo.setBounds(20, 50, 80, 25);
        formPanel.add(lblNo);

        lblDoctorNo = new JLabel();// Dynamic value from AutoIncrementID
        lblDoctorNo.setForeground(Color.YELLOW);
        lblDoctorNo.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDoctorNo.setBounds(140, 50, 100, 25);
        formPanel.add(lblDoctorNo);
        
        // Call AutoIncrementID to set Doctor No
        AutoIncrementID();

        JLabel lblName = new JLabel("Doctor Name");
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(20, 90, 100, 25);
        formPanel.add(lblName);

        txtDoctorName = new JTextField();
        txtDoctorName.setBounds(140, 90, 160, 25);
        formPanel.add(txtDoctorName);

        JLabel lblSpecialization = new JLabel("Specialization");
        lblSpecialization.setForeground(Color.WHITE);
        lblSpecialization.setBounds(20, 130, 100, 25);
        formPanel.add(lblSpecialization);

        txtSpecialization = new JTextField();
        txtSpecialization.setBounds(140, 130, 160, 25);
        formPanel.add(txtSpecialization);

        JLabel lblQualification = new JLabel("Qualification");
        lblQualification.setForeground(Color.WHITE);
        lblQualification.setBounds(20, 170, 100, 25);
        formPanel.add(lblQualification);

        txtQualification = new JTextField();
        txtQualification.setBounds(140, 170, 160, 25);
        formPanel.add(txtQualification);

        JLabel lblChannelFee = new JLabel("Channel Fee (Rs)");
        lblChannelFee.setForeground(Color.WHITE);
        lblChannelFee.setBounds(20, 210, 100, 25);
        formPanel.add(lblChannelFee);

        txtChannelFee = new JTextField();
        txtChannelFee.setBounds(140, 210, 160, 25);
        formPanel.add(txtChannelFee);
        
        // Restrict Channel Fee input to Integers, Decimals only
        txtChannelFee.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
             // Allow only digits and limit to 10 characters
                if (!Character.isDigit(c) || txtChannelFee.getText().length() >= 9) {
                    e.consume();
                }
            }
        });

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setForeground(Color.WHITE);
        lblPhone.setBounds(20, 250, 100, 25);
        formPanel.add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(140, 250, 160, 25);
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

        JLabel lblRoomNo = new JLabel("Room No");
        lblRoomNo.setForeground(Color.WHITE);
        lblRoomNo.setBounds(20, 290, 100, 25);
        formPanel.add(lblRoomNo);

        spnRoomNo = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
        spnRoomNo.setBounds(140, 290, 80, 25);
        formPanel.add(spnRoomNo);


        // Buttons
        
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(50, 400, 80, 30);
        contentPane.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnUpdate.setBounds(140, 400, 90, 30); // Align next to Add button
        contentPane.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBounds(240, 400, 80, 30); // Align next to Update button
        contentPane.add(btnDelete);

        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(330, 400, 80, 30); // Align next to Delete button
        contentPane.add(btnExit);

        // Table
        String[] columns = {"Doctor No", "Name", "Specialization", "Qualification", "Channel Fee", "Phone", "Room No"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(370, 40, 600, 340);
        contentPane.add(scrollPane);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Fetch data from the selected row and populate the fields
                    lblDoctorNo.setText(model.getValueAt(selectedRow, 0).toString());
                    txtDoctorName.setText(model.getValueAt(selectedRow, 1).toString());
                    txtSpecialization.setText(model.getValueAt(selectedRow, 2).toString());
                    txtQualification.setText(model.getValueAt(selectedRow, 3).toString());
                    txtChannelFee.setText(model.getValueAt(selectedRow, 4).toString());
                    txtPhone.setText(model.getValueAt(selectedRow, 5).toString());
                    spnRoomNo.setValue(Integer.parseInt(model.getValueAt(selectedRow, 6).toString()));

                    // Disable the Add button to avoid duplicate addition
                    btnAdd.setEnabled(false);
                }
            }
        });

        
        // Add Button Action
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addDoctor();
            }
        });
        
        // Update Button Action
        btnUpdate.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   updateDoctor();
               }
           });
           
        // Delete Button Action
        btnDelete.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   deleteDoctor();
               }
           });

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current window
            	Doctor.this.setVisible(false);
            }
        });

        doctor_table(); // Load doctor data into table
        setLocationRelativeTo(null); // This centers the Doctor window
    }

	private void addDoctor() {
	    String doctorNo = lblDoctorNo.getText();
	    String name = txtDoctorName.getText();
	    String specialization = txtSpecialization.getText();
	    String qualification = txtQualification.getText();
	    String channelFee = txtChannelFee.getText();
	    String phone = txtPhone.getText();
	    int roomNo = (int) spnRoomNo.getValue();
	    int loginid = userId1;
	
	    try {
	        // Validate login ID availability
	        pst = con.prepareStatement("SELECT * FROM user WHERE id = ?");
	        pst.setInt(1, loginid);
	        rs = pst.executeQuery();
	        if (!rs.next()) {
	            JOptionPane.showMessageDialog(this, "Login ID " + loginid + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Validate input fields
	        if (name.isEmpty() || specialization.isEmpty() || qualification.isEmpty() || channelFee.isEmpty() || phone.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Validate phone number
	        if (phone.length() != 9) {
	            JOptionPane.showMessageDialog(this, "Phone number must contain 9 digits", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Check if the room is already occupied
	        try {
	            pst = con.prepareStatement("SELECT * FROM doctor WHERE room_no = ?");
	            pst.setInt(1, roomNo);
	            rs = pst.executeQuery();
	            if (rs.next()) {
	                JOptionPane.showMessageDialog(this, "Room " + roomNo + " is already occupied!", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	
	            // Insert the doctor into the database
	            try {
	                pst = con.prepareStatement("INSERT INTO doctor(doctorno, name, specialization, qualification, channel_fee, phone, room_no, log_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	                pst.setString(1, doctorNo);
	                pst.setString(2, name);
	                pst.setString(3, specialization);
	                pst.setString(4, qualification);
	                pst.setString(5, channelFee);
	                pst.setString(6, phone);
	                pst.setInt(7, roomNo);
	                pst.setInt(8, loginid);
	                pst.executeUpdate();
	
	                JOptionPane.showMessageDialog(this, "Doctor Inserted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
	                doctor_table();
	                AutoIncrementID(); // Update doctor number
	                clearFields();
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Failed to save doctor data!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error checking room availability!", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error validating login ID!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void updateDoctor() {
		
	    int selectedRow = table.getSelectedRow();
	    if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(this, "Please select a doctor to update!", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	
	    String doctorNo = model.getValueAt(selectedRow, 0).toString();
	    String name = txtDoctorName.getText();
	    String specialization = txtSpecialization.getText();
	    String qualification = txtQualification.getText();
	    String channelFee = txtChannelFee.getText();
	    String phone = txtPhone.getText();
	    int roomNo = (int) spnRoomNo.getValue();
	    int loginid = userId1;
	
	    try {
	        // Validate login ID availability
	        pst = con.prepareStatement("SELECT * FROM user WHERE id = ?");
	        pst.setInt(1, loginid);
	        rs = pst.executeQuery();
	        if (!rs.next()) {
	            JOptionPane.showMessageDialog(this, "Login ID " + loginid + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Validate input fields
	        if (name.isEmpty() || specialization.isEmpty() || qualification.isEmpty() || channelFee.isEmpty() || phone.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Validate phone number length
	        if (phone.length() != 9) {
	            JOptionPane.showMessageDialog(this, "Phone number must contain 9 digits", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Check if the room is already occupied by another doctor
	        pst = con.prepareStatement("SELECT * FROM doctor WHERE room_no = ? AND doctorno != ?");
	        pst.setInt(1, roomNo);
	        pst.setString(2, doctorNo);
	        rs = pst.executeQuery();
	        if (rs.next()) {
	            JOptionPane.showMessageDialog(this, "Room " + roomNo + " is already occupied!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	
	        // Update doctor information
	        pst = con.prepareStatement("UPDATE doctor SET name = ?, specialization = ?, qualification = ?, channel_fee = ?, phone = ?, room_no = ?, log_id = ? WHERE doctorno = ?");
	        pst.setString(1, name);
	        pst.setString(2, specialization);
	        pst.setString(3, qualification);
	        pst.setString(4, channelFee);
	        pst.setString(5, phone);
	        pst.setInt(6, roomNo);
	        pst.setInt(7, loginid);
	        pst.setString(8, doctorNo);
	
	        if (pst.executeUpdate() > 0) {
	            JOptionPane.showMessageDialog(this, "Doctor updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            doctor_table();
	            clearFields();
	        } else {
	            JOptionPane.showMessageDialog(this, "No changes were made!", "Info", JOptionPane.INFORMATION_MESSAGE);
	        }
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "An error occurred while updating the doctor!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

    private void deleteDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String doctorNo = model.getValueAt(selectedRow, 0).toString();

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            pst = con.prepareStatement("DELETE FROM doctor WHERE doctorno = ?");
            pst.setString(1, doctorNo);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Doctor Registration deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                doctor_table();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete doctor data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doctor_table() {
        try {
        	
        	if (userType1 =="Doctor") {
        		
        		//if user type Doctor only allows to display own registration
        		pst = con.prepareStatement("SELECT * FROM doctor where log_id = ?");
                pst.setInt(1, userId1);
                
        	}else if(userType1 =="Admin") {
        		
        		//if user type Admin, displays all
                pst = con.prepareStatement("SELECT * FROM doctor");
//                rs = pst.executeQuery();
              
        	}

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
            
            // Set custom column widths for more horizontal space
            TableColumnModel columnModel = table.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(120); // "Doctor No"
            columnModel.getColumn(1).setPreferredWidth(180); // "Name"
            columnModel.getColumn(2).setPreferredWidth(180); // "Specialization"
            columnModel.getColumn(3).setPreferredWidth(180); // "Qualification"
            columnModel.getColumn(4).setPreferredWidth(130); // "Channel Fee"
            columnModel.getColumn(5).setPreferredWidth(130); // "Phone"
            columnModel.getColumn(6).setPreferredWidth(100);  // "Room No"

            // Set the table width to fit the content properly
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading doctor data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}


