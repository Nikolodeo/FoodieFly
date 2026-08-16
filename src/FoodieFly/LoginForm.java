package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.sql.*;

public class LoginForm extends JFrame{

	LoginForm(){
		// Create the frame
        setTitle("Login");
        setSize(400, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Custom panel with background image
        JPanel panel = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon icon = new ImageIcon("images/loginBackground.jpg"); // Specify your image path here
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        add(panel);

        //Load the custom font
        try{
        	Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/CassandraPersonalUseRegular-3BjG.ttf"));
        
        	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        	//Register the font
        	ge.registerFont(customFont);
        
        	//Welcome label
        	JLabel welcomeLabel = new JLabel("Welcome to");
        	welcomeLabel.setFont(customFont.deriveFont(22f));
        	welcomeLabel.setForeground(Color.BLACK);
        	welcomeLabel.setBounds(130, 10, 200, 50);
        	panel.add(welcomeLabel);
        } catch(FontFormatException | IOException e) {
        	e.printStackTrace();
        }
        
        // Logo label
        JLabel logoLabel = new JLabel(new ImageIcon("images/beecupcake2.png"));
        logoLabel.setBounds(100, 50, 200, 200);
        panel.add(logoLabel);
        
        //Create a custom rounded border
        RoundedBorder rb = new RoundedBorder(15, Color.LIGHT_GRAY);
        
        // Username field
        JTextField usernameField = new JTextField() {
			private String placeholder = "User Name";	//PlaceHolder special xd

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(Color.GRAY); // Placeholder color
                    int height = getHeight();
                    int textY = (height - g.getFontMetrics().getHeight())/2 + g.getFontMetrics().getAscent();
                    g.drawString(placeholder, getInsets().left, textY);
                }
            }
        };
        usernameField.setBounds(45, 270, 300, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        // Add a FocusListener to remove and restore placeholder text
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameField.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameField.repaint();
            }
        });
        usernameField.setBorder(rb);
        panel.add(usernameField);

        // Password field
        JPasswordField passwordField = new JPasswordField(10) {
			private String placeholder = "Password";	//PlaceHolder special again
        	
			@Override
        	protected void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		if(getPassword().length==0 && !isFocusOwner()) {
        			g.setColor(Color.GRAY);
        			int height = getHeight();
                    int textY = (height - g.getFontMetrics().getHeight())/2 + g.getFontMetrics().getAscent();
                    g.drawString(placeholder, getInsets().left, textY);
        		}
        	}
        };
        passwordField.setBounds(45, 320, 300, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        // Add a FocusListener to remove and restore placeholder text
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.repaint();
            }
        });
        passwordField.setBorder(rb);
        panel.add(passwordField);

        // Remember me checkbox
        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setBounds(50, 370, 150, 30);
        rememberMeCheckBox.setForeground(Color.BLACK);
        rememberMeCheckBox.setOpaque(false);  // Make checkbox transparent
        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(rememberMeCheckBox);

        // Forgot password label
        JLabel forgotPasswordLabel = new JLabel("Forgot password?");
        forgotPasswordLabel.setBounds(220, 370, 130, 30);
        forgotPasswordLabel.setForeground(Color.BLACK);
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand icon
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Define the action to be performed when the label is clicked
                JOptionPane.showMessageDialog(null, "Redirecting to password reset...", "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
                // You can replace this with navigation to another window or other logic
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                forgotPasswordLabel.setForeground(Color.RED); // Change text color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                forgotPasswordLabel.setForeground(Color.BLACK); // Revert text color when not hovered
            }
        });
        panel.add(forgotPasswordLabel);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(45, 420, 300, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setForeground(Color.BLACK);
        loginButton.setOpaque(false);	//Make login button transparent
        loginButton.setBorder(rb);
        loginButton.setBackground(new Color(0,0,0,0));
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String name = usernameField.getText();
        		String pwd = new String(passwordField.getPassword());
        		if(e.getSource()==loginButton) {
        			// Check if either the user name or password fields are empty
                    if (name.isEmpty() && pwd.isEmpty()) 
                        JOptionPane.showMessageDialog(null, "Please enter your info!");
                    
                    else {
                    	try{	//check validation from database table
                    		Class.forName("com.mysql.cj.jdbc.Driver");
                    		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly","root","Databasethinml123");
                    		Statement statement = connection.createStatement();
                    		String sql = "select * from loginTable";
                    		ResultSet rs = statement.executeQuery(sql);
                    		boolean valid = false;
                    		while(rs.next()) {
                    			if(name.equals(rs.getString(2)) && (pwd.equals(rs.getString(3)))){
                    				if(rs.getString(7).equals("user")) {
                    					new MainFrame(name);
                    					valid = true;
                    					dispose();
                    					break;
                    				}
                    				else if(rs.getString(7).equals("admin")) {
                    					new AdminFrame(name);
                    					valid = true;
                    					dispose();
                    					break;
                    				}
                    			}
                    		}
                    		if(!valid)
                    			JOptionPane.showMessageDialog(null,"Invalid Username or Password");          
                    		connection.close();
                    	} catch(Exception de) {
                    		System.out.println("Database Error");
                    	  }	
                    }	
        		}
        	}
        });

        // New user? label
        JLabel signUpLabel = new JLabel("Don't have an account? ");
        signUpLabel.setBounds(100, 510, 200, 30);
        signUpLabel.setForeground(Color.BLACK); // Ensure this color contrasts with your background image
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(signUpLabel);
        
        // Sign up button
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(45, 550, 300, 40);
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        signupButton.setForeground(Color.BLACK);
        signupButton.setOpaque(false);
        signupButton.setBorder(rb);
        signupButton.setBackground(new Color(0,0,0,0));
        panel.add(signupButton);
        signupButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==signupButton) {
        			new Register();	//go to the register frame
        			dispose();
        		}
        	}
        });
        
        // Display the frame
        setVisible(true);
    }
	
	public static void main(String[] args) {
		new LoginForm();
	}
}
