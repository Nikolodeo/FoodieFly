package FoodieFly;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Add extends JFrame {
    JLabel name, price,description, cat, status;
    JTextField tname, tprice, tcat, tstatus;
    JTextArea tdes;
    JButton addBtn, cancelBtn;
    JPanel p, p1, p2;
    private JFrame productFrame;
 

    // Updated constructor
    Add(JFrame productFrame) {
        this.productFrame = productFrame;

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

        RoundedBorder roundedBorder = new RoundedBorder(15, Color.LIGHT_GRAY);

        p1 = new JPanel(new GridLayout(5, 2, 5, 5));
        p1.setOpaque(false);

        name = new JLabel("Name");
        name.setForeground(Color.DARK_GRAY);
        name.setFont(new Font("Arial", Font.BOLD, 14));
        tname = new JTextField();
        tname.setOpaque(false);
        tname.setBorder(roundedBorder);

        price = new JLabel("Price");
        price.setForeground(Color.DARK_GRAY);
        price.setFont(new Font("Arial", Font.BOLD, 14));
        tprice= new JTextField();
        tprice.setOpaque(false);
        tprice.setBorder(roundedBorder);
        
        description = new JLabel("Description");
        description.setForeground(Color.DARK_GRAY);
        description.setFont(new Font("Arial", Font.BOLD, 14));
        tdes= new JTextArea();
        tdes.setOpaque(false);
        tdes.setBorder(roundedBorder);
        
        cat = new JLabel("Category");
        cat.setForeground(Color.DARK_GRAY);
        cat.setFont(new Font("Arial", Font.BOLD, 14));
        tcat= new JTextField();
        tcat.setOpaque(false);
        tcat.setBorder(roundedBorder);
        
        status = new JLabel("Status");
        status.setForeground(Color.DARK_GRAY);
        status.setFont(new Font("Arial", Font.BOLD, 14));
        tstatus= new JTextField();
        tstatus.setOpaque(false);
        tstatus.setBorder(roundedBorder);

        p1.add(name);
        p1.add(tname);
        p1.add(price);
        p1.add(tprice);
        p1.add(description);
        p1.add(tdes);
        p1.add(cat);
        p1.add(tcat);
        p1.add(status);
        p1.add(tstatus);
        p1.setBorder(new EmptyBorder(0, 0, 10, 0));

        p2 = new JPanel(new FlowLayout());
        p2.setOpaque(false);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setOpaque(false);
        cancelBtn.setBorder(roundedBorder);
        cancelBtn.setBackground(new Color(0, 0, 0, 0));
        cancelBtn.setPreferredSize(new Dimension(80, 30));
        cancelBtn.setForeground(Color.DARK_GRAY);
        p2.add(cancelBtn);

        addBtn = new JButton("add");
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.setOpaque(false);
        addBtn.setBorder(roundedBorder);
        addBtn.setBackground(new Color(0, 0, 0, 0));
        addBtn.setPreferredSize(new Dimension(80, 30));
        addBtn.setForeground(Color.DARK_GRAY);
        p2.add(addBtn);

        p.add(p1, BorderLayout.CENTER);
        p.add(p2, BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(p);

        // Initialize the ButtonListener with this UserInfo instance
        ButtonListener buttonListener = new ButtonListener(productFrame,this);
        cancelBtn.addActionListener(buttonListener);
        addBtn.addActionListener(buttonListener);
        setTitle("User Info");
        setSize(400, 300);
        setLocation(1066,5);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only user info frame
        setVisible(true);
    }

    class ButtonListener implements ActionListener {
        private JFrame productFrame;
        private JFrame addFrame;

        ButtonListener(JFrame productFrame, JFrame addFrame) {
            this.productFrame = productFrame;
            this.addFrame = addFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cancelBtn) {
                if (productFrame != null) {
                    productFrame.dispose(); // Close the main frame
                }
                if (addFrame != null) {
                    addFrame.dispose(); // Close the user info frame
                }
               
            } else if (e.getSource() == addBtn) {
                String name = tname.getText();
                String price = tprice.getText();
                String des = tdes.getText();
                String cat = tcat.getText();
                String status = tstatus.getText();
                
                if(name.isBlank() || price.isBlank() || des.isBlank() || cat.isBlank() || status.isBlank()) {
                	JOptionPane.showMessageDialog(null, "Please input first!");
                }
                else{
                	try {
                		Class.forName("com.mysql.cj.jdbc.Driver");
	                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
	                    String sql = "Insert into itemTable (itemName,itemPrice,Catagory,description,status) values (?,?,?,?,?)";
	                    PreparedStatement pstatement = connection.prepareStatement(sql);
	                    pstatement.setString(1, name);
	                    pstatement.setString(2, price);
	                    pstatement.setString(3, cat);
	                    pstatement.setString(4,des);
	                    pstatement.setString(5, status);
	                    pstatement.executeUpdate();
	                    connection.close();
	
	                    JOptionPane.showMessageDialog(null, "Your information has been added successfully!");
	                    dispose();
                	} catch (ClassNotFoundException | SQLException ex) {
                		ex.printStackTrace();
                		JOptionPane.showMessageDialog(null, "An error occurred while adding information.");
                	}
                }
            }
        }
    }

    public static void main(String[] args) {
        // Example usage; replace with actual frame reference
        JFrame productFrame = new JFrame("Product Frame"); // Placeholder for your actual MainFrame
        new Add(productFrame); // Pass the main frame and username
    }
}