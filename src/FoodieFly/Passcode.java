package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Passcode extends JFrame{
 	JPanel p,p1,p2;
 	JLabel l1,l2;
 	JPasswordField pwd;
 	JButton login;
 	
 	String name;
    String pass;
    String email;
    String userType;
  
 	public Passcode(String name, String pass, String email, String userType){
 		this.name = name;
        this.pass = pass;
        this.email = email;
        this.userType = userType;
 		
 		// Load the background image
        ImageIcon backgroundImage = new ImageIcon("images/infobg.jpg");

        // Create a custom panel with a background image
        p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    
	    l1 = new JLabel("Enter Passcode First");
	    l1.setFont(new Font("Arial",Font.BOLD,13));
	    l1.setBorder(new EmptyBorder(10,10,10,10));
    
	    RoundedBorder roundedBorder = new RoundedBorder(15,Color.LIGHT_GRAY);
	    p1 = new JPanel(new GridLayout(1,2));
	    p1.setOpaque(false);
    
	    l2 = new JLabel("Passcode");
	    l2.setFont(new Font("Arial",Font.BOLD,15));
	    
	    pwd = new JPasswordField();
	    pwd.setFont(new Font("Arial",Font.BOLD,15));
	    pwd.setPreferredSize(new Dimension(80,20));
	    pwd.setOpaque(false);
        pwd.setBorder(roundedBorder);
        
	    p1.setBorder(new EmptyBorder(10,10,10,10));
	    p1.add(l2);
	    p1.add(pwd);
    
	    p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    p2.setOpaque(false);
	    login = new JButton("Login");
	    login.setOpaque(false);
        login.setBorder(roundedBorder);
        login.setBackground(new Color(0,0,0,0));
        login.setPreferredSize(new Dimension(80,30));
        login.setFont(new Font("Arial",Font.BOLD,15));
        login.addActionListener(new ButtonListener());
        
	    p2.add(login);
	    p2.setBorder(new EmptyBorder(10,10,10,10));
	    
	    p.add(l1,BorderLayout.NORTH);
	    p.add(p1,BorderLayout.CENTER);
	    p.add(p2,BorderLayout.SOUTH);
	    
	    add(p);

	    setSize(300,200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
 	}
 	
 	class ButtonListener implements ActionListener{
    @Override
    	public void actionPerformed(ActionEvent e) {
    		if(e.getSource() == login) {
    			String pass = new String(pwd.getPassword());
    			String passCode = "nopasscode";
        
    			if(pass.isBlank()) {
    				JOptionPane.showMessageDialog(null, "You must input first!");
    			}
        
    			else if(pass.equals(passCode)) {  								
    				insertAdminData();
    				JOptionPane.showMessageDialog(null, "Sign_Up successful!");
    				new LoginForm();
    				dispose();
    				
    			}  
    			else {
    				JOptionPane.showMessageDialog(null, "Incorrect Passcode!");
    				pwd.setText("");
    			}
    		}
      
    	}
 	}
 	private void insertAdminData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");

            if (connection != null) {
                String sql = "insert into loginTable(Name,Password,Email,userType) values (?,?,?,?)";
                PreparedStatement pstatement = connection.prepareStatement(sql);
                pstatement.setString(1, name);
                pstatement.setString(2, pass);
                pstatement.setString(3, email);
                pstatement.setString(4, userType);
                pstatement.executeUpdate();
                connection.close();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to connect to the database");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
          } catch (SQLException e) {
        	  JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        	  e.printStackTrace();
          	}
 	}	
}
