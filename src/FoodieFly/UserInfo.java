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

public class UserInfo extends JFrame {
    JLabel phNo, address;
    JTextField tph, tadd;
    JButton logoutBtn, addBtn;
    JPanel p, p1, p2;
    private JFrame mainFrame;
    private String username;

    // Updated constructor
    UserInfo(JFrame mainFrame, String username) {
        this.mainFrame = mainFrame;
        this.username = username;

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

        p1 = new JPanel(new GridLayout(2, 1, 5, 5));
        p1.setOpaque(false);

        phNo = new JLabel("Phone Number");
        phNo.setForeground(Color.DARK_GRAY);
        phNo.setFont(new Font("Arial", Font.BOLD, 15));
        tph = new JTextField();
        tph.setOpaque(false);
        tph.setBorder(roundedBorder);

        address = new JLabel("Address");
        address.setForeground(Color.DARK_GRAY);
        address.setFont(new Font("Arial", Font.BOLD, 15));
        tadd = new JTextField();
        tadd.setOpaque(false);
        tadd.setBorder(roundedBorder);

        p1.add(phNo);
        p1.add(tph);
        p1.add(address);
        p1.add(tadd);
        p1.setBorder(new EmptyBorder(0, 0, 10, 0));

        p2 = new JPanel(new FlowLayout());
        p2.setOpaque(false);

        addBtn = new JButton("Add");
        addBtn.setFont(new Font("Arial", Font.BOLD, 15));
        addBtn.setOpaque(false);
        addBtn.setBorder(roundedBorder);
        addBtn.setBackground(new Color(0, 0, 0, 0));
        addBtn.setPreferredSize(new Dimension(100, 30));
        addBtn.setForeground(Color.DARK_GRAY);
        p2.add(addBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 15));
        logoutBtn.setOpaque(false);
        logoutBtn.setBorder(roundedBorder);
        logoutBtn.setBackground(new Color(0, 0, 0, 0));
        logoutBtn.setPreferredSize(new Dimension(100, 30));
        logoutBtn.setForeground(Color.DARK_GRAY);
        p2.add(logoutBtn);

        p.add(p1, BorderLayout.CENTER);
        p.add(p2, BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(p);

        // Initialize the ButtonListener with this UserInfo instance
        ButtonListener buttonListener = new ButtonListener(mainFrame, this, username);
        logoutBtn.addActionListener(buttonListener);
        addBtn.addActionListener(buttonListener);

        setTitle("User Info");
        setSize(414, 200);
        setLocation(1054,37);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only UserInfo frame
        setVisible(true);
    }

    class ButtonListener implements ActionListener {
        private JFrame mainFrame;
        private JFrame userInfoFrame;	//is this necessary
        private String username;

        ButtonListener(JFrame mainFrame, JFrame userInfoFrame,String username) {
            this.mainFrame = mainFrame;
            this.userInfoFrame = userInfoFrame;
            this.username = username;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logoutBtn) {
                if (mainFrame != null) {
                    mainFrame.dispose(); // Close the main frame
                }
                if (userInfoFrame != null) {
                    userInfoFrame.dispose(); // Close the user info frame
                }
                new LoginForm(); // Open the login form
            } else if (e.getSource() == addBtn) {
                String phno = tph.getText();
                String add = tadd.getText();
                if(phno.isBlank()||add.isBlank()) {
                	JOptionPane.showMessageDialog(null, "Please input first");
                }
                else{
                	try {
                
                		Class.forName("com.mysql.cj.jdbc.Driver");
                    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
                    	String sql = "Update loginTable set PhoneNumber = ?, Address = ? where Name=?";
                    	PreparedStatement pstatement = connection.prepareStatement(sql);
                    	pstatement.setString(1, phno);
                    	pstatement.setString(2, add);
                    	pstatement.setString(3,username);
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
        JFrame mainFrame = new JFrame("Main Frame"); // Placeholder for your actual MainFrame
        new UserInfo(mainFrame, "username"); // Pass the main frame and username
    }
}