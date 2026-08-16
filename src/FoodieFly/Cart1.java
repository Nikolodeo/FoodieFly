package FoodieFly;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

import FoodieFly.UserList.CustomHeaderRenderer;
import FoodieFly.UserList.CustomTableCellRenderer;

import java.util.List;

public class Cart1 extends JFrame{
	JPanel panel,p1,p2,p3;
	JButton orderBtn;
	String name,product;
	int quantity,totalCost;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField totalCostField;
    
	public Cart1(List<String[]> cartItems,String name){
		this.name = name;
		panel = new JPanel(new BorderLayout());
      
		p1 = new JPanel(new BorderLayout());
		String[] columnNames = {"Product","Price","Quantity","Total Price"};
		tableModel = new DefaultTableModel(cartItems.toArray(new Object[0][]), columnNames);
    
		table = new JTable(tableModel);
		//table.setBounds(30, 40, 200, 200);
	    table.setOpaque(false);
	    table.setRowHeight(50);
	    table.setFont(new Font("Arial",Font.PLAIN,16));
	    table.setGridColor(Color.LIGHT_GRAY);
	    table.setShowVerticalLines(false);
	    table.setShowHorizontalLines(false);
	    table.setIntercellSpacing(new Dimension(0,0));
   
    
	    JTableHeader tableHeader = table.getTableHeader();
	    tableHeader.setPreferredSize(new Dimension(100,40));
	    // Customize the JTableHeader appearance to remove vertical lines
    
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel headerLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Remove border to hide vertical lines
                headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                headerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
                headerLabel.setFont(new Font("Arial",Font.PLAIN,16));
                headerLabel.setBackground(Color.LIGHT_GRAY);
                return headerLabel;
            }
        });
        
        CustomTableCellRenderer renderer = new CustomTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
     // Apply custom header renderer
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());
        header.setPreferredSize(new Dimension(100, 40));
    
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(Color.white);
    
        p1.add(sp);
        p1.setOpaque(false);
        p1.setPreferredSize(new Dimension(400,560));
  
        JPanel p2 = new JPanel(new GridLayout(1,2));
        p2.setOpaque(false);
        p2.setPreferredSize(new Dimension(400,60));
        JLabel tp = new JLabel("Total Cost :");
        tp.setFont(new Font("Arial",Font.BOLD,16));
        tp.setHorizontalAlignment(SwingConstants.RIGHT);
      
        totalCostField = new JTextField();
        totalCostField.setOpaque(false);
        totalCostField.setFont(new Font("Arial", Font.BOLD, 16));
        totalCostField.setHorizontalAlignment(SwingConstants.CENTER);
        totalCostField.setBorder(null);
        totalCostField.setEditable(false);  // Make the field non-editable
        p2.add(tp);
        p2.add(totalCostField);
       
        updateTotalCost();
        
        //two buttons
        JPanel p3 = new JPanel(new GridLayout(1,2));
        p3.setPreferredSize(new Dimension(400,40));
      
        JButton editBtn = new JButton("Edit");
        editBtn.setFont(new Font("Arial",Font.BOLD,16));
        editBtn.setPreferredSize(new Dimension(200,40));
      
        p3.add(editBtn);
        editBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==editBtn) {
        			cartItems.clear();
        			dispose();
        		}
        	}
        });
        
        orderBtn = new JButton("Order");
        orderBtn.setFont(new Font("Arial",Font.BOLD,16));
        p3.add(orderBtn);
        orderBtn.addActionListener(new ButtonListener());
        panel.add(p1,BorderLayout.NORTH);
        panel.add(p2,BorderLayout.CENTER);
        panel.add(p3,BorderLayout.SOUTH);
         
        add(panel);
        setSize(600,700);
        setLocationRelativeTo(null);
        setVisible(true);      
	}
    
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String orderstatus = "pending";
			if(e.getSource() == orderBtn) {
				new UserInfo(Cart1.this,name);
    		
				int totalCost = Integer.parseInt(totalCostField.getText());
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly","root","Databasethinml123");
	          
					// Retrieve the maximum cartID from the carttable
					String getMaxCartIdSQL = "SELECT MAX(ID) FROM carttable";
					PreparedStatement maxCartIdStmt = connection.prepareStatement(getMaxCartIdSQL);
					ResultSet rs = maxCartIdStmt.executeQuery();

					int newCartId = 1;  // Default cartID if there are no existing records
					if (rs.next()) {
						newCartId = rs.getInt(1) + 1;  // Increment the max cartID by 1
					}
	          
					String sql = "insert into carttable(userName,total_cost,status1) values(?,?,?)";
					PreparedStatement pstatement = connection.prepareStatement(sql);
	          
					pstatement.setString(1, name); 
					pstatement.setInt(2, totalCost);
					pstatement.setString(3, orderstatus);
					pstatement.executeUpdate();
	          
					for(int i=0;i<tableModel.getRowCount();i++) {
						String sql2 = "insert into producttable(productName, quantity, cartID) values(?,?,?)";
						PreparedStatement pstatement2 = connection.prepareStatement(sql2);       
						product = (String) tableModel.getValueAt(i, 0);
						pstatement2.setString(1, product);    	  
						quantity = Integer.parseInt((String) tableModel.getValueAt(i, 2));
						pstatement2.setInt(2, quantity); 
						pstatement2.setInt(3, newCartId);
						pstatement2.execute();
					}
	        
					connection.close();
	          
					JOptionPane.showMessageDialog(null, "Ordered Successfully!");
				} catch (ClassNotFoundException e1) {
	          	  	e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
    }
    
    private void updateTotalCost() {
    	int totalCost = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
        	String totalPriceStr = (String) tableModel.getValueAt(i, 3);
            totalCost += Integer.parseInt(totalPriceStr);
        }
        totalCostField.setText(String.valueOf(totalCost));
    }
    
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
        														boolean isSelected, boolean hasFocus, int row, int column) {
        	Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
        	// Set custom background color for odd rows
        	if (!isSelected) {
        		if (row % 2 == 1) {
        			c.setBackground(new Color(249, 245, 240)); 
        		} 
        		else {
        			c.setBackground(Color.white);
        		}
        	}
        	// Override background color for selected rows
        	if (isSelected) {
        		c.setBackground(table.getSelectionBackground());
        	}
        	return c;
        }
    }

    class CustomHeaderRenderer extends DefaultTableCellRenderer {
        public CustomHeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Set the background and foreground colors for the header
            setBackground(new Color(249, 212, 2)); 

            // Set font if needed
            setFont(new Font("Arial", Font.BOLD, 16));

            return this;
        }
    }
    
    public void refreshCart(List<String[]> cartItems) {
        tableModel.setDataVector(cartItems.toArray(new Object[0][]), new String[]{"Product", "Price", "Quantity", "Total Price"});
        tableModel.fireTableDataChanged();
    }
}