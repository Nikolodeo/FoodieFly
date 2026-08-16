package FoodieFly;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class UserList extends JFrame{

	JPanel userpanel,p1;
	Object[][] data;
	String name,email,phno,address,userType;
  
	public JPanel createuserPanel() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly","root","Databasethinml123");
	    ///System.out.println("connected");
	    String sql = "select * from logintable";
	    Statement statement = connection.createStatement();
	    ResultSet rs = statement.executeQuery(sql);
	    
	    List<Object[]> rowList = new ArrayList<>();
	    while(rs.next()) {
	    	name = rs.getString(2);
	    	email =rs.getString(4);
	    	phno = rs.getString(5);
	    	address =rs.getString(6);
	    	userType =rs.getString(7);
	    	rowList.add(new Object[]{name,email,phno,address,userType});
	    }
	    data = rowList.toArray(new Object[0][]);
    
	    userpanel = new JPanel(new BorderLayout());
	    p1 = new JPanel(new BorderLayout());
	    String[] columnNames = {"Name","Email","Phone Number","Address","User Type"};
    
	    JTable table = new JTable(data, columnNames);
	    table.setRowHeight(60);
	    table.setFont(new Font("Arial",Font.PLAIN,16));
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
        
        //Apply the custom renderer
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
        p1.setPreferredSize(new Dimension(400,480));
        userpanel.add(p1,BorderLayout.CENTER);
  
        return userpanel;
	}

	class CustomTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			// Set custom background color for odd rows
			if (!isSelected) { // Only apply when the row is not selected
				if (row % 2 == 1) {
					c.setBackground(new Color(249, 245, 240)); // Light gray for odd rows
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
		new UserList();
	}
}