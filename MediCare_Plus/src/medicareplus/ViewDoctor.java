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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ViewDoctor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
    private DefaultTableModel model;
    private JButton btnExit;
    
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
					ViewDoctor frame = new ViewDoctor(404, "Test Doctor");
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
	public ViewDoctor(int userId, String userType) {
		
		userId1 = userId;
        userType1 = userType;
		
		Connect(); // Establish database connection
		
		setTitle("Medicare Plus+ Doctor Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600); // Adjusted window size
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title Label for View Channel
        JLabel lblTitle = new JLabel("All Doctors - Doctor Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);

        // Adjusting the bounds for centered alignment
        int frameWidth = 900; // Width of the frame
        int titleWidth = 400; // Estimated width for the new title text
        lblTitle.setBounds((frameWidth - titleWidth) / 2, 10, titleWidth, 30);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Align text to center
        contentPane.add(lblTitle);


        // Table setup
        String[] columns = {"Doctor No", "Name", "Specialization", "Channel Fee", "Room No"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 800, 400); // Centered table
        contentPane.add(scrollPane);

        // Exit Button
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds((900 - 100) / 2, 500, 100, 30); // Horizontally centered
        contentPane.add(btnExit);

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewDoctor.this.setVisible(false);
            }
        });

        // Load data into the table
        loadDoctorData(); // Load Doctor data into table
        setLocationRelativeTo(null); // This centers the ViewDoctor window
	}
	
	private void loadDoctorData() {
	    try {
	    	
	    	if (userType1 =="Doctor") {
        		
        		//if user type Doctor only allows to display own registration
        		pst = con.prepareStatement("SELECT doctorno, name, specialization, channel_fee, room_no FROM doctor where log_id = ?");
                pst.setInt(1, userId1);
                
        	}else if(userType1 =="Admin" || userType1 =="Pharmacist" || userType1 =="Receptionist" ) {
        		
        		//if user type Admin, displays all
        		// Modify the query to select only the required columns
    	        pst = con.prepareStatement("SELECT doctorno, name, specialization, channel_fee, room_no FROM doctor");
//    	        rs = pst.executeQuery();
              
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

	        // Adjust the table to fit content properly
	        table.setPreferredScrollableViewportSize(table.getPreferredSize());

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading doctor data!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


}
