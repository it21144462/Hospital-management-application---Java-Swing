//package medicareplus;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//
//public class MonthlyReport extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
//    private JTable table;
//    private DefaultTableModel tableModel;
//
//    Connection con;
//    PreparedStatement pst;
//    ResultSet rs;
//
//    public void Connect() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    MonthlyReport frame = new MonthlyReport();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public MonthlyReport() {
//        setTitle("Medicare Plus+ Inventory Management");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 800, 600);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(null);
//        contentPane.setBackground(new Color(30, 30, 30));
//        setContentPane(contentPane);
//
//        JLabel lblTitle = new JLabel("Sales Management");
//        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
//        lblTitle.setForeground(Color.YELLOW);
//        lblTitle.setBounds(300, 10, 250, 30);
//        contentPane.add(lblTitle);
//
//        tableModel = new DefaultTableModel(new Object[]{"Item Name", "Quantity Sold", "Total Revenue", "Profit"}, 0);
//        table = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBounds(50, 50, 700, 400);
//        contentPane.add(scrollPane);
//
//        JButton btnMonthlyReport = new JButton("Monthly Report");
//        btnMonthlyReport.setBounds(50, 500, 150, 30);
//        contentPane.add(btnMonthlyReport);
//
//        JButton btnWeeklyReport = new JButton("Weekly Report");
//        btnWeeklyReport.setBounds(250, 500, 150, 30);
//        contentPane.add(btnWeeklyReport);
//
//        JButton btnYearlyReport = new JButton("Yearly Report");
//        btnYearlyReport.setBounds(450, 500, 150, 30);
//        contentPane.add(btnYearlyReport);
//
//        JButton btnDownloadReport = new JButton("Download Report");
//        btnDownloadReport.setBounds(650, 500, 150, 30);
//        contentPane.add(btnDownloadReport);
//
//        Connect();
//
//        btnMonthlyReport.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                loadReport("MONTH");
//            }
//        });
//
//        btnWeeklyReport.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                loadReport("WEEK");
//            }
//        });
//
//        btnYearlyReport.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                loadReport("YEAR");
//            }
//        });
//
//        btnDownloadReport.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                downloadReport();
//            }
//        });
//    }
//
//    private void loadReport(String period) {
//        try {
//            tableModel.setRowCount(0);
//            String query = "SELECT i.itemname, SUM(sp.qty) AS total_qty, SUM(sp.total) AS total_revenue, SUM((sp.sellprice - i.buyprice) * sp.qty) AS profit " +
//                           "FROM sales s " +
//                           "JOIN salesproduct sp ON s.id = sp.sales_id " +
//                           "JOIN item i ON sp.product_id = i.itemno " +
//                           "WHERE s.date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 " + period + ") " +
//                           "GROUP BY sp.product_id";
//            pst = con.prepareStatement(query);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                tableModel.addRow(new Object[]{
//                    rs.getString("itemname"),
//                    rs.getInt("total_qty"),
//                    rs.getDouble("total_revenue"),
//                    rs.getDouble("profit")
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void downloadReport() {
//        try {
//            File file = new File("Report.csv");
//            FileWriter writer = new FileWriter(file);
//
//            writer.write("Item Name,Quantity Sold,Total Revenue,Profit\n");
//            for (int i = 0; i < tableModel.getRowCount(); i++) {
//                writer.write(tableModel.getValueAt(i, 0) + "," +
//                             tableModel.getValueAt(i, 1) + "," +
//                             tableModel.getValueAt(i, 2) + "," +
//                             tableModel.getValueAt(i, 3) + "\n");
//            }
//
//            writer.close();
//            JOptionPane.showMessageDialog(this, "Report downloaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//        } catch (IOException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Failed to download report!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}

package medicareplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MonthlyReport extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<ItemRevenue> itemRevenues;
    private JLabel lblTotalRevenue;
    private JLabel lblTotalProfit;
    private JButton btnExit;
    private JButton btnInventoryLevel;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/medicare_plus", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MonthlyReport frame = new MonthlyReport();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MonthlyReport() {
        setTitle("Medicare Plus+ Inventory Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (itemRevenues != null && !itemRevenues.isEmpty()) {
                    drawPieChart((Graphics2D) g);
                }
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(30, 30, 30));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Sales Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setBounds(300, 10, 250, 30);
        contentPane.add(lblTitle);

        tableModel = new DefaultTableModel(new Object[]{"Item Name", "Quantity Sold", "Total Revenue", "Profit"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 700, 200);
        contentPane.add(scrollPane);

        JLabel lblTotalRevenueLabel = new JLabel("Total Revenue:");
        lblTotalRevenueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTotalRevenueLabel.setForeground(Color.WHITE);
        lblTotalRevenueLabel.setBounds(50, 250, 150, 30);
        contentPane.add(lblTotalRevenueLabel);

        lblTotalRevenue = new JLabel();
        lblTotalRevenue.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotalRevenue.setForeground(Color.YELLOW);
        lblTotalRevenue.setBounds(150, 250, 200, 30);
        contentPane.add(lblTotalRevenue);

        JLabel lblTotalProfitLabel = new JLabel("Total Profit:");
        lblTotalProfitLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTotalProfitLabel.setForeground(Color.WHITE);
        lblTotalProfitLabel.setBounds(350, 250, 150, 30);
        contentPane.add(lblTotalProfitLabel);

        lblTotalProfit = new JLabel();
        lblTotalProfit.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotalProfit.setForeground(Color.YELLOW);
        lblTotalProfit.setBounds(430, 250, 200, 30);
        contentPane.add(lblTotalProfit);
        
        btnInventoryLevel = new JButton("Inventory Level");
        btnInventoryLevel.setBounds(610, 260, 140, 30);
        contentPane.add(btnInventoryLevel);

        JLabel lblReportType = new JLabel("Select Report Type:");
        lblReportType.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblReportType.setForeground(Color.WHITE);
        lblReportType.setBounds(50, 300, 150, 30);
        contentPane.add(lblReportType);

        JComboBox<String> reportTypeComboBox = new JComboBox<>(new String[]{"Monthly Report", "Weekly Report", "Yearly Report"});
        reportTypeComboBox.setBounds(200, 300, 200, 30);
        contentPane.add(reportTypeComboBox);

        JButton btnGenerateReport = new JButton("Generate Report");
        btnGenerateReport.setBounds(410, 300, 130, 30);
        contentPane.add(btnGenerateReport);

        JButton btnDownloadReport = new JButton("Download Report");
        btnDownloadReport.setBounds(610, 300, 140, 30);
        btnDownloadReport.setEnabled(false); // Disable DownloadReport button by default
        contentPane.add(btnDownloadReport);
        
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(620, 510, 130, 30); 
        contentPane.add(btnExit);

        Connect();

        btnGenerateReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedReport = (String) reportTypeComboBox.getSelectedItem();
                if (selectedReport != null) {
                    String period = "";
                    switch (selectedReport) {
                        case "Monthly Report":
                            period = "MONTH";
                            break;
                        case "Weekly Report":
                            period = "WEEK";
                            break;
                        case "Yearly Report":
                            period = "YEAR";
                            break;
                    }
                    loadReport(period);       
                    btnDownloadReport.setEnabled(true); // Enable DownloadReport button
                }
            }
        });

        btnDownloadReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                downloadReport();
            }
        });
        
        //InventoryLevel Button Action
        btnInventoryLevel.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   InventoryLevel inventorylevelFrame = new InventoryLevel();
            	   inventorylevelFrame.setVisible(true);
               }
        });
        
        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current window
                MonthlyReport.this.setVisible(false);
            }
        });
        
        setLocationRelativeTo(null); //center the MonthlyReport window

    }

    private void loadReport(String period) {
        try {
            tableModel.setRowCount(0);
            itemRevenues = new ArrayList<>();
            String query = "SELECT i.itemname, SUM(sp.qty) AS total_qty, SUM(sp.total) AS total_revenue, SUM((sp.sellprice - i.buyprice) * sp.qty) AS profit " +
                           "FROM sales s " +
                           "JOIN salesproduct sp ON s.id = sp.sales_id " +
                           "JOIN item i ON sp.product_id = i.itemno " +
                           "WHERE s.date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 " + period + ") " +
                           "GROUP BY sp.product_id";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            double totalRevenue = 0;
            double totalProfit = 0;

            while (rs.next()) {
                String itemName = rs.getString("itemname");
                int totalQty = rs.getInt("total_qty");
                double revenue = rs.getDouble("total_revenue");
                double profit = rs.getDouble("profit");

                totalRevenue += revenue;
                totalProfit += profit;

                itemRevenues.add(new ItemRevenue(itemName, revenue));

                tableModel.addRow(new Object[]{
                    itemName, totalQty, revenue, profit
                });
            }

         // Set the text correctly in loadReport() method
            lblTotalRevenue.setText(String.format("$%.2f", totalRevenue));
            lblTotalProfit.setText(String.format("$%.2f", totalProfit));

            for (ItemRevenue item : itemRevenues) {
                item.calculatePercentage(totalRevenue);
            }

            repaint();

            JOptionPane.showMessageDialog(this, "Report generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawPieChart(Graphics2D g2d) {
        int x = 50;
        int y = 350;
        int width = 200;
        int height = 200;
        int startAngle = 0;

        for (ItemRevenue item : itemRevenues) {
            g2d.setColor(item.getColor());
            int arcAngle = (int) Math.round(item.getPercentage() * 360 / 100);
            g2d.fillArc(x, y, width, height, startAngle, arcAngle);
            startAngle += arcAngle;
        }

        int legendX = x + width + 20;
        int legendY = y;
        for (ItemRevenue item : itemRevenues) {
            g2d.setColor(item.getColor());
            g2d.fillRect(legendX, legendY, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawString(item.getItemName() + " - " + String.format("%.2f", item.getPercentage()) + "%", legendX + 30, legendY + 15);
            legendY += 30;
        }
    }


    private void downloadReport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setSelectedFile(new File("MedicarePlus_Revenue_Report.txt"));
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
                writer.write("****************************************************************\n");
                writer.write("                           Medicare Report                      \n");
                writer.write("****************************************************************\n\n");

                writer.write("Table of Sales Data\n");
                writer.write("----------------------------------------------------------------\n");
                writer.write(String.format("%-20s %-15s %-15s %-15s\n", "Item Name", "Quantity Sold", "Revenue", "Profit"));
                writer.write("----------------------------------------------------------------\n");
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    writer.write(String.format("%-20s %-15s %-15s %-15s\n",
                            tableModel.getValueAt(i, 0),
                            tableModel.getValueAt(i, 1),
                            String.format("$%.2f", tableModel.getValueAt(i, 2)),
                            String.format("$%.2f", tableModel.getValueAt(i, 3))));
                }
                writer.write("----------------------------------------------------------------\n\n");

                writer.write(String.format("Total Revenue: %s\n", lblTotalRevenue.getText()));
                writer.write(String.format("Total Items Sold: %d\n\n", calculateTotalItemsSold()));

                writer.write("Revenue Proportion (Pie Chart Data)\n");
                writer.write("----------------------------------------------------------------\n");
                for (ItemRevenue item : itemRevenues) {
                    writer.write(String.format("%-20s %-10.2f%%\n", item.getItemName(), item.getPercentage()));
                }
                writer.write("----------------------------------------------------------------\n");
                
                // Adding policies and disclaimers
                writer.write("****************************************************************\n");
                writer.write("                          Important Policies                    \n");
                writer.write("****************************************************************\n\n");

                writer.write("1. **Data Accuracy**: The information provided in this report\n"
                		+ " is based on the most recent data available in our system. While\n"
                		+ " efforts are made to ensure accuracy, discrepancies may occur due\n"
                		+ " to real-time updates.\n");
                writer.write("2. **Confidentiality**: This report is intended for internal\n"
                		+ " use only. Unauthorized distribution, reproduction, or sharing\n"
                		+ " is strictly prohibited.\n");
                writer.write("3. **Compliance**: All data is collected and processed in\n"
                		+ " compliance with applicable privacy and data protection laws.\n");
                writer.write("4. **Support**: For any queries regarding this report,\n"
                		+ " please contact our support team at support@medicare.com\n"
                		+ " or call 1-800-MEDICARE.\n\n");

                writer.write("****************************************************************\n");
                writer.write("                           Disclaimer                           \n");
                writer.write("****************************************************************\n\n");

                writer.write("The contents of this report are provided for informational purposes\n"
                		+ " only. Medicare assumes no liability for any errors or omissions.\n"
                		+ " Use of this report constitutes acceptance of the terms and \n"
                		+ "conditions outlined herein.\n");

                writer.write("----------------------------------------------------------------\n");
                writer.write("THANK YOU FOR CHOOSING MEDICARE PLUS+.\n YOUR HEALTH, OUR PRIORITY.\n");
                writer.write("----------------------------------------------------------------\n");

                JOptionPane.showMessageDialog(this, "Report downloaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to download report!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int calculateTotalItemsSold() {
        int totalItems = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalItems += Integer.parseInt(tableModel.getValueAt(i, 1).toString());
        }
        return totalItems;
    }




    private class ItemRevenue {
        private String itemName;
        private double revenue;
        private double percentage;
        private Color color;

        public ItemRevenue(String itemName, double revenue) {
            this.itemName = itemName;
            this.revenue = revenue;
            this.color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        }

        public String getItemName() {
            return itemName;
        }

        public double getPercentage() {
            return percentage;
        }

        public Color getColor() {
            return color;
        }

        public void calculatePercentage(double totalRevenue) {
            this.percentage = (revenue / totalRevenue) * 100;
        }
    }
}
