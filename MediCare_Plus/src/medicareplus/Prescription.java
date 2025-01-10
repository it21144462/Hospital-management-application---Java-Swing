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
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Prescription extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel prescriptionNo;
//    private JTextArea txtDescription;
    private JTextField channelNo;
    private JTextField txtdiseaseType;
    private JTextArea txtdescription;
	
	private JButton btnAdd; // Declare btnAdd as a class-level variable
	
	Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    // New fields for userId and userType
    private String pchannelNo;  // Class-level variable for channel number
    private String pdoctorname; // Class-level variable for doctor name

    
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
            rs = s.executeQuery("SELECT MAX(presno) FROM prescription");
            rs.next();
            String maxID = rs.getString("MAX(presno)");

            if (maxID == null) {
            	prescriptionNo.setText("PC001");
            } else {
                long id = Long.parseLong(maxID.substring(2));
                id++;
                prescriptionNo.setText("PC" + String.format("%03d", id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to generate Prescription No!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Clears the input fields after user creation.
     */
    private void clearFields() {
    	txtdiseaseType.setText("");
        txtdescription.setText("");
        txtdiseaseType.requestFocus();
        AutoIncrementID();
        AutoIncrementID();
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prescription frame = new Prescription("C001","Mr.NUWAN");
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
	public Prescription(String pchannelNo,String pdoctorname) {
		
		this.pchannelNo = pchannelNo;  // Assign parameter value to class-level variable
        this.pdoctorname = pdoctorname; // Assign parameter value to class-level variable
		
		Connect(); // Establish database connection
		
		setTitle("Medicare Plus+ Prescription Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 430, 430);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Prescription Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setBounds(30, 10, 330, 30);
        contentPane.add(lblTitle);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 200));
        formPanel.setBounds(20, 50, 370, 280);
        formPanel.setLayout(null);
        contentPane.add(formPanel);
        
        // Prescription No
        JLabel lblPrescriptionNo = new JLabel("Prescription No");
        lblPrescriptionNo.setForeground(Color.WHITE);
        lblPrescriptionNo.setBounds(20, 50, 100, 25);
        formPanel.add(lblPrescriptionNo);

        prescriptionNo = new JLabel(); // Initialize the class-level variable
        prescriptionNo.setForeground(Color.YELLOW);
        prescriptionNo.setFont(new Font("Tahoma", Font.BOLD, 12));
        prescriptionNo.setBounds(150, 50, 100, 25);
        formPanel.add(prescriptionNo);
        
        AutoIncrementID();

        // Channel No
        JLabel lblChannelNo = new JLabel("Channel No");
        lblChannelNo.setForeground(Color.WHITE);
        lblChannelNo.setBounds(20, 90, 100, 25);
        formPanel.add(lblChannelNo);

        channelNo = new JTextField(pchannelNo);
        channelNo.setFont(new Font("Tahoma", Font.BOLD, 12));
        channelNo.setBounds(150, 90, 200, 25);
        formPanel.add(channelNo);
        
        // Editable False
//        channelNo.setEditable(false);
        channelNo.setEnabled(false);

        // Disease Type
        JLabel lblDiseaseType = new JLabel("Disease Type");
        lblDiseaseType.setForeground(Color.WHITE);
        lblDiseaseType.setBounds(20, 130, 100, 25);
        formPanel.add(lblDiseaseType);

        txtdiseaseType = new JTextField();
        txtdiseaseType.setBounds(150, 130, 200, 25);
        formPanel.add(txtdiseaseType);

        // Description
        JLabel lblDescription = new JLabel("Description");
        lblDescription.setForeground(Color.WHITE);
        lblDescription.setBounds(20, 170, 100, 25);
        formPanel.add(lblDescription);

        txtdescription = new JTextArea();
        txtdescription.setLineWrap(true);
        txtdescription.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtdescription);
        scrollPane.setBounds(150, 170, 200, 70);
        formPanel.add(scrollPane);
        
        // button
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(190, 340, 70, 30);
        contentPane.add(btnAdd);
        
        // Add Button Action
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addPrescription();
            }
        });
        
        setLocationRelativeTo(null);  // This centers the Channel window

	}
	
//	public Prescription(String channelNo2, String doctorname) {
//		// TODO Auto-generated constructor stub
//	}

	private void addPrescription() {
		
		String pprescriptionNo = prescriptionNo.getText();
	    String pchannelNo = channelNo.getText();
	    String diseaseType = txtdiseaseType.getText();
	    String description = txtdescription.getText();
	    String doctorname1 = pdoctorname;
	    String docID = "";
	    

	    if (pprescriptionNo.isEmpty() || pchannelNo.isEmpty() || diseaseType.isEmpty() || description.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    
		 
		    try {
		    	/// Check if an item with the same name already exists
		        pst = con.prepareStatement("SELECT COUNT(*) FROM prescription WHERE channelid = ?");
		        pst.setString(1, pchannelNo);
		        ResultSet rs = pst.executeQuery();
		        if (rs.next() && rs.getInt(1) > 0) {
		            JOptionPane.showMessageDialog(this, "Prescription for this channel already given!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        System.out.println("Doctor name :"+ pdoctorname + ":" + doctorname1);
		        
		        // Retrieve the doctor ID for the given doctor name
		        pst = con.prepareStatement("SELECT doctorno FROM doctor WHERE name = ?");
		        pst.setString(1, pdoctorname);
		        rs = pst.executeQuery();

		        if (rs.next()) {
		            docID = rs.getString("doctorno"); // Assign the doctor ID
		        } else {
		            JOptionPane.showMessageDialog(this, "Doctor not found!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Insert the new item
		        pst = con.prepareStatement("INSERT INTO prescription (presno, channelid, doctorname, diseasetype, description) VALUES (?, ?, ?, ?, ?)");
		        pst.setString(1, pprescriptionNo);
		        pst.setString(2, pchannelNo);
//		        pst.setString(3, pdoctorname);
		        pst.setString(3, docID); // Use the retrieved doctor ID here
		        pst.setString(4, diseaseType);
		        pst.setString(5, description);

		        int rowsAffected = pst.executeUpdate();

		        if (rowsAffected > 0) {
		            JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		            Prescription.this.setVisible(false);
		            clearFields();
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Failed to add Item!", "Error", JOptionPane.ERROR_MESSAGE);
		    }
  }

}