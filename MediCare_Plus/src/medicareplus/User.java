package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class User extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName, txtUsername, txtPassword;
    private JComboBox<String> userType;
    
    Connection con;
    PreparedStatement pst;
    
    public void Connect() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus","root","");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Clears the input fields after user creation.
     */
    private void clearFields() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        userType.setSelectedIndex(0); // Reset dropdown to the first item
        txtName.requestFocus();
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User frame = new User();
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
    public User() {
        setTitle("Medicare Plus+  ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); // Window size
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); // Absolute positioning
        setContentPane(contentPane);

        // Title Label
        JLabel lblTitle = new JLabel("User Creation");
        lblTitle.setForeground(Color.RED);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setBounds(200, 20, 200, 30);
        contentPane.add(lblTitle);

        // Labels
        JLabel lblName = new JLabel("Name");
        lblName.setForeground(Color.YELLOW);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblName.setBounds(100, 80, 100, 30);
        contentPane.add(lblName);

        JLabel lblUsername = new JLabel("UserName");
        lblUsername.setForeground(Color.YELLOW);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUsername.setBounds(100, 130, 100, 30);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.YELLOW);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPassword.setBounds(100, 180, 100, 30);
        contentPane.add(lblPassword);

        JLabel lblUserType = new JLabel("User Type");
        lblUserType.setForeground(Color.YELLOW);
        lblUserType.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUserType.setBounds(100, 230, 100, 30);
        contentPane.add(lblUserType);

        // Text Fields
        txtName = new JTextField();
        txtName.setBounds(220, 80, 200, 30);
        contentPane.add(txtName);

        txtUsername = new JTextField();
        txtUsername.setBounds(220, 130, 200, 30);
        contentPane.add(txtUsername);

        txtPassword = new JTextField();
        txtPassword.setBounds(220, 180, 200, 30);
        contentPane.add(txtPassword);
//        JPasswordField txtPassword;
//        txtPassword = new JPasswordField();
//        txtPassword.setBounds(220, 180, 200, 30);
//        contentPane.add(txtPassword);



        // ComboBox for User Type
        userType = new JComboBox<>(new String[] { "Pharmacist", "Doctor", "Receptionist","Admin" });
        userType.setBounds(220, 230, 200, 30);
        contentPane.add(userType);

        // Buttons
        JButton btnAdd = new JButton("Create");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		addUser();
        		
        		
        	}
        });
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(150, 300, 100, 30);
        contentPane.add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		dispose(); // Close the window
        		User.this.setVisible(false);
        	}
        });
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCancel.setBounds(300, 300, 100, 30);
        contentPane.add(btnCancel);
        
        setLocationRelativeTo(null); // This centers the User window
    }
    
    
    private void addUser() {
    	String name = txtName.getText();
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		//String password = new String(txtPassword.getPassword());
		String usertype = userType.getSelectedItem().toString();
		
		if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
        	Connect();
    		
    		try {
    			
    			 // Step 1: Check for duplicate username
                pst = con.prepareStatement("SELECT COUNT(*) FROM user WHERE username = ?");
                pst.setString(1, username);
                java.sql.ResultSet rs = pst.executeQuery();
                
                if (rs.next() && rs.getInt(1) > 0) {
                    // If a record with the same username exists
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose another.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                    // Call the clearFields method 
	                clearFields();
                    
                } else {
				pst = con.prepareStatement("insert into user(name,username,password,utype) values(?,?,?,?)");
				
				 	pst.setString(1, name);
		            pst.setString(2, username);
		            pst.setString(3, password);
		            pst.setString(4, usertype);
		            pst.executeUpdate();
		            
		            JOptionPane.showMessageDialog(this, "User added successfully","User Creation",JOptionPane.INFORMATION_MESSAGE);
		            
		            // Call the clearFields method 
	                clearFields();
                }
		            
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Database Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
    }
}
