package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Declare instance variables for user details
    private int userId;
    private String username;
    private String userType;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main(1, "JohnDoe", "Admin"); // For testing purposes
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
    // Constructor to initialize with user data
    public Main(int userId, String username, String userType) {
        this.userId = userId;
        this.username = username;
        this.userType = userType;

        setTitle("Medicare Plus+");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500); // Adjust size for better visibility
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Sidebar Panel (Left Side)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 30, 30)); // Dark gray color
        sidebar.setBounds(0, 0, 200, 500);
        sidebar.setLayout(null);
        contentPane.add(sidebar);

        // Sidebar Buttons
        String[] buttonLabels = {"Patient", "Doctor", "Create Channel", "View Channel", 
                                 "View Prescription", "Create Item", "Create User", 
                                 "View Doctor","Monthly Report", "Logout"};

////////////////////////////////////////////////////////////////////  login type 2       
        Map<String, String[]> buttonPermissions = new HashMap<>();
        buttonPermissions.put("Pharmacist", new String[]{"View Prescription", "Create Item", "View Doctor", "Logout"});
        buttonPermissions.put("Doctor", new String[]{"Doctor", "View Channel","View Prescription",  "View Doctor", "Logout"});
        buttonPermissions.put("Receptionist", new String[]{"Patient", "Create Channel", "View Channel", "Create Item", "Create User", "View Doctor", "Logout"});
        buttonPermissions.put("Admin", new String[]{"Patient", "Doctor", "Create Channel", "View Channel", "View Prescription", "Create Item", "Create User", "View Doctor","Monthly Report", "Logout"});

        // Get allowed buttons for the current user type
        String[] allowedButtons = buttonPermissions.getOrDefault(userType, new String[]{"Logout"});

//        // Add buttons to the sidebar
//        int yPosition = 30; // Initial Y position for buttons
//        for (String buttonText : buttonLabels) {
//            JButton button = new JButton(buttonText);
//            button.setBounds(20, yPosition, 160, 30);
//
//            // Enable/Disable buttons based on user type
//            button.setEnabled(Arrays.asList(allowedButtons).contains(buttonText));
//
//            // Add ActionListeners for allowed buttons
//            if (button.isEnabled()) {
//                if (buttonText.equals("Patient")) {
//                    button.addActionListener(e -> {
//                        Patient patientFrame = new Patient();
//                        patientFrame.setVisible(true);
//                        patientFrame.setLocationRelativeTo(null); // Center the Patient frame
//                    });
//                } else if (buttonText.equals("Doctor")) {
//                    button.addActionListener(e -> {
//                    	Doctor doctorFrame = new Doctor(userId,userType);
//                    	doctorFrame.setVisible(true);
//                    	doctorFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("Create Channel")) {
//                    button.addActionListener(e -> {
//                        Channel channelFrame = new Channel();
//                        channelFrame.setVisible(true);
//                        channelFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("View Prescription")) {
//                    button.addActionListener(e -> {
//                        ViewPrescription viewprescriptionFrame = new ViewPrescription(userId,userType);
//                        viewprescriptionFrame.setVisible(true);
//                        viewprescriptionFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("View Channel")) {
//                    button.addActionListener(e -> {
//                        ViewChannel viewchannelFrame = new ViewChannel(userId,userType);
//                        viewchannelFrame.setVisible(true);
//                        viewchannelFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("Create Item")) { //Inventory
//                    button.addActionListener(e -> {
//                    	Items itemsFrame = new Items();
//                    	itemsFrame.setVisible(true);
//                    	itemsFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("Create User")) {
//                    button.addActionListener(e -> {
//                        User userFrame = new User();
//                        userFrame.setVisible(true);
//                        userFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                }else if (buttonText.equals("View Doctor")) {
//                    button.addActionListener(e -> {
//                        ViewDoctor viewdoctorFrame = new ViewDoctor(userId,userType);
//                        viewdoctorFrame.setVisible(true);
//                        viewdoctorFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("Monthly Report")) {
//                    button.addActionListener(e -> {
//                    	MonthlyReport monthlyreportFrame = new MonthlyReport();
//                    	monthlyreportFrame.setVisible(true);
//                    	monthlyreportFrame.setLocationRelativeTo(null); // Center the User frame
//                    });
//                } else if (buttonText.equals("Logout")) {
//                    button.addActionListener(e -> {
//                        dispose(); // Close the current frame
//                        Login loginFrame = new Login();
//                        loginFrame.setVisible(true);
//                        loginFrame.setLocationRelativeTo(null); // Center the Login frame
//                    });
//                }
//            }
//
//            sidebar.add(button);
//            yPosition += 40; // Increment Y position for the next button
//        }
        
