package medicareplus;

import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class BrowseDrugs extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JButton btnExit;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    // Method to connect to the database
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BrowseDrugs frame = new BrowseDrugs();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public BrowseDrugs() {
        Connect(); // Establish database connection

        setTitle("\"Medicare Plus+ Browse Drugs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Table to display drug information
        DefaultTableModel model = new DefaultTableModel(new String[]{"Drug Code", "Drug Name", "Description"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 560, 350);
        contentPane.add(scrollPane);

        // Fetch drug data from the database
        fetchDrugData(model);

        // Exit button
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(260, 400, 70, 30); // Positioned at the center bottom
        contentPane.add(btnExit);

        // Exit Button Action
        btnExit.addActionListener(e -> {
            // Hide the current window
            BrowseDrugs.this.setVisible(false);
        });
        
        setLocationRelativeTo(null);
    }

    // Fetch drug data from the database
    private void fetchDrugData(DefaultTableModel model) {
        try {
            String query = "SELECT itemno, itemname, description FROM item";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                String itemNo = rs.getString("itemno");
                String itemName = rs.getString("itemname");
                String description = rs.getString("description");

                model.addRow(new Object[]{itemNo, itemName, description});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching drug data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
