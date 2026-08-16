package FoodieFly;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CategorySlidePanel extends JPanel {
	private String name,price,description,status;
	private List<String[]> cartItems;
	
    public CategorySlidePanel(List<String[]> cartItems) {
        this.cartItems = cartItems;
    	setBounds(200, 200, 1200, 562); // Set the bounds for the slide panel
        setLayout(new GridLayout(2, 4, 5, 5)); // 2 rows, 4 columns, 5px spacing
        setOpaque(false);
    }
    
    public void loadItems(String category) {
    	// Clear the current items
        removeAll();

        // Determine the items based on the category
        List<String[]> itemTexts = new ArrayList<>();
        List<String> imagePaths = new ArrayList<>();
        
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly","root","Databasethinml123");
        
	        switch (category) {
	        	case "CAKE":          
	                String sql1 = "select * from itemtable where Catagory = 'cake'";
	                Statement statement1 = connection.createStatement();
	                ResultSet rs1 = statement1.executeQuery(sql1);
	                
	                while(rs1.next()) {
	                	//id = rs.getInt(1);
	                	name = rs1.getString(2);
	                	price =rs1.getString(3);
	                	rs1.getString(4);
	                	description =rs1.getString(5);
	                	status =rs1.getString(6);
	                	itemTexts.add(new String[]{name, price, description, status});
	                }
	              
	                imagePaths.add("images/chococake.jpg");
	                imagePaths.add("images/strawbcake.jpg");
	                imagePaths.add("images/cheesecake.jpg");
	                imagePaths.add("images/pancake.jpg");
	                imagePaths.add("images/waffle.jpg");
	                imagePaths.add("images/cupcake.jpg");
	                imagePaths.add("images/icecreamroll.jpg");
	                imagePaths.add("images/comingsoon.png");
	                break;
	                
	            case "COOKIE":
	            	String sql2 = "select * from itemtable where Catagory = 'cookie'";
	                Statement statement2 = connection.createStatement();
	                ResultSet rs2 = statement2.executeQuery(sql2);
	                while(rs2.next()) {
	                	//id = rs.getInt(1);
	                	name = rs2.getString(2);
	                	price =rs2.getString(3);
	                	rs2.getString(4);
	                	description =rs2.getString(5);
	                	status =rs2.getString(6);
	                	itemTexts.add(new String[]{name, price, description, status});
	                }
	                
	                // Add more cookies as needed
	                imagePaths.add("images/chocochip.jpg");
	                imagePaths.add("images/pineapplecookie.jpg");
	                imagePaths.add("images/gingerbread.jpg");
	                imagePaths.add("images/p&bcookie.jpg");
	                imagePaths.add("images/almondcookie.jpg");
	                imagePaths.add("images/macaron.jpg");
	                imagePaths.add("images/m&mcookie.jpg");
	                imagePaths.add("images/lemonbars.jpg");
	
	                break;
	                
	            case "BREAD":
	            	String sql3 = "select * from itemtable where Catagory = 'bread'";
	                Statement statement3 = connection.createStatement();
	                ResultSet rs3 = statement3.executeQuery(sql3);
	                
	                while(rs3.next()) {
	                	//id = rs.getInt(1);
	                	name = rs3.getString(2);
	                	price =rs3.getString(3);
	                	rs3.getString(4);
	                	description =rs3.getString(5);
	                	status =rs3.getString(6);
	                	itemTexts.add(new String[]{name, price, description, status});
	                }
	              
	                imagePaths.add("images/frenchtoast.jpg");
	                imagePaths.add("images/croissant.jpg");
	                imagePaths.add("images/garlicbread.jpg");
	                imagePaths.add("images/sesamebagel.jpg");
	                imagePaths.add("images/bananabread.jpg");
	                imagePaths.add("images/pretzel.jpg");
	                imagePaths.add("images/sandwich.jpg");
	                imagePaths.add("images/comingsoon.png");
	                break;
	                
	            case "DRINK":
	            	String sql4 = "select * from itemtable where Catagory = 'drink'";
	                Statement statement4 = connection.createStatement();
	                ResultSet rs4 = statement4.executeQuery(sql4);
	                
	                while(rs4.next()) {
	                	//id = rs.getInt(1);
	                	name = rs4.getString(2);
	                	price =rs4.getString(3);
	                	rs4.getString(4);
	                	description =rs4.getString(5);
	                	status =rs4.getString(6);
	                	itemTexts.add(new String[]{name, price, description, status});
	                }
	              
	                imagePaths.add("images/latte.jpg");
	                imagePaths.add("images/cappuccino.jpg");
	                imagePaths.add("images/americano.jpg");
	                imagePaths.add("images/espresso.jpg");
	                imagePaths.add("images/chaitea.jpg");
	                imagePaths.add("images/hotchocolate.jpg");
	                imagePaths.add("images/pepsi.jpg");
	                imagePaths.add("images/water.jpg");
	                break;	              	         
	            }

	            // Add the new items to the panel
	            for (int i = 0; i < itemTexts.size(); i++) {
	                add(createItemPanel(imagePaths.get(i), itemTexts.get(i)));
	            }
        } catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + e1.getMessage());
			e1.printStackTrace();
	      } catch (SQLException e1) {
	    	  JOptionPane.showMessageDialog(null, "SQL Error: " + e1.getMessage());
	    	  e1.printStackTrace();
			}
        // Refresh the panel
        revalidate();
        repaint();
	}
    
    private JPanel createItemPanel(String imagePath, String[] texts) {
        
    	JPanel itemPanel = new JPanel();
        itemPanel.setLayout(null); // Use absolute positioning for elements within the item
        itemPanel.setPreferredSize(new Dimension(300, 280));
        //itemPanel.setBorder(new LineBorder(Color.BLACK, 5)); // Thick border of 5 pixels

        // Create and add the image
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 200, 200);
        imageLabel.setIcon(new ImageIcon(imagePath)); // Use the image path provided
        itemPanel.add(imageLabel);

        // Create and add the text fields
        //create name text field
        JTextArea nameText = new JTextArea(texts[0]);
        nameText.setBounds(200, 0, 100, 50);
        nameText.setLineWrap(true); // Enable line wrapping
        nameText.setWrapStyleWord(true); // Wrap at word boundaries
        nameText.setFont(new Font("Arial", Font.BOLD, 13)); // Set the font
        nameText.setEditable(false); // Make it non-editable like a JTextField
        nameText.setWrapStyleWord(true);
        nameText.setMargin(new Insets(10, 10, 10, 10)); 
        itemPanel.add(nameText);
        
        //create price text field
        JTextField priceField = new JTextField(texts[1]);
        priceField.setBounds(200, 50 * 1, 100, 50); // Position to the right of the image
        priceField.setFont(new Font("Arial", Font.BOLD, 13));
        priceField.setHorizontalAlignment(SwingConstants.CENTER);
        priceField.setEditable(false);
        itemPanel.add(priceField);
        
        //create description text field
        JTextArea dspText = new JTextArea(texts[2]);
        dspText.setBounds(200, 50 * 2, 100, 50);
        dspText.setLineWrap(true); // Enable line wrapping
        dspText.setWrapStyleWord(true); // Wrap at word boundaries
        dspText.setFont(new Font("Arial", Font.BOLD, 13)); // Set the font
        dspText.setEditable(false); // Make it non-editable like a JTextField
        dspText.setWrapStyleWord(true);
        dspText.setMargin(new Insets(10, 10, 10, 10)); 
        itemPanel.add(dspText);
        
        //create status text field
        JTextField lastTextField = new JTextField(texts[3]);
        lastTextField.setBounds(200, 50 * 3, 100, 50); // Position to the right of the image
        lastTextField.setFont(new Font("Arial", Font.BOLD, 13));
        lastTextField.setHorizontalAlignment(SwingConstants.CENTER);
        lastTextField.setEditable(false);
        itemPanel.add(lastTextField);


        // Text field button
        JTextField quantityField = new JTextField("1");
        quantityField.setHorizontalAlignment(SwingConstants.CENTER); // Center text
        quantityField.setEditable(false);
        quantityField.setFont(new Font("Arial", Font.BOLD, 15));
        quantityField.setBounds(100, 200, 100, 30);
        itemPanel.add(quantityField);
        
        // Plus button
        JButton plusButton = createIconButton("images/plus.png"); // Replace with your plus icon path
        plusButton.setBounds(0, 200, 100, 30);           
        itemPanel.add(plusButton);

        // Minus button
        JButton minusButton = createIconButton("images/minus.png"); // Replace with your minus icon path
        minusButton.setBounds(200, 200, 100, 30);      
        itemPanel.add(minusButton);

        // Create and add the atc button
        JButton largeButton = new JButton("Add to cart");
        largeButton.setFont(new Font("Arial", Font.BOLD, 16));
        largeButton.setBounds(0, 230, 300, 50); // Positioned at the bottom
        itemPanel.add(largeButton);
        
        // Check the content of the last text field and set background color accordingly
        String textContent = lastTextField.getText().toLowerCase();
        if (lastTextField != null) {       
            if (textContent.contains("now serving")) {
                lastTextField.setBackground(new Color(144, 238, 144));
                plusButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int currentValue = Integer.parseInt(quantityField.getText());
                        quantityField.setText(String.valueOf(currentValue + 1));
                    }
                });
                minusButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int currentValue = Integer.parseInt(quantityField.getText());
                        if (currentValue > 1) { // Prevent the value from going below 1
                            quantityField.setText(String.valueOf(currentValue - 1));
                        }
                    }
                });
                largeButton.addActionListener(new ActionListener() {
                	@Override
                    public void actionPerformed(ActionEvent e) {
                        String quantity = quantityField.getText();
                        String price = texts[1];  // Assuming the price is in the second text field
                        int totalPrice = Integer.parseInt(price.replaceAll("[^0-9]", "")) * Integer.parseInt(quantity);

                        String[] cartItem = new String[]{
                            texts[0],     // Product name
                            price,        // Price
                            quantity,     // Quantity from the quantityField
                            String.valueOf(totalPrice)  // Total price
                        };

                        cartItems.add(cartItem);
                        JOptionPane.showMessageDialog(null, texts[0] + " Added to the cart!");
                    }
                });
                
                
            } else if (textContent.contains("sold out")) {
                lastTextField.setBackground(new Color(211, 211, 211));
                
            }
        }

        return itemPanel;
    }
    
    private JButton createIconButton(String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0xFFFFE0)); // Set the background color
                g2d.fillRect(0, 0, getWidth(), getHeight()); // No rounded corners
                g2d.setColor(Color.BLACK); // Set the border color
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // No rounded corners
                super.paintComponent(g2d); // Draw the icon on top
                g2d.dispose();
            }
        };
        button.setContentAreaFilled(false); 
        button.setFocusPainted(false); 
        button.setBorderPainted(false);
        return button;
    }
     
}