//      ////////////////////////////////////////////// login type 2

        
        int yPosition = 30; // Initial Y position for buttons
        for (String buttonText : buttonLabels) {
            JButton button = new JButton(buttonText);
            button.setBounds(20, yPosition, 160, 30);

            // Set visibility based on user type
            boolean isVisible = false;
            switch (userType) {
                case "Pharmacist":
                    isVisible = buttonText.equals("View Prescription") || 
                                buttonText.equals("Create Item") || 
                                buttonText.equals("View Doctor") || 
                                buttonText.equals("Logout");
                    break;
                case "Doctor":
                    isVisible = buttonText.equals("View Channel") || 
                                buttonText.equals("View Prescription") || 
                                buttonText.equals("View Doctor") || 
                                buttonText.equals("Logout");
                    break;
                case "Receptionist":
                    isVisible = buttonText.equals("Patient") || 
                                buttonText.equals("Create Channel") || 
                                buttonText.equals("View Channel") || 
                                buttonText.equals("Create Item") || 
                                buttonText.equals("Create User") || 
                                buttonText.equals("View Doctor") || 
                                buttonText.equals("Logout");
                    break;
                case "Admin":
                    isVisible = true; // Admin can see all buttons
                    break;
                default:
                    isVisible = buttonText.equals("Logout"); // Unknown roles can only see Logout
                    break;
            }

            // Set visibility and add ActionListeners
            if (isVisible) {
                button.setVisible(true);
                sidebar.add(button); // Add the button only if it's visible
                button.addActionListener(e -> {
                	switch (buttonText) {
                    case "Patient":
                            Patient patientFrame = new Patient();
                            patientFrame.setVisible(true);
                            patientFrame.setLocationRelativeTo(null); // Center the Patient frame
                        break;
                    case "Doctor":
                            Doctor doctorFrame = new Doctor(userId, userType);
                            doctorFrame.setVisible(true);
                            doctorFrame.setLocationRelativeTo(null); // Center the Doctor frame
                        break;
                    case "Create Channel":
                            Channel channelFrame = new Channel();
                            channelFrame.setVisible(true);
                            channelFrame.setLocationRelativeTo(null); // Center the Channel frame
                        break;
                    case "View Prescription":
                            ViewPrescription viewPrescriptionFrame = new ViewPrescription(userId, userType);
                            viewPrescriptionFrame.setVisible(true);
                            viewPrescriptionFrame.setLocationRelativeTo(null); // Center the Prescription frame
                        break;
                    case "View Channel":
                            ViewChannel viewChannelFrame = new ViewChannel(userId, userType);
                            viewChannelFrame.setVisible(true);
                            viewChannelFrame.setLocationRelativeTo(null); // Center the Channel frame
                        break;
                    case "Create Item": // Inventory
                            Items itemsFrame = new Items();
                            itemsFrame.setVisible(true);
                            itemsFrame.setLocationRelativeTo(null); // Center the Items frame
                        break;
                    case "Create User":
                            User userFrame = new User();
                            userFrame.setVisible(true);
                            userFrame.setLocationRelativeTo(null); // Center the User frame
                        break;
                    case "View Doctor":
                            ViewDoctor viewDoctorFrame = new ViewDoctor(userId, userType);
                            viewDoctorFrame.setVisible(true);
                            viewDoctorFrame.setLocationRelativeTo(null); // Center the Doctor frame
                        break;
                    case "Monthly Report":
                            MonthlyReport monthlyReportFrame = new MonthlyReport();
                            monthlyReportFrame.setVisible(true);
                            monthlyReportFrame.setLocationRelativeTo(null); // Center the Monthly Report frame
                        break;
                    case "Logout":
                            dispose(); // Close the current frame
                            Login loginFrame = new Login();
                            loginFrame.setVisible(true);
                            loginFrame.setLocationRelativeTo(null); // Center the Login frame
                        break;
                }

                });
                yPosition += 40; // Increment position only if the button is visible
            }
        }


      //////////////////////////////////////////////

        // Title Label (Top Center)
        JLabel lblTitle = new JLabel("*** Medicare Plus+ ****");
        lblTitle.setForeground(Color.RED);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setBounds(300, 20, 400, 50);
        contentPane.add(lblTitle);

        // Main Content Panel (Right Side)
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 30, 30)); // Dark gray color
        mainPanel.setBounds(210, 100, 550, 150);
        mainPanel.setLayout(null);
        contentPane.add(mainPanel);

        // Labels for User Info
        JLabel lblUsername = new JLabel("UserName");
        lblUsername.setForeground(Color.YELLOW);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUsername.setBounds(50, 30, 100, 30);
        mainPanel.add(lblUsername);

        JLabel lblUserType = new JLabel("UserType");
        lblUserType.setForeground(Color.YELLOW);
        lblUserType.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUserType.setBounds(50, 80, 100, 30);
        mainPanel.add(lblUserType);

        // Dynamic Labels (Example placeholders)
        JLabel lblUsernameValue = new JLabel('@'+username); // Display the passed username
        lblUsernameValue.setForeground(Color.WHITE);
        lblUsernameValue.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblUsernameValue.setBounds(200, 30, 200, 30);
        mainPanel.add(lblUsernameValue);

        JLabel lblUserTypeValue = new JLabel(userType); // Display the passed user type
        lblUserTypeValue.setForeground(Color.WHITE);
        lblUserTypeValue.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblUserTypeValue.setBounds(200, 80, 200, 30);
        mainPanel.add(lblUserTypeValue);
        
        // Center the Main window on screen
        setLocationRelativeTo(null); // This centers the Main window
    }
}
