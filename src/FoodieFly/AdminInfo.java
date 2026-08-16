package FoodieFly;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminInfo extends JFrame {
    JLabel phNo, address,email;
    JTextField tph, tadd,temail;
    JButton logoutBtn, addBtn;
    JPanel p, p1, p2;
    private JFrame adminFrame;
    private String adminname;

    // Updated constructor
    AdminInfo(JFrame AdminFrame, String adminname) {
        this.adminFrame = AdminFrame;
        this.adminname = adminname;

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

        p1 = new JPanel(new GridLayout(3, 2, 5, 5));
        p1.setOpaque(false);

        email = new JLabel("Email");
        email.setForeground(Color.DARK_GRAY);
        email.setFont(new Font("Arial", Font.BOLD, 14));
        temail = new JTextField();
        temail.setOpaque(false);
        temail.setBorder(null);
        
        phNo = new JLabel("Phone Number");
        phNo.setForeground(Color.DARK_GRAY);
        phNo.setFont(new Font("Arial", Font.BOLD, 14));
        tph = new JTextField();
        tph.setOpaque(false);
        tph.setBorder(roundedBorder);

        address = new JLabel("Address");
        address.setForeground(Color.DARK_GRAY);
        address.setFont(new Font("Arial", Font.BOLD, 14));
        tadd = new JTextField();
        tadd.setOpaque(false);
        tadd.setBorder(roundedBorder);

        p1.add(email);
        p1.add(temail);
        p1.add(phNo);
        p1.add(tph);
        p1.add(address);
        p1.add(tadd);
        p1.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
            String sql = "select email from logintable where name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, adminname);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
              temail.setText(rs.getString("email"));
            }
            connection.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while adding information.");
        }


        p2 = new JPanel(new FlowLayout());
        p2.setOpaque(false);

        addBtn = new JButton("Add");
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.setOpaque(false);
        addBtn.setBorder(roundedBorder);
        addBtn.setBackground(new Color(0, 0, 0, 0));
        addBtn.setPreferredSize(new Dimension(80, 30));
        addBtn.setForeground(Color.DARK_GRAY);
        p2.add(addBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setOpaque(false);
        logoutBtn.setBorder(roundedBorder);
        logoutBtn.setBackground(new Color(0, 0, 0, 0));
        logoutBtn.setPreferredSize(new Dimension(80, 30));
        logoutBtn.setForeground(Color.DARK_GRAY);
        p2.add(logoutBtn);

        p.add(p1, BorderLayout.CENTER);
        p.add(p2, BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(p);
     // Initialize the ButtonListener with this UserInfo instance
        ButtonListener buttonListener = new ButtonListener(adminFrame, this, adminname);
        logoutBtn.addActionListener(buttonListener);
        addBtn.addActionListener(buttonListener);
        
        setTitle("Admin Info");
        setSize(400, 200);
        setLocation(1066,5);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only user info frame
        setVisible(true);
    }

    class ButtonListener implements ActionListener {
        private JFrame adminFrame;
        private JFrame adminInfoFrame;
        private String adminname;

        ButtonListener(JFrame adminFrame, JFrame adminInfoFrame,String adminname) {
            this.adminFrame = adminFrame;
            this.adminInfoFrame = adminInfoFrame;
            this.adminname = adminname;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logoutBtn) {
                if (adminFrame != null) {
                    adminFrame.dispose(); // Close the admin frame
                }
                if (adminInfoFrame != null) {
                    adminInfoFrame.dispose(); // Close the admin info frame
                }
                new LoginForm(); // Open the login form
            }else if (e.getSource() == addBtn) {
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
                    pstatement.setString(3,adminname);
                    pstatement.executeUpdate();
                    connection.close();

                    JOptionPane.showMessageDialog(null, "Your information has been added successfully!");
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
        JFrame AdminFrame = new JFrame("Admin Frame"); // Placeholder for your actual adminFrame
        new AdminInfo(AdminFrame, "adminname"); // Pass the admin frame and username
    }
}