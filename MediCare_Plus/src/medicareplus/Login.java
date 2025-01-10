package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;

    Connection con;
    PreparedStatement pst;

    /**
     * Establishes database connection.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void clearFields() {
    	usernameField.setText("");
    	passwordField.setText("");
    	userTypeComboBox.setSelectedIndex(0); // Reset dropdown to the first item
        usernameField.requestFocus();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        // Frame settings
        setTitle("Medicare Plus+ Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        setContentPane(contentPane);

        // Title Label
        JLabel lblTitle = new JLabel("Medicare Plus+ Login");
        lblTitle.setForeground(Color.RED);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(100, 20, 300, 30);
        contentPane.add(lblTitle);

        // User Name Label
        JLabel lblUserName = new JLabel("Username");
        lblUserName.setFont(new Font("Arial", Font.BOLD, 14));
        lblUserName.setForeground(Color.YELLOW);
        lblUserName.setBounds(50, 70, 100, 30);
        contentPane.add(lblUserName);

        // User Name TextField
        usernameField = new JTextField();
        usernameField.setBounds(160, 70, 200, 25);
        contentPane.add(usernameField);

        // Password Label
        JLabel lblPassword = new JLabel("Password"); 
        lblUserName.setFont(new Font("Arial", Font.BOLD, 12));
        lblPassword.setForeground(Color.YELLOW);
        lblPassword.setBounds(50, 120, 100, 30);
        contentPane.add(lblPassword);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(160, 120, 200, 25);
        contentPane.add(passwordField);

        // User Type Label
        JLabel lblUserType = new JLabel("User Type");
        lblUserType.setFont(new Font("Arial", Font.BOLD, 12));
        lblUserType.setForeground(Color.YELLOW);
        lblUserType.setBounds(50, 170, 100, 30);
        contentPane.add(lblUserType);

        // User Type ComboBox
        userTypeComboBox = new JComboBox<>(new String[]{"Pharmacist", "Doctor", "Receptionist","Admin"});
        userTypeComboBox.setBounds(160, 170, 200, 25);
        contentPane.add(userTypeComboBox);
        
//        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
//        btnAdd.setBounds(150, 300, 100, 30);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogin.setBounds(120, 220, 80, 30);
        contentPane.add(btnLogin);

        // Exit Button
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(230, 220, 80, 30);
        contentPane.add(btnExit);

        // Exit button action
        //btnExit.addActionListener(e -> System.exit(0));
        btnExit.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0); // Exit the application
            }
        });

        // Login button action
        btnLogin.addActionListener(e -> login());
        
        setLocationRelativeTo(null); // This centers the Login window
    }

    /**
     * Handles the login functionality.
     */
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = userTypeComboBox.getSelectedItem().toString();
        
        // Console log the values
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);
//        System.out.println("User Type: " + userType);

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
        	connect(); // Establish database connection
        	try {
        	    pst = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ? AND utype = ?");
        	    pst.setString(1, username);
        	    pst.setString(2, password);
        	    pst.setString(3, userType);

        	    ResultSet rs = pst.executeQuery();

        	    if (rs.next()) {
        	        // Assuming the user's full name is stored in a column called 'name'
        	        String fullName = rs.getString("name"); 

        	        // Display a welcome message with the user's name
        	        JOptionPane.showMessageDialog(this, 
        	        	"Login Success" +"\n\nWelcome, " + fullName + " !\nUser Type: " + userType,
        	            "Medicare plus - Login", 
        	            JOptionPane.INFORMATION_MESSAGE);
        	        
        	        System.out.println("System login: Success\n" + fullName + "\nUser Type: " + userType);

        	        // You can open a new window here based on user type
        	        int userid = rs.getInt("id");
        	        this.setVisible(false);
//        	        new Main(userid,username,userType);
        	        
//        	        User userFrame = new User(); // Open the "User" JFrame
//                    userFrame.setVisible(true);
//                    userFrame.setLocationRelativeTo(null); // Center the User frame
        	        Main mainFrame = new Main(userid,username,userType); // Open the "User" JFrame
        	        mainFrame.setVisible(true);
        	        mainFrame.setLocationRelativeTo(null); // Center the User frame
        	        
        	    } else {
        	        JOptionPane.showMessageDialog(this, 
        	            "Invalid credentials or user type !", 
        	            "Error", 
        	            JOptionPane.ERROR_MESSAGE);
        	        //clear the input fields
        	        
        	        System.out.println("System login: Failed"+ "\nInvalid Credentials" );
        	        clearFields();
        	    }
        	} catch (SQLException ex) {
        	    ex.printStackTrace();
        	}

        }
    }
}
