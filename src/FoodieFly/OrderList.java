package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class OrderList extends JPanel {

    JPanel orderpanel, p0, p1, p2, p3, cartf, tcf;
    JLabel username, phno, address, status;
    JTable table;
    DefaultTableModel model;
    JTextField totalCostF;
    String[] columnNames = {"Product", "Quantity"};
    Object[][] data;
    JButton confirm;
    private int currentCartID = -1; // Initialize with an invalid ID

    public JPanel createorderPanel() throws ClassNotFoundException, SQLException {
        // Custom rounded border for UI components
        RoundedBorder rb = new RoundedBorder(15, Color.BLACK);

        // Order panel with layout and size
        orderpanel = new JPanel(new BorderLayout());

        // Create button panel for cart IDs 1 to 20
        p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.X_AXIS));
        p0.setPreferredSize(new Dimension(1200, 30));
        for (int i = 1; i <= 20; i++) {
            JButton idnum = new JButton(String.valueOf(i));
            idnum.setPreferredSize(new Dimension(61, 30));
            p0.add(idnum);

            int cartID = i;

            idnum.addActionListener(e -> {
                try {
                	//System.out.println("Button pressed: " + cartID); // Debugging
                	updateOrderPanel(cartID);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
            });
        }

        // Create a panel for user information
        p1 = new JPanel(new FlowLayout());
        username = new JLabel("");
        phno = new JLabel("");
        address = new JLabel("");
        setupUserInfoLabels(rb);

        // Product Table
        p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.setPreferredSize(new Dimension(580, 400));

        cartf = new JPanel(new BorderLayout());
        cartf.setPreferredSize(new Dimension(580, 300));

        // Initialize table
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        setupTable();

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(Color.white);
        cartf.add(sp);

        // Total Cost Field
        tcf = new JPanel(new GridLayout(1, 2));
        tcf.setPreferredSize(new Dimension(580, 70));
        JLabel tcTF = new JLabel("Total Cost: ");
        tcTF.setFont(new Font("Arial", Font.BOLD, 16));
        tcTF.setHorizontalAlignment(SwingConstants.RIGHT);

        totalCostF = new JTextField();
        totalCostF.setFont(new Font("Arial", Font.BOLD, 16));
        totalCostF.setHorizontalAlignment(SwingConstants.CENTER);
        totalCostF.setEditable(false);
        tcf.add(tcTF);
        tcf.add(totalCostF);

        p2.add(cartf, BorderLayout.NORTH);
        p2.add(tcf, BorderLayout.SOUTH);

        // Confirm button and status label
        p3 = new JPanel(new FlowLayout());
        status = new JLabel("");
        setupConfirmPanel(rb);

        // Add panels to main orderpanel
        orderpanel.add(p0, BorderLayout.NORTH);
        orderpanel.add(p1, BorderLayout.WEST);
        orderpanel.add(p2, BorderLayout.CENTER);
        orderpanel.add(p3, BorderLayout.EAST);
        orderpanel.setPreferredSize(new Dimension(1200, 450));

        return orderpanel;
    }

    private void setupUserInfoLabels(RoundedBorder rb) {
        setupLabel(username, rb);
        setupLabel(phno, rb);
        setupLabel(address, rb);

        p1.add(Box.createRigidArea(new Dimension(200, 20)));
        p1.add(username);
        p1.add(Box.createRigidArea(new Dimension(200, 50)));
        p1.add(phno);
        p1.add(Box.createRigidArea(new Dimension(200, 50)));
        p1.add(address);
        p1.setPreferredSize(new Dimension(300, 420));
    }

    private void setupLabel(JLabel label, RoundedBorder rb) {
        label.setPreferredSize(new Dimension(200, 70));
        label.setForeground(Color.BLACK);
        label.setBorder(rb);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setupTable() {
        table.setRowHeight(50);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(100, 50));
        header.setDefaultRenderer(new CustomHeaderRenderer());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void setupConfirmPanel(RoundedBorder rb) {
        status.setForeground(Color.BLACK);
        status.setBorder(rb);
        status.setFont(new Font("Arial", Font.PLAIN, 18));
        status.setPreferredSize(new Dimension(200, 70));
        status.setHorizontalAlignment(SwingConstants.CENTER);

        confirm = new JButton("CONFIRM");
        confirm.setBorder(rb);
        confirm.setFont(new Font("Arial", Font.PLAIN, 18));
        confirm.setPreferredSize(new Dimension(200, 70));
        confirm.addActionListener(e -> {
			try {
				confirmOrder();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        p3.add(Box.createRigidArea(new Dimension(200, 370)));
        p3.add(status);
        p3.add(Box.createRigidArea(new Dimension(200, 50)));
        p3.add(confirm);
        p3.setPreferredSize(new Dimension(300, 420));
    }

    private void confirmOrder() throws ClassNotFoundException {
        if (currentCartID == -1) {
            JOptionPane.showMessageDialog(null, "No cart selected.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123")) {
            String StatusUpdateSql = "UPDATE carttable SET status1 = ? WHERE ID = ?";
            PreparedStatement pstm = connection.prepareStatement(StatusUpdateSql);
            pstm.setString(1, "Confirmed");
            pstm.setInt(2, currentCartID); // Use the current cart ID
            pstm.executeUpdate();

            // Optionally refresh the panel after confirming
            updateOrderPanel(currentCartID);

            JOptionPane.showMessageDialog(null, "Order confirmed! Wait for the delivery ^_^");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while confirming the order.");
        }
    }
    
    // Custom Table Header Renderer
    class CustomHeaderRenderer extends DefaultTableCellRenderer {
        public CustomHeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(new Color(249, 212, 2)); // Custom color for header
            setFont(new Font("Arial", Font.BOLD, 16));
            return this;
        }
    }
  
    public void updateOrderPanel(int cartID) throws ClassNotFoundException, SQLException {
    	currentCartID = cartID; // Set the current cart ID
    	
    	// Update user info based on cartID
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123")) {
            // Fetch user info based on cartID
            String sql = "SELECT userName, total_cost, status1 FROM carttable WHERE ID = ?";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, cartID);
            ResultSet rs = pstatement.executeQuery();

            String un = null, ttc = null, sts = null;
            if (rs.next()) {
                un = rs.getString(1);
                ttc = rs.getString(2);
                sts = rs.getString(3);
            }

            String sql3 = "SELECT PhoneNumber, Address FROM logintable WHERE Name = ?";
            PreparedStatement pstatement3 = connection.prepareStatement(sql3);
            pstatement3.setString(1, un);
            ResultSet rs3 = pstatement3.executeQuery();

            String pn = null, addr = null;
            if (rs3.next()) {
                pn = rs3.getString(1);
                addr = rs3.getString(2);
            }

            // Update text labels
            username.setText(un);
            phno.setText(pn);
            address.setText(addr);

            // Fetch product details for the selected cart
            String sql2 = "SELECT productName, quantity FROM producttable WHERE cartID = ?";
            PreparedStatement pstatement2 = connection.prepareStatement(sql2);
            pstatement2.setInt(1, cartID);
            ResultSet rs2 = pstatement2.executeQuery();

            List<Object[]> rowList = new ArrayList<>();
            while (rs2.next()) {
                String product = rs2.getString(1);
                String count = rs2.getString(2);
                rowList.add(new Object[]{product, count});
            }

            data = rowList.toArray(new Object[0][]);

            // Update table data
            model.setDataVector(data, columnNames);
            setupTable();
            
            // Update total cost and status
            totalCostF.setText(ttc);
            status.setText(sts);

            // Refresh the panels
            p1.revalidate();
            p1.repaint();
            p2.revalidate();
            p2.repaint();
            p3.revalidate();
            p3.repaint();
        }
    }

}
