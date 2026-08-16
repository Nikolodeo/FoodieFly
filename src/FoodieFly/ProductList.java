package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;


public class ProductList extends JFrame{
	JPanel productpanel,p1,p;
	Object[][] data;
	String name,price,catagory,description,status;
	int id,selectedId;
	boolean select;
	JButton update,add,remove;
	JTable table ;
  
	public JPanel createproductPanel() throws SQLException, ClassNotFoundException{
    
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly","root","Databasethinml123");
		//System.out.println("connected");
		String sql = "select * from itemtable";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
    
		ArrayList<Object[]> rowList = new ArrayList<>();
		while(rs.next()) {
			id = rs.getInt(1);
			name = rs.getString(2);
			price =rs.getString(3);
			catagory = rs.getString(4);
			description =rs.getString(5);
			status =rs.getString(6);
			rowList.add(new Object[]{false,id,name,price,catagory,description,status});
		}
		data = rowList.toArray(new Object[0][]);
    
		RoundedBorder roundedBorder = new RoundedBorder(15,Color.LIGHT_GRAY);
    
	    productpanel = new JPanel(new BorderLayout());
	    productpanel.setOpaque(false);
	    
	    p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    p.setOpaque(false);
	    
	    update = new JButton("Update");
	    update.setBorder(roundedBorder);
	    update.setFont(new Font("Arial",Font.BOLD,15));
	    update.setPreferredSize(new Dimension(100,40));
	    p.add(update);
	    p.setBorder(new EmptyBorder(20,20,20,20));
	    update.setBackground(new Color(249, 212, 2));
	    update.addActionListener(new ButtonListener());
	  
	    // Adding new data to item table
	    add = new JButton("Add");
	    add.setBorder(roundedBorder);
	    add.setFont(new Font("Arial",Font.BOLD,15));
	    add.setPreferredSize(new Dimension(100,40));
	    p.add(add);
	    p.setBorder(new EmptyBorder(20,20,20,20));
	    add.setBackground(new Color(249, 212, 2));
	    add.addActionListener(new ButtonListener());
	    
	    remove = new JButton("Remove");
	    remove.setBorder(roundedBorder);
	    remove.setFont(new Font("Arial",Font.BOLD,15));
	    remove.setPreferredSize(new Dimension(100,40));
	    p.add(remove);
	    p.setBorder(new EmptyBorder(20,20,20,20));
	    remove.setBackground(new Color(249, 212, 2));
	    remove.addActionListener(new ButtonListener());
	    
	    
	    p1 = new JPanel(new BorderLayout());
	    String[] columnNames = {"","ID","Name","Price","Category","Description","Status"};
    
	    //create a table model with the data and column names
	    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
	    	@Override
	    	public Class<?> getColumnClass(int columnIndex) {
	    		if (columnIndex == 0) {
	    			return Boolean.class;
	    		}
	    		return super.getColumnClass(columnIndex);
	    	}
	    	
	    	@Override
	    	public boolean isCellEditable(int row, int column) {
	    		return column == 0; // Only the checkbox column is editable
	    	}

	    	@Override
	    	public void setValueAt(Object aValue, int row, int column) {
	    		if (column == 0) {
	    			// When a checkbox is selected, deselect all other checkboxes
	    			for (int i = 0; i < getRowCount(); i++) {
	    				super.setValueAt(false, i, column);
	    			}
	    			// Set the current checkbox to the selected state
	    			super.setValueAt(aValue, row, column);
	    			fireTableDataChanged(); // Notify the table that the data has changed
	    		} else {
	    			super.setValueAt(aValue, row, column);
	    		}
	    	}
	    };
	    table = new JTable(model);
	    table.setRowHeight(60);
	    table.setFont(new Font("Arial",Font.PLAIN,16));
	    table.setShowVerticalLines(false);
	    table.setShowHorizontalLines(false);
	    table.setIntercellSpacing(new Dimension(0,0));
	   
	    table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
	    table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));

     
	    JTableHeader tableHeader = table.getTableHeader();
	    tableHeader.setPreferredSize(new Dimension(100,40));
    
	    // Customize the JTableHeader appearance to remove vertical lines
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel headerLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Remove border to hide vertical lines
                headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                headerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
                headerLabel.setFont(new Font("Arial",Font.PLAIN,16));
                headerLabel.setBackground(Color.LIGHT_GRAY);
                return headerLabel;
            }
        });
    
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(8);
  
        // Apply custom header renderer
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());
        header.setPreferredSize(new Dimension(100, 40));
        
	    JScrollPane sp = new JScrollPane(table);
	    sp.getViewport().setBackground(Color.white);
	    
	    p1.add(sp);
	    p1.setOpaque(false);
	    p1.setPreferredSize(new Dimension(400,480));
	    productpanel.add(p,BorderLayout.NORTH);
	    productpanel.add(p1,BorderLayout.CENTER);
	
	    return productpanel;
    
	}
  
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { 
			if (e.getSource() == update) {
				boolean itemSelected = false; 

				// Loop through the table to find the selected rows
				for (int i = 0; i < table.getRowCount(); i++) {
					Boolean isSelected = (Boolean) table.getValueAt(i, 0); // Check if the checkbox is selected
					if (isSelected != null && isSelected) {
						itemSelected = true;
						selectedId = (Integer) table.getValueAt(i, 1); // Get the ID from the selected row
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
							String sql1 = "select * from itemtable where itemID="+selectedId+";";
							Statement statement = connection.createStatement();
							ResultSet rs = statement.executeQuery(sql1);
							while(rs.next()) {
								id = rs.getInt(1);
								name = rs.getString(2);
								price = rs.getString(3);
								catagory = rs.getString(4);
								description = rs.getString(5);
								status = rs.getString(6);
							}
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    
						new Update(ProductList.this,selectedId,name,price,catagory,description,status);
						break; // Break after finding the first selected item
					}
				}
            
            
				if (!itemSelected) {
					// If no item is selected, show a message dialog
					JOptionPane.showMessageDialog(ProductList.this, "Please select an item to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
				}
			} 
			else if (e.getSource() == add) {
				//System.out.println("Add button clicked");
				Add addDialog = new Add(ProductList.this);
				addDialog.setBounds(1070, 20, 400, 300);
			}
			else if(e.getSource() == remove) {
				boolean itemSelected = false; 
				// Loop through the table to find the selected rows
				for (int i = 0; i < table.getRowCount(); i++) {
					Boolean isSelected = (Boolean) table.getValueAt(i, 0); // Check if the checkbox is selected

					if (isSelected != null && isSelected) {
						itemSelected = true;
						selectedId = (Integer) table.getValueAt(i, 1); // Get the ID from the selected row
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
							//System.out.println("connected and selectId"+selectedId);
							String sql2 = "delete from itemtable where itemID = "+selectedId+";";
							PreparedStatement prestatement = connection.prepareStatement(sql2);
							prestatement.executeUpdate();
							connection.close();
                      
							JOptionPane.showMessageDialog(null, "Removed successfully!");
                      
                
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break; // Break after finding the first selected item
					}
				}
          
				if (!itemSelected) {
					// If no item is selected, show a message dialog
					JOptionPane.showMessageDialog(ProductList.this, "Please select an item to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
				}       
			}
		}
	}
	class CustomTableCellRenderer extends DefaultTableCellRenderer 
	{ 
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
		{
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Set custom background color for odd rows
			if (!isSelected) { // Only apply when the row is not selected
				if (row % 2 == 1) {
					c.setBackground(new Color(249, 245, 240));
				} 
				else {
					c.setBackground(Color.white); // White for even rows
				}
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


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ProductList();
	}
}