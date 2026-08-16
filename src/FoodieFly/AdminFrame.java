package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminFrame extends JFrame{
	JButton button1,button2,button3,button4,optionButton;
	JPanel mainPanel;
  
	AdminFrame(String adminname){  
		// Custom panel with background image
		mainPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Load the background image
				ImageIcon icon = new ImageIcon("images/adminbg.jpg"); // Specify your image path here
				Image image = icon.getImage();
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mainPanel.setLayout(null);            
    
		//Create a custom rounded border
		RoundedBorder rb = new RoundedBorder(15, Color.BLACK);
      
		// Logo label
		JPanel titlePanel = new JPanel(new BorderLayout());
		JLabel logoLabel = new JLabel(new ImageIcon("images/beecupcake2.png"));
		logoLabel.setPreferredSize(new Dimension(200, 150));
		titlePanel.add(logoLabel, BorderLayout.WEST);
		titlePanel.setBackground(new Color(249, 245, 236));

		// Create a new panel for admin info 
		JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Aligns components to the right
		userInfoPanel.setOpaque(false); // Make the panel transparent
		userInfoPanel.setBorder(BorderFactory.createEmptyBorder(30,0,0,15));
		  
		// admin Icon 
		ImageIcon userimage = new ImageIcon("images/adminicon.png");
		JLabel usericon = new JLabel(userimage) {		  
			@Override 
			protected void paintComponent(Graphics g) { 
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
				g2d.setColor(new Color(0xFFFFE0));
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
				g2d.setColor(Color.BLACK); g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); 
				Image img = userimage.getImage();
				g2d.drawImage(img, 5, 5, getWidth() - 10, getHeight() - 10, this);
				g2d.dispose(); 
			} 
		}; 
		
		usericon.setPreferredSize(new Dimension(50, 50));			
		usericon.setOpaque(false);
				
		// admin Info 
		JLabel userinfo = new JLabel(adminname);
		userinfo.setForeground(Color.BLACK); userinfo.setBorder(rb);
		userinfo.setPreferredSize(new Dimension(200,50));
		userinfo.setFont(new Font("Arial", Font.PLAIN, 18));
		  
		//Option Button 
		ImageIcon optionIcon = new ImageIcon("images/optionicon.png"); 
		optionButton = new JButton(optionIcon){
			@Override 
			protected void paintComponent(Graphics g) { // Cast Graphics to
				//Graphics2D 
				Graphics2D g2d = (Graphics2D) g.create();
		 
				// Set anti-aliasing for smoother edges
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		  
				// Draw the rounded background 
				g2d.setColor(new Color(0xFFFFE0));
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded rectangle with radius 20
		  
				// Draw the rounded border 
				g2d.setColor(Color.BLACK); 
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded border with radius 20
		  
				// Call super.paintComponent() AFTER custom painting to preserve clickability and image 
				g2d.setComposite(AlphaComposite.SrcOver); // Reset alpha composite to default 
				super.paintComponent(g2d); g2d.dispose(); 
			} 
		};
		
		//optionButton.setBounds(1300, 75, 30, 50); 
		optionButton.setPreferredSize(new Dimension(30,50)); 
		optionButton.setContentAreaFilled(false);
		optionButton.setFocusPainted(false); // Remove focus painting to maintain rounded look optionButton.setBorderPainted(false);
		 
		optionButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(e.getSource()==optionButton) { 
					new AdminInfo(AdminFrame.this, adminname); 
				} 
			} 
		});
		  
		// Add components to userInfoPanel 
		userInfoPanel.add(usericon);
		userInfoPanel.add(userinfo); 
		userInfoPanel.add(optionButton);
		 
		//Load the custom font
		try{
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/CassandraPersonalUseRegular-3BjG.ttf"));
      
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      
			//Register the font
			ge.registerFont(customFont);
      
			JLabel title = new JLabel("Admin");
			title.setFont(customFont.deriveFont(45f));
			title.setForeground(new Color(249, 212, 2));
			title.setHorizontalAlignment(SwingConstants.CENTER);
			titlePanel.add(title, BorderLayout.CENTER);
		} catch(FontFormatException | IOException e) {
			e.printStackTrace();
		  }

		// Add userInfoPanel to the titlePanel on the right
		titlePanel.add(userInfoPanel, BorderLayout.EAST);
      
		// Create a JPanel for the vertical menu
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); // Set vertical layout
		menuPanel.setBackground(new Color(249,245,236));
		menuPanel.setPreferredSize(new Dimension(200,800));
		menuPanel.setBorder(new EmptyBorder(20,20,20,20));

		// Add buttons to the menu panel
		button1 = createMenuButton("Product List");
		button2 = createMenuButton("Order List");
		button3 = createMenuButton("User List");
		button4 = createMenuButton("Customer Feedback");
      
		menuPanel.add(button1);
		menuPanel.add(button2);
		menuPanel.add(button3);
		menuPanel.add(button4);

		// Add the menu panel to the frame on the left side
		add(titlePanel, BorderLayout.NORTH);
		add(menuPanel, BorderLayout.WEST);
		add(mainPanel, BorderLayout.CENTER);
      
		ProductList product = new ProductList();
		JPanel productPanel;
		try {
			productPanel = product.createproductPanel();
			productPanel.setBounds(0,0,1200,620);
			mainPanel.add(productPanel);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		  }
    
		setTitle("FoodieFly");
		setSize(1413,800);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	// Helper method to create a menu button with hover and click effects
	private JButton createMenuButton(String text) {
		JButton button = new JButton(text);
     	button.setFont(new Font("Arial", Font.BOLD, 16));
     	button.setHorizontalAlignment(SwingConstants.CENTER);
     	button.setOpaque(false);
     	button.setPreferredSize(new Dimension(100,100));
     	button.setBackground(new Color(0,0,0,0));
     	//button.setBorder(null);
     	button.setBorder(new EmptyBorder(30, 0, 30, 0));
     	button.setFocusPainted(false);

     	// Hover effect
     	button.addMouseListener(new MouseAdapter() {
     		@Override
     		public void mouseEntered(MouseEvent e) {
     			button.setForeground(new Color(249, 212, 2));
     		}

     		@Override
     		public void mouseExited(MouseEvent e) {
     			button.setForeground(Color.BLACK);
     		}
     	});

     	// Click effect
     	button.addActionListener(new ButtonListener());

     	return button;
	}

  
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			mainPanel.removeAll();
			// TODO Auto-generated method stub
			if(e.getSource() == button3) {
				// Instantiate UserList and add the panel
				try {
					UserList user = new UserList();
					JPanel userPanel = user.createuserPanel();
					userPanel.setBounds(0,0,1200,615);
					mainPanel.add(userPanel);
					mainPanel.revalidate();
					mainPanel.repaint();
	            
				} catch (ClassNotFoundException | SQLException e1) {
	        // TODO Auto-generated catch block
					e1.printStackTrace();
				  }  
			}
			else if(e.getSource() == button2) {
				try{
					OrderList order = new OrderList();			
					JPanel orderPanel = order.createorderPanel();
					orderPanel.setBounds(0,0,1200,613);
					mainPanel.add(orderPanel);
					mainPanel.revalidate();
					mainPanel.repaint();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  }
			}
			else if(e.getSource() == button1) {
				ProductList product = new ProductList();
				JPanel productPanel;
				try {
					productPanel = product.createproductPanel();
					productPanel.setBounds(0,0,1200,610);
					mainPanel.add(productPanel);
					mainPanel.revalidate();
					mainPanel.repaint();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  }
			}
			else if(e.getSource() == button4) {
				FeedbackList feedback = new FeedbackList();
				JPanel feedbackPanel;
				try {
					feedbackPanel = feedback.createfeedbackPanel();
					feedbackPanel.setBounds(0, 0, 1200, 620);					
					JScrollPane sp = new JScrollPane(feedbackPanel);
					sp.setBounds(0, 0, 1200, 620);
					sp.getViewport().setBackground(Color.white);
					mainPanel.add(sp);
					//mainPanel.add(feedbackPanel);				
					mainPanel.revalidate();
					mainPanel.repaint();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		String plchd = "Admin Name Here";
	    new AdminFrame(plchd);
	}
}