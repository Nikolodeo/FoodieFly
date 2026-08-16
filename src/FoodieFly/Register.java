package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame{
  JLabel titleLabel,label1,label2,label3,label4;
  JTextField temail,tname;
  JPasswordField pwd,cpwd;
   JButton btnSignUp,btnCancel;
   JPanel backgroundPanel,p1,p2,p3;
   JRadioButton user,admin;
  
   public Register() {
     // Load the background image
     ImageIcon backgroundImage = new ImageIcon("images/loginBackground.jpg");
    
     // Create a custom panel with a background image
     backgroundPanel = new JPanel(new BorderLayout()) {
       protected void paintComponent (Graphics g){
         super.paintComponent(g);
         g.drawImage(backgroundImage.getImage(),0 , 0 , getWidth(), getHeight(), this);
       }
     };
    
      titleLabel = new JLabel("Sign-Up");
      titleLabel.setForeground(Color.black);
      titleLabel.setFont(new Font("Arial",Font.BOLD,24));
      titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      titleLabel.setBorder(new EmptyBorder(10,0,30,0));
    
      p1 = new JPanel(new GridLayout(10,1,5,5)); // 8 rows, 1 column, spacing of 5 pixels
      p1.setOpaque(false);
    
      // Create a custom rounded border
        RoundedBorder roundedBorder = new RoundedBorder(15, Color.LIGHT_GRAY);

        label1 = new JLabel("Email Address");
        label1.setFont(new Font("Arial",Font.BOLD,16));
        
        temail = new JTextField();
        temail.setOpaque(false);
        temail.setBorder(roundedBorder);
        temail.setPreferredSize(new Dimension(100,25));

        label2 = new JLabel("User Name");
        label2.setFont(new Font("Arial",Font.BOLD,16));
        
        tname = new JTextField();
        tname.setOpaque(false);
        tname.setBorder(roundedBorder);
        tname.setPreferredSize(new Dimension(100,25));

        label3 = new JLabel("Password");
        label3.setFont(new Font("Arial",Font.BOLD,16));
        
        pwd = new JPasswordField();
        pwd.setOpaque(false);
        pwd.setBorder(roundedBorder);
        pwd.setPreferredSize(new Dimension(100,25));
        label4 = new JLabel("Confirm Password");
        label4.setFont(new Font("Arial",Font.BOLD,16));
        
        cpwd = new JPasswordField();
        cpwd.setOpaque(false);
        cpwd.setBorder(roundedBorder);
        cpwd.setPreferredSize(new Dimension(100,25));
        
        user = new JRadioButton("Customer");
      user.setFont(new Font("Arial",Font.PLAIN,15));
      user.setOpaque(false);
      user.setBorder(roundedBorder);
      user.setPreferredSize(new Dimension(80,25));
      user.setBackground(new Color(0,0,0,0));
      
      admin = new JRadioButton("Admin");
      admin.setFont(new Font("Arial",Font.PLAIN,15));
      admin.setOpaque(false);
      admin.setBorder(roundedBorder);
      admin.setPreferredSize(new Dimension(80,25));
      admin.setBackground(new Color(0,0,0,0));
    
      ButtonGroup g = new ButtonGroup();
      g.add(user);
      g.add(admin);
      
      p1.add(label1);
      p1.add(temail);
      p1.add(label2);
      p1.add(tname);
      p1.add(label3);
      p1.add(pwd);
      p1.add(label4);
      p1.add(cpwd);
      p1.add(user);
      p1.add(admin);
    
      p2 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
      p2.setOpaque(false);
      
      btnSignUp = new JButton("Sign Up");
      btnSignUp.setFont(new Font("Arial",Font.BOLD,16));
      btnSignUp.setPreferredSize(new Dimension(130,40));
      btnSignUp.setOpaque(false);
      btnSignUp.setBorder(roundedBorder);
      btnSignUp.setBackground(new Color(0,0,0,0));
      
      btnCancel = new JButton("Cancel");
      btnCancel.setFont(new Font("Arial",Font.BOLD,16));
      btnCancel.setPreferredSize(new Dimension(130,40));
      btnCancel.setOpaque(false);
      btnCancel.setBorder(roundedBorder);
      btnCancel.setBackground(new Color(0,0,0,0));
    
      p2.add(btnSignUp);
      p2.add(Box.createHorizontalStrut(20));
      p2.add(btnCancel);
      p2.setBorder(new EmptyBorder(15,0,15,0));
      backgroundPanel.add(titleLabel,BorderLayout.NORTH);
      backgroundPanel.add(p1,BorderLayout.CENTER);
      backgroundPanel.add(p2,BorderLayout.SOUTH);
    
      // Add space around the panel (20 pixels on all sides)
        backgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(backgroundPanel);
        
        btnSignUp.addActionListener(new ButtonListener());
        btnCancel.addActionListener(new ButtonListener());
        
        setTitle("Sign Up");
        setSize(400,650);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
      
    class ButtonListener implements ActionListener{  
        public String getState() {
          if(user.isSelected()) {
            return "user";
          }
          return "admin";   
        }
        @Override
        public void actionPerformed(ActionEvent e) {
          String email = temail.getText();
            String name = tname.getText();
            String pass = pwd.getText();
            String confirmPass = new String(cpwd.getText());
            
            if(e.getSource() == btnCancel) {
              new LoginForm();
              dispose();
            }
            else if(e.getSource() == btnSignUp) {
            
              if(email.isBlank() || name.isBlank() || pass.isBlank() || confirmPass.isBlank())
                JOptionPane.showMessageDialog(null, "You must input first!");
              else if(pass.length() < 6) {
            	  	JOptionPane.showMessageDialog(null, "Password must me at least 6 letters!");
            	  	pwd.setText(null);
            	  	cpwd.setText(null);
              }
              else if(!(pass.equals(confirmPass))) {
                JOptionPane.showMessageDialog(null, "Password must be the same");
                cpwd.setText(null);
              }
              else if(!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address!");
                temail.setText(null);
              }
              
              else if(admin.isSelected()) {
                new Passcode(name, pass, email, getState());
                dispose();
              }
              else {          
                try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
                    
                    if(connection != null) {
                      String sql = "insert into loginTable(Name,Password,Email,userType) values (?,?,?,?)";
                      PreparedStatement pstatement = connection.prepareStatement(sql);
                      pstatement.setString(1, name);
                      pstatement.setString(2, pass);
                      pstatement.setString(3, email);
                      pstatement.setString(4, getState());
                      pstatement.executeUpdate();
                      connection.close(); 
                    
                      JOptionPane.showMessageDialog(null, "Sign_Up successful!");
                      new LoginForm();
                      dispose();
                    }
                    else
                      JOptionPane.showMessageDialog(null, "Failed to connect to the database");
                    
                } catch (ClassNotFoundException e1) {
                  JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + e1.getMessage());
                  e1.printStackTrace();
                } catch (SQLException e1) {
                  JOptionPane.showMessageDialog(null, "SQL Error: " + e1.getMessage());
                  e1.printStackTrace();
                }                         
              }
            }
        }
    }
    
    private boolean isValidEmail(String email) {
      String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"; 
      return email.matches(emailRegex);
    }
      
    public static void main(String[] args) {
        new Register();
    }
}