package FoodieFly;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class MainFrame extends JFrame{
	private CategorySlidePanel categorySlidePanel;
	MainFrame(String username){
		//Create the frame
		setTitle("FoodieFly");
	    setSize(1415,800);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // Custom panel with background image
        JPanel panel = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon icon = new ImageIcon("images/userbg.jpg"); // Specify your image path here
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        add(panel);
        
        // Logo label
        JLabel logoLabel = new JLabel(new ImageIcon("images/beecupcake2.png"));
        logoLabel.setBounds(0, 0, 200, 200);
        panel.add(logoLabel);
        
        //Create a custom rounded border
        RoundedBorder rb = new RoundedBorder(15, Color.BLACK);
        
        // Create the search bar
        JTextField searchBar = new JTextField(20) {
        	private String placeholder = "Search";	//PlaceHolder special
			@Override
        	protected void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		if(getText().isEmpty() && !isFocusOwner()) {
        			g.setColor(Color.BLACK);
        			int height = getHeight();
                    int textY = (height - g.getFontMetrics().getHeight())/2 + g.getFontMetrics().getAscent();
                    g.drawString(placeholder, getInsets().left, textY);
        		}
        	}
        };
        searchBar.setBounds(500, 75, 300, 50); // Set position (x, y) and size (width, height)
        searchBar.setFont(new Font("Arial", Font.PLAIN, 18));
        searchBar.setOpaque(false);
        searchBar.setBackground(new Color(0,0,0,0));
        // Add a FocusListener to remove and restore placeholder text
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                searchBar.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                searchBar.repaint();
            }
        });
        searchBar.setBorder(rb);
        panel.add(searchBar);
        //Search button
        ImageIcon searchIcon = new ImageIcon("images/sicon.png");
        JButton imageButton = new JButton(searchIcon){
            @Override
            protected void paintComponent(Graphics g) {
                // Cast Graphics to Graphics2D
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
                super.paintComponent(g2d);

                g2d.dispose();
            }
        };
        imageButton.setBounds(800, 75, 50, 50);
        imageButton.setContentAreaFilled(false); 
        imageButton.setFocusPainted(false); // Remove focus painting to maintain rounded look
        imageButton.setBorderPainted(false);
        panel.add(imageButton);
        
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==imageButton) {
                	JOptionPane.showMessageDialog(null, "Future improvement xd");
                }
            }
        });
        
        //User Icon
        ImageIcon userimage = new ImageIcon("images/usericon.png");
        JLabel usericon = new JLabel(userimage) {
            @Override
            protected void paintComponent(Graphics g) {
            	// Cast Graphics to Graphics2D
                Graphics2D g2d = (Graphics2D) g.create();

                // Set anti-aliasing for smoother edges
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the rounded background
                g2d.setColor(new Color(0xFFFFE0));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded rectangle with radius 20

                // Draw the rounded border
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded border with radius 20

                // Scale and draw the image to fit within the rounded rectangle
                Image img = userimage.getImage();
                g2d.drawImage(img, 5, 5, getWidth() - 10, getHeight() - 10, this);

                // Dispose the graphics object
                g2d.dispose();
            }
        };
        usericon.setBounds(1050, 75, 50, 50);
        usericon.setOpaque(false);
        panel.add(usericon);
        
        //UserInfo
        JLabel userinfo = new JLabel(username);
        userinfo.setBounds(1100, 75, 200, 50);
        userinfo.setForeground(Color.BLACK);
        userinfo.setBorder(rb);
        userinfo.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(userinfo);
        
        //Option Button
        ImageIcon optionIcon = new ImageIcon("images/optionicon.png");
        JButton optionButton = new JButton(optionIcon){
            @Override
            protected void paintComponent(Graphics g) {
                // Cast Graphics to Graphics2D
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
                super.paintComponent(g2d);

                g2d.dispose();
            }
        };
        optionButton.setBounds(1300, 75, 30, 50);
        optionButton.setContentAreaFilled(false); 
        optionButton.setFocusPainted(false); // Remove focus painting to maintain rounded look
        optionButton.setBorderPainted(false);
        panel.add(optionButton);
        
        optionButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==optionButton) {
        			new UserInfo(MainFrame.this, username);
        		}
        	}
        });
        List<String[]> cartItems = new ArrayList<>();
        // Initialize the slide panel but keep it hidden initially
        categorySlidePanel = new CategorySlidePanel(cartItems);
        panel.add(categorySlidePanel);
        categorySlidePanel.setVisible(false);

        // Create the category panel and pass the listener
        Catagory catagory = new Catagory(this::showCategorySlide);
        JPanel catagoryPanel = catagory.createCatagoryPanel();
        catagoryPanel.setBounds(0, 220, 200, 500);
        panel.add(catagoryPanel);
        
        // feedBack button
        JButton feedBackBtn= new JButton("FeedBack");
        feedBackBtn.setBounds(0, 664, 200, 50);
        feedBackBtn.setFont(new Font("Arial", Font.BOLD, 18));
        feedBackBtn.setForeground(Color.BLACK);
        feedBackBtn.setOpaque(false);
        feedBackBtn.setBorder(rb);
        feedBackBtn.setBackground(new Color(0,0,0,0));
        panel.add(feedBackBtn);
        feedBackBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if(e.getSource()==feedBackBtn) {
              
                  FeedBack feedback = new FeedBack(username);
                  feedback.setBounds(60, 360, 400, 450);
            }
          }
        });
        
        // Cart button
        JButton cartButton = new JButton("View Cart");
        cartButton.setBounds(0, 713, 200, 50);
        cartButton.setFont(new Font("Arial", Font.BOLD, 18));
        cartButton.setForeground(Color.BLACK);
        cartButton.setOpaque(false);
        cartButton.setBorder(rb);
        cartButton.setBackground(new Color(0,0,0,0));
        panel.add(cartButton);
        cartButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==cartButton) {
        			
        	        new Cart1(cartItems,username);
        		}
        	}
        });
        
        //Display the frame
        setVisible(true);
	}
	private void showCategorySlide(String categoryName) {
        categorySlidePanel.setVisible(true);
        categorySlidePanel.loadItems(categoryName); 
        // categorySlidePanel.loadContent(categoryName);
    }
	
	public static void main(String[] args) {
		String plchd = "User Name Here";
		new MainFrame(plchd);
	}

}
