package FoodieFly;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Update extends JFrame {
    JLabel IdLabel, nameLabel, priceLabel,descriptionLabel,catLabel,statusLabel;
    JTextField tid,tname, tprice, tcat,tstatus;
    JTextArea tdes;
    JButton updateBtn, cancelBtn;
    JPanel p, p1, p2;
    private JFrame productFrame;
    private int ID;
    private String name,price,catagory,description,status;
    
    // Updated constructor
    public Update(JFrame productFrame, int ID,String name,String price,String catagory,String description,String status) {
        this.productFrame = productFrame;
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.catagory = catagory;
        this.description = description;
        this.status = status;
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

        p1 = new JPanel(new GridLayout(6, 2, 5, 5));
        p1.setOpaque(false);
        
        IdLabel = new JLabel("ID");
        IdLabel.setForeground(Color.DARK_GRAY);
        IdLabel.setFont(new Font("Arial",Font.BOLD,14));
        tid = new JTextField(String.valueOf(ID));
        tid.setOpaque(false);
        tid.setBorder(null);
        
        nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.DARK_GRAY);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tname = new JTextField(name);
        tname.setOpaque(false);
        tname.setBorder(roundedBorder);

        priceLabel = new JLabel("Price");
        priceLabel.setForeground(Color.DARK_GRAY);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tprice= new JTextField(price);
        tprice.setOpaque(false);
        tprice.setBorder(roundedBorder);
        
        descriptionLabel = new JLabel("Description");
        descriptionLabel.setForeground(Color.DARK_GRAY);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tdes= new JTextArea(description);
        tdes.setOpaque(false);
        tdes.setBorder(roundedBorder);
        
        catLabel = new JLabel("Category");
        catLabel.setForeground(Color.DARK_GRAY);
        catLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tcat= new JTextField(catagory);
        tcat.setOpaque(false);
        tcat.setBorder(roundedBorder);
        
        statusLabel = new JLabel("Status");
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tstatus= new JTextField(status);
        tstatus.setOpaque(false);
        tstatus.setBorder(roundedBorder);

        p1.add(IdLabel);
        p1.add(tid);
        p1.add(nameLabel);
        p1.add(tname);
        p1.add(priceLabel);
        p1.add(tprice);
        p1.add(descriptionLabel);
        p1.add(tdes);
        p1.add(catLabel);
        p1.add(tcat);
        p1.add(statusLabel);
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

        updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        updateBtn.setOpaque(false);
        updateBtn.setBorder(roundedBorder);
        updateBtn.setBackground(new Color(0, 0, 0, 0));
        updateBtn.setPreferredSize(new Dimension(80, 30));
        updateBtn.setForeground(Color.DARK_GRAY);
        p2.add(updateBtn);

        p.add(p1, BorderLayout.CENTER);
        p.add(p2, BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(p);

        // Initialize the ButtonListener with this UserInfo instance
        ButtonListener buttonListener = new ButtonListener(productFrame,this);
        cancelBtn.addActionListener(buttonListener);
        updateBtn.addActionListener(buttonListener);

        setTitle("Update item");
        setSize(400, 300);
        setLocation(1066,5);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only user info frame
        setVisible(true);
    }

    class ButtonListener implements ActionListener {
        private JFrame productFrame;
        private JFrame updateFrame;

        ButtonListener(JFrame productFrame, JFrame updateFrame) {
            this.productFrame = productFrame;
            this.updateFrame = updateFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cancelBtn) {
                if (productFrame != null) {
                    productFrame.dispose(); // Close the main frame
                }
                if (updateFrame != null) {
                    updateFrame.dispose(); // Close the user info frame
                }
               
            } else if (e.getSource() == updateBtn) {
              //String id = tid.getText();
              String name = tname.getText();
                String price = tprice.getText();
                String des = tdes.getText();
                String cat = tcat.getText();
                String status = tstatus.getText();
               
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
                    String sql = "Update itemtable set itemName = ?, itemPrice = ?, Catagory = ?,description=?,status=? where itemID=?";
                    PreparedStatement pstatement = connection.prepareStatement(sql);
                    pstatement.setString(1, name);
                    pstatement.setString(2, price);
                    pstatement.setString(3, cat);
                    pstatement.setString(4, des);
                    pstatement.setString(5, status);
                    pstatement.setInt(6, ID);
                    pstatement.executeUpdate();
                    connection.close();

                    JOptionPane.showMessageDialog(null, "Your information has been updated successfully!");
                    dispose();
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while adding information.");
                }
            }
        }
    }

//    public static void main(String[] args) {
//        // Example usage; replace with actual frame reference
//        JFrame updateFrame = new JFrame("Update Frame");
//        new Update(productFrame,ID); 
//    }
}