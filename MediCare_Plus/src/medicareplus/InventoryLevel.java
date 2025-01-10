////package medicareplus;
////
////import java.awt.EventQueue;
////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.PreparedStatement;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////
////import javax.swing.JFrame;
////import javax.swing.JOptionPane;
////import javax.swing.JPanel;
////import javax.swing.border.EmptyBorder;
////
////public class InventoryLevel extends JFrame {
////
////	private static final long serialVersionUID = 1L;
////	private JPanel contentPane;
////	
////	Connection con;
////    PreparedStatement pst;
////    ResultSet rs;
////
////    // Method to connect to the database
////    public void Connect() {
////        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
////        } catch (ClassNotFoundException | SQLException e) {
////            e.printStackTrace();
////            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
////        }
////    }
////
////	/**
////	 * Launch the application.
////	 */
////	public static void main(String[] args) {
////		EventQueue.invokeLater(new Runnable() {
////			public void run() {
////				try {
////					InventoryLevel frame = new InventoryLevel();
////					frame.setVisible(true);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
////		});
////	}
////
////	/**
////	 * Create the frame.
////	 */
////	public InventoryLevel() {
////		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		setBounds(100, 100, 450, 300);
////		contentPane = new JPanel();
////		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
////
////		setContentPane(contentPane);
////	}
////
////}
//package medicareplus;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//
//public class InventoryLevel extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
//    private JTable table;
//    private JButton btnExit;
//
//    Connection con;
//    PreparedStatement pst;
//    ResultSet rs;
//
//    // Method to connect to the database
//    public void Connect() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                InventoryLevel frame = new InventoryLevel();
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    /**
//     * Create the frame.
//     */
//    public InventoryLevel() {
//        Connect(); // Establish database connection
//
//        setTitle("Medicare Plus+ Inventory Level");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setBounds(100, 100, 600, 500);
//        contentPane = new JPanel();
//        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(null);
//        setContentPane(contentPane);
//
//        // Table to display inventory information
//        DefaultTableModel model = new DefaultTableModel(new String[]{"Drug Code", "Drug Name", "Current Inventory Level"}, 0);
//        table = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBounds(10, 10, 560, 350);
//        contentPane.add(scrollPane);
//
//        // Fetch inventory data from the database
//        fetchInventoryData(model);
//        
//        JButton btnDownloadReport = new JButton("Download Report");
//        btnDownloadReport.setFont(new Font("Tahoma", Font.BOLD, 14));
//        btnDownloadReport.setBounds(125, 400, 180, 30);
//        contentPane.add(btnDownloadReport);
//
//        // Exit button
//        btnExit = new JButton("Exit");
//        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
//        btnExit.setBounds(320, 400, 70, 30); // Positioned at the center bottom
//        contentPane.add(btnExit);
//
//        // Exit Button Action
//        btnExit.addActionListener(e -> {
//            // Hide the current window
//            InventoryLevel.this.setVisible(false);
//        });
//        
//        setLocationRelativeTo(null);
//    }
//
//    // Fetch inventory data from the database
//    private void fetchInventoryData(DefaultTableModel model) {
//        try {
//            String query = "SELECT itemno, itemname, qty FROM item";
//            pst = con.prepareStatement(query);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                String itemNo = rs.getString("itemno");
//                String itemName = rs.getString("itemname");
//                int quantity = rs.getInt("qty");
//
//                model.addRow(new Object[]{itemNo, itemName, quantity});
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "Error fetching inventory data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}
package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class InventoryLevel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JButton btnExit, btnDownloadReport;

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
                InventoryLevel frame = new InventoryLevel();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public InventoryLevel() {
        Connect(); // Establish database connection

        setTitle("Medicare Plus+ Inventory Level");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); // Dark gray color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Table to display inventory information
        DefaultTableModel model = new DefaultTableModel(new String[]{"Item No", "Item Name", "Quantity"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 560, 350);
        contentPane.add(scrollPane);

        // Fetch inventory data from the database
        fetchInventoryData(model);

        // Download Report button
        btnDownloadReport = new JButton("Download Report");
        btnDownloadReport.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDownloadReport.setBounds(145, 400, 160, 30);
        contentPane.add(btnDownloadReport);

        // Action for Download Report button
        btnDownloadReport.addActionListener(e -> downloadReport(model));

        // Exit button
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(320, 400, 70, 30);
        contentPane.add(btnExit);

        // Exit Button Action
        btnExit.addActionListener(e -> {
            InventoryLevel.this.setVisible(false);
        });

        setLocationRelativeTo(null);
    }

    // Fetch inventory data from the database
    private void fetchInventoryData(DefaultTableModel model) {
        try {
            String query = "SELECT itemno, itemname, qty FROM item";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                String itemNo = rs.getString("itemno");
                String itemName = rs.getString("itemname");
                int quantity = rs.getInt("qty");

                model.addRow(new Object[]{itemNo, itemName, quantity});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching inventory data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to download the table data as a report
    private void downloadReport(DefaultTableModel model) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setSelectedFile(new File("MedicarePlus_Inventory_Report.txt"));
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (file.exists()) {
                int overwriteResult = JOptionPane.showConfirmDialog(this,
                        "The file already exists. Do you want to overwrite it?",
                        "Confirm Overwrite",
                        JOptionPane.YES_NO_OPTION);
                if (overwriteResult == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
            	// Getting the current date and time
                String currentDate = java.time.LocalDate.now().toString();
                
                writer.write("*********************************************************\n");
                writer.write("                  Medicare Inventory Report              \n");
                writer.write("*********************************************************\n\n");

                writer.write("This is the current up-to-date inventory level report as of: " + currentDate + "\n\n");
                writer.write(String.format("%-15s %-25s %-10s\n", "Item No", "Item Name", "Quantity"));
                writer.write("---------------------------------------------------------\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    writer.write(String.format("%-15s %-25s %-10s\n",
                            model.getValueAt(i, 0),
                            model.getValueAt(i, 1),
                            model.getValueAt(i, 2)));
                }

                writer.write("---------------------------------------------------------\n");
                writer.write("This report reflects the most recent inventory levels.\n\n");
                
                writer.write("THANK YOU FOR CHOOSING MEDICARE PLUS+. YOUR HEALTH, OUR PRIORITY.\n");

                JOptionPane.showMessageDialog(this, "Inventory levelsReport downloaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to download report!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
