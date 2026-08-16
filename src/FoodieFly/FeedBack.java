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

public class FeedBack extends JFrame {
    JLabel titleLabel, label1,label;
    JTextArea area;
    JButton cancelBtn, publishBtn,starButtons[];
    JPanel starPanel,p,p2, p3;
    String name;
    ImageIcon unselectedStar,selectedStar;
    int userRating;

    FeedBack(String name) {
      this.name = name;

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

        titleLabel = new JLabel("Give Feedback");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(0,0,0,0));

        p2 = new JPanel(new GridLayout(4,1));
        p2.setOpaque(false);
       
        label = new JLabel("How did we do?");
        label.setFont(new Font("Arial",Font.BOLD,14));
        label.setBorder(new EmptyBorder(0,0,0,0));
        
        selectedStar = new ImageIcon("images/yellowstar.jpg");
        unselectedStar = new ImageIcon("images/whitestar.jpg");
        starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        starPanel.setPreferredSize(new Dimension(400,50));
        starPanel.setOpaque(false);
        
        starButtons = new JButton[5];
        
        for(int i=0;i<5;i++) {
          starButtons[i] = new JButton(unselectedStar);
          starButtons[i].setPreferredSize(new Dimension(40,40));
          starButtons[i].setOpaque(false);
          starButtons[i].setContentAreaFilled(false);
          starButtons[i].setBorderPainted(false);
          starPanel.add(starButtons[i]);        
          final int rating = i+1;
          starButtons[i].addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          // TODO Auto-generated method stub
          updateStars(rating);
          userRating = rating;
        }
            
          });
        }
        
        label1 = new JLabel("Care to share your experiences?");
        label1.setFont(new Font("Arial", Font.BOLD, 14));
        label1.setBorder(new EmptyBorder(0,0,0,0));
        
        area = new JTextArea();
        area.setOpaque(false);
        area.setBorder(roundedBorder);
        area.setPreferredSize(new Dimension(400, 330));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);  
        
     // Add components to p2 with spacing
        p2.add(label);
        p2.add(starPanel); // Add the star panel
        p2.add(label1);
        p2.add(area);
        

        p3 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        p3.setOpaque(false);
        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setOpaque(false);
        cancelBtn.setBorder(roundedBorder);
        cancelBtn.setPreferredSize(new Dimension(130, 40));
        cancelBtn.setBackground(new Color(0, 0, 0, 0));

        publishBtn = new JButton("Publish");
        publishBtn.setFont(new Font("Arial", Font.BOLD, 14));
        publishBtn.setOpaque(false);
        publishBtn.setBorder(roundedBorder);
        publishBtn.setPreferredSize(new Dimension(130, 40));
        publishBtn.setBackground(new Color(0, 0, 0, 0));
        
        cancelBtn.addActionListener(new ButtonListener());
        publishBtn.addActionListener(new ButtonListener());
        p3.add(cancelBtn);
        p3.add(publishBtn);
        p3.setPreferredSize(new Dimension(400,80));
        p3.setBorder(new EmptyBorder(20,0,5,0));

        p.add(titleLabel, BorderLayout.NORTH);
        p.add(p2, BorderLayout.CENTER);
        p.add(p3, BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        add(p);

        setTitle("FeedBack");
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void updateStars(int rating) {
      for(int i =0;i<5;i++) {
        if(i < rating) {
          starButtons[i].setIcon(selectedStar);
        }
        else {
          starButtons[i].setIcon(unselectedStar);
        }
      }
    }
    
    class ButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if(e.getSource() == cancelBtn) {
        dispose();
      }
      else if(e.getSource() == publishBtn) {
        String comment = area.getText();
        try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly","root","Databasethinml123");
          String sql = "insert into feedback(userName,rating,comment) values(?,?,?)";
          PreparedStatement pstatement = connection.prepareStatement(sql);
          pstatement.setString(1, name);
          pstatement.setInt(2, userRating);
          pstatement.setString(3, comment);
          pstatement.executeUpdate();
          connection.close();
          
          JOptionPane.showMessageDialog(null, "Thank you for your feedback UwU");
        } catch (ClassNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      
    }  
      
    }
    public static void main(String[] args) {
        new FeedBack("hi");
    }
}