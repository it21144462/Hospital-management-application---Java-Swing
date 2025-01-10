package medicareplus;

import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;

public class Channel extends JFrame {

   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JTextField txtPatientName, txtDoctorName, txtDate;
   private JTable table;
   private JLabel lblChannelNo;
   private DefaultTableModel model;
   private JButton btnAdd, btnDelete, btnExit;

   Connection con;
   PreparedStatement pst;
   ResultSet rs;
   
   private JComboBox<String> cmbPatientName;
   private JComboBox<String> cmbDoctorName;
   
   JComboBox<String> cmbDay = new JComboBox<>();
   JComboBox<String> cmbMonth = new JComboBox<>();
   JComboBox<String> cmbYear = new JComboBox<>();

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
           rs = s.executeQuery("SELECT MAX(channel_no) FROM channel");
           rs.next();
           String maxID = rs.getString(1);
           if (maxID == null) {
               lblChannelNo.setText("C001");
           } else {
               long id = Long.parseLong(maxID.substring(1)) + 1;
               lblChannelNo.setText("C" + String.format("%03d", id));
           }
       } catch (Exception e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Failed to generate Channel No!", "Error", JOptionPane.ERROR_MESSAGE);
       }
   }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Channel frame = new Channel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Clears the input fields after channel creation.
	 */
	private void clearFields() {
	    // Reset input fields
	    cmbPatientName.setSelectedIndex(-1);  // Clear Patient Name ComboBox
	    cmbDoctorName.setSelectedIndex(-1);   // Clear Doctor Name ComboBox

	    // Reset the date fields to today's date
	    cmbDay.setSelectedItem(String.format("%02d", LocalDate.now().getDayOfMonth()));  // Set to current day
	    cmbMonth.setSelectedIndex(LocalDate.now().getMonthValue() - 1);  // Set to current month
	    cmbYear.setSelectedItem(String.valueOf(LocalDate.now().getYear()));  // Set to current year

	    // Reset the focus to the first input field
	    cmbPatientName.requestFocus();

	    // Re-enable the Add button
	    btnAdd.setEnabled(true);

	    // Reset AutoIncrementID (if needed)
	    AutoIncrementID();
	}

	/**
	 * Create the frame.
	 */
	public Channel() {
		Connect(); // Establish database connection
       
       setTitle("Medicare Plus+ Channel Management");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setBounds(100, 100, 1000, 500);
       contentPane = new JPanel();
       contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       setContentPane(contentPane);
       contentPane.setLayout(null);

       // Title Label
       JLabel lblTitle1 = new JLabel("Channel Management");
       lblTitle1.setFont(new Font("Tahoma", Font.BOLD, 18));
       lblTitle1.setForeground(Color.YELLOW);
       lblTitle1.setBounds(30, 10, 330, 30);
       contentPane.add(lblTitle1);

       // Left Panel for Form
       JPanel formPanel = new JPanel();
       formPanel.setBackground(new Color(0, 0, 200));
       formPanel.setBounds(20, 50, 335, 340);
       formPanel.setLayout(null);
       contentPane.add(formPanel);
       
       JLabel lblTitle = new JLabel("Create Channels");
       lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
       lblTitle.setForeground(Color.YELLOW);
       lblTitle.setBounds(60, 10, 200, 30);
       formPanel.add(lblTitle);

       JLabel lblChannelLabel = new JLabel("Channel No");
       lblChannelLabel.setForeground(Color.WHITE);
       lblChannelLabel.setBounds(20, 50, 100, 25);
       formPanel.add(lblChannelLabel);

       lblChannelNo = new JLabel();
       lblChannelNo.setForeground(Color.YELLOW);
       lblChannelNo.setFont(new Font("Tahoma", Font.BOLD, 12));
       lblChannelNo.setBounds(130, 50, 160, 25);
       formPanel.add(lblChannelNo);

       JLabel lblPatientName = new JLabel("Patient Name");
       lblPatientName.setForeground(Color.WHITE);
       lblPatientName.setBounds(20, 90, 100, 25);
       formPanel.add(lblPatientName);

       cmbPatientName = new JComboBox<>(); // Initialize cmbPatientName
       cmbPatientName.setBounds(130, 90, 160, 25);
       formPanel.add(cmbPatientName);

       JLabel lblDoctorName = new JLabel("Doctor Name");
       lblDoctorName.setForeground(Color.WHITE);
       lblDoctorName.setBounds(20, 130, 100, 25);
       formPanel.add(lblDoctorName);

       cmbDoctorName = new JComboBox<>(); // Initialize cmbDoctorName
       cmbDoctorName.setBounds(130, 130, 160, 25);
       formPanel.add(cmbDoctorName);

       JLabel lblDate = new JLabel("Date");
       lblDate.setForeground(Color.WHITE);
       lblDate.setBounds(20, 170, 100, 25);
       formPanel.add(lblDate);

       cmbDay = new JComboBox<>();
       cmbDay.setBounds(130, 170, 50, 25);
       for (int i = 1; i <= 31; i++) {
           cmbDay.addItem(String.format("%02d", i)); // Add day values (01, 02, ..., 31)
       }
       formPanel.add(cmbDay);

       cmbMonth = new JComboBox<>();
       cmbMonth.setBounds(185, 170, 60, 25);
       String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
       for (String month : months) {
           cmbMonth.addItem(month); // Add month values (Jan, Feb, ..., Dec)
       }
       formPanel.add(cmbMonth);

       cmbYear = new JComboBox<>();
       cmbYear.setBounds(250, 170, 80, 25);
       int currentYear = java.time.Year.now().getValue();
       for (int i = currentYear; i <= currentYear + 10; i++) {
           cmbYear.addItem(String.valueOf(i)); // Add year values (current year + 10 years ahead)
       }
       formPanel.add(cmbYear);

       btnAdd = new JButton("Add");
       btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
       btnAdd.setBounds(50, 400, 80, 30);
       contentPane.add(btnAdd);

       btnDelete = new JButton("Delete");
       btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
       btnDelete.setBounds(140, 400, 90, 30);
       contentPane.add(btnDelete);

       btnExit = new JButton("Exit");
       btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
       btnExit.setBounds(240, 400, 80, 30);
       contentPane.add(btnExit);

       String[] columns = {"Channel No", "Patient Name","Doctor Name","Room No", "Date"};
       model = new DefaultTableModel();
       model.setColumnIdentifiers(columns);

       table = new JTable(model);
       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setBounds(370, 50, 600, 340);
       contentPane.add(scrollPane);
       
    // Add Button Action
       btnAdd.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
           	addChannel();
           }
       });
          
       // Delete Button Action
       btnDelete.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  deleteChannel();
              }
          });

       // Exit Button Action
       btnExit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Hide the current window
           	Channel.this.setVisible(false);
           }
       });
       
       table.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               int selectedRow = table.getSelectedRow();
               if (selectedRow != -1) {
                   lblChannelNo.setText(model.getValueAt(selectedRow, 0).toString());
                   txtPatientName.setText(model.getValueAt(selectedRow, 1).toString());
                   txtDoctorName.setText(model.getValueAt(selectedRow, 2).toString());
                   txtDate.setText(model.getValueAt(selectedRow, 3).toString());
               }
           }
       });

       AutoIncrementID(); // Generate channel ID
       loadComboData();    // Load patient and doctor data into combo boxes
       channel_table();
       
       setLocationRelativeTo(null); // This centers the Channel window
	}
	
	
   private void addChannel() {
        try {
            // Retrieve selected values from the ComboBoxes
            String day = (String) cmbDay.getSelectedItem();
            String month = String.format("%02d", cmbMonth.getSelectedIndex() + 1); // Convert month to numeric value
            String year = (String) cmbYear.getSelectedItem();
            String date = year + "-" + month + "-" + day; // Format as YYYY-MM-DD

            // Debug date selection
            System.out.println("Selected Date: " + date + "\n");

            // Get the selected patient from the ComboBox
            String selectedPatient = cmbPatientName.getSelectedItem() != null ? cmbPatientName.getSelectedItem().toString() : "";

            // Get the selected doctor from the ComboBox
            String selectedDoctor = cmbDoctorName.getSelectedItem() != null ? cmbDoctorName.getSelectedItem().toString() : "";

            // Validate inputs
            if (selectedPatient.isEmpty() || selectedDoctor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select both a patient and a doctor.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Extract doctor and patient names (assuming format "ID - Name")
            String doctorName = selectedDoctor.split(" - ")[0];
            String patientName = selectedPatient.split(" - ")[0];

            // Fetch the room number for the selected doctor
            String roomNo = getDoctorRoomNo(selectedDoctor.split(" - ")[0]); // Use doctor ID
            
         // Print the data to the console before inserting
            System.out.println("Channel No: " + lblChannelNo.getText());
            System.out.println("Patient Name: " + patientName); // Assuming cmbPatientName holds patient names
            System.out.println("Doctor Name: " + doctorName);
            System.out.println("Room No: " + roomNo);
            System.out.println("Date: " + date + "\n");

         // Check if the record already exists in the database
            try {
                // Prepare SQL to check for duplicate channels
                pst = con.prepareStatement("SELECT * FROM channel WHERE doctorname = ? AND patientname = ? AND date = ?");
                pst.setString(1, doctorName);  // Doctor name without ID
                pst.setString(2, patientName); // Patient name
                pst.setString(3, date);  // Date in the format YYYY-MM-DD
                rs = pst.executeQuery();

                if (rs.next()) {
                    // If a record is found, display an error message
                    JOptionPane.showMessageDialog(this, "The same patient already has an appointment with this doctor on this date.", "Duplicate Channel Record", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insert the new channel if no duplicate is found
                    pst = con.prepareStatement("INSERT INTO channel(channel_no, patientname, doctorname, room_no, date) VALUES (?, ?, ?, ?, ?)");
                    pst.setString(1, lblChannelNo.getText());  // Channel number
                    pst.setString(2, patientName); // Patient name from combo box
                    pst.setString(3, doctorName);  // Doctor name
                    pst.setString(4, roomNo);  // Room number
                    pst.setString(5, date);  // Date

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Channel Added Successfully!");
                    channel_table();
                    sendMessageToDoctor(doctorName);

                    AutoIncrementID();
                    clearFields();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Adding Channel!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while adding the channel.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   
   private void sendMessageToDoctor(String doctorID) {
	    try {
	        // Retrieve doctorID and debug
	        String doctorId = doctorID;
	        System.out.println("Doctor ID: " + doctorId);

	        // Retrieve phone number of the doctor
	        pst = con.prepareStatement("SELECT phone FROM doctor WHERE doctorno = ?");
	        pst.setString(1, doctorId);
	        rs = pst.executeQuery();

	        // Process the result
	        if (rs.next()) {
	            String phone = rs.getString("phone");
	            System.out.println("Doctor's Phone Number: " + phone);
	            // Optionally, send a message using the phone number here
	        } else {
	            System.out.println("No phone number found for Doctor ID: " + doctorId);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "An error occurred while retrieving the phone number.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	

   // Helper method to fetch the doctor's room number from the database
   private String getDoctorRoomNo(String doctorID) {
       String roomNo = "";
       try {
           pst = con.prepareStatement("SELECT room_no FROM doctor WHERE doctorno = ?");
           pst.setString(1, doctorID);
           rs = pst.executeQuery();

           if (rs.next()) {
               roomNo = rs.getString("room_no");
           }
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error fetching doctor room number!", "Error", JOptionPane.ERROR_MESSAGE);
       }
       return roomNo;
   }


   private void deleteChannel() {
       int selectedRow = table.getSelectedRow();
       if (selectedRow == -1) {
           JOptionPane.showMessageDialog(this, "Please select a channel to Cancel!", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }

       String channelNo = model.getValueAt(selectedRow, 0).toString();

       int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to Cancel this channel?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
       if (confirmation != JOptionPane.YES_OPTION) {
           return;
       }

       try {
           // Check if the channel exists
           pst = con.prepareStatement("SELECT * FROM channel WHERE channel_no = ?");
           pst.setString(1, channelNo);
           rs = pst.executeQuery();
           if (!rs.next()) {
               JOptionPane.showMessageDialog(this, "Channel not found!", "Error", JOptionPane.ERROR_MESSAGE);
               return;
           }

           // Delete the selected channel
           pst = con.prepareStatement("DELETE FROM channel WHERE channel_no = ?");
           pst.setString(1, channelNo);
           int rowsAffected = pst.executeUpdate();

           if (rowsAffected > 0) {
               JOptionPane.showMessageDialog(this, "Channel Canceled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
               channel_table();
               clearFields();
           }
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Failed to Cancel channel!", "Error", JOptionPane.ERROR_MESSAGE);
       }
   }
   

   public void loadComboData() {

       try {
           // Load Patient Data
           pst = con.prepareStatement("SELECT patientno, name FROM patient");
           rs = pst.executeQuery();
           cmbPatientName.removeAllItems();
           while (rs.next()) {
               String patientInfo = rs.getString("patientno") + " - " + rs.getString("name");
               cmbPatientName.addItem(patientInfo);
           }

           // Load Doctor Data
           pst = con.prepareStatement("SELECT doctorno, name FROM doctor");
           rs = pst.executeQuery();
           cmbDoctorName.removeAllItems();
           while (rs.next()) {
               String doctorInfo = rs.getString("doctorno") + " - " + rs.getString("name");
               cmbDoctorName.addItem(doctorInfo);
           }
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Failed to load data for dropdowns!", "Error", JOptionPane.ERROR_MESSAGE);
       }
   }
   

//   private void channel_table() {
//       try {
//
//           pst = con.prepareStatement("SELECT * FROM channel");
//           rs = pst.executeQuery();
//           ResultSetMetaData Rsm = rs.getMetaData();
//           int columnCount = Rsm.getColumnCount();
//
//           // Clear the existing data in the table model
//           model.setRowCount(0);
//
//           // Populate the table model with data from the ResultSet
//           while (rs.next()) {
//               Object[] rowData = new Object[columnCount];
//               for (int i = 0; i < columnCount; i++) {
//                   rowData[i] = rs.getObject(i + 1);
//               }
//               model.addRow(rowData);
//           }
//
//           // Set the table width to fit the content properly
//           table.setPreferredScrollableViewportSize(table.getPreferredSize());
//
//       } catch (SQLException e) {
//           e.printStackTrace();
//           JOptionPane.showMessageDialog(this, "Error loading channel data!", "Error", JOptionPane.ERROR_MESSAGE);
//       }
//   }
   private void channel_table() {
	    try {
	        // SQL query to join channel table with patient and doctor tables and order by channel_no
	        String query = "SELECT c.channel_no, p.name AS patient_name, d.name AS doctor_name, c.room_no, c.date " +
	                       "FROM channel c " +
	                       "JOIN patient p ON c.patientname = p.patientno " +
	                       "JOIN doctor d ON c.doctorname = d.doctorno " +
	                       "ORDER BY c.channel_no ASC";
	        pst = con.prepareStatement(query);
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

	        // Set the table width to fit the content properly
	        table.setPreferredScrollableViewportSize(table.getPreferredSize());

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading channel data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}




}
