package FoodieFly;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FeedbackList {
    JTextField tf1, tf2;
    JTextArea ta1;
    JPanel feedbackpanel, p1, starPanel;
    String name, rating, comment;
    ImageIcon selectedStar;
    JButton[] starButtons;

    public JPanel createfeedbackPanel() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/foodiefly", "root", "Databasethinml123");
        String sql = "SELECT userName, rating, comment FROM feedback";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        feedbackpanel = new JPanel(new BorderLayout());
        feedbackpanel.setBackground(new Color(249, 245, 236)); // Set background color for the feedback panel

        p1 = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("images/bgp.jpg");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS)); // Use vertical box layout
        p1.setOpaque(true);

        while (rs.next()) {
            name = rs.getString(1);
            rating = rs.getString(2);
            comment = rs.getString(3);
            int rate = Integer.parseInt(rating);

            RoundedBorder rb = new RoundedBorder(15, Color.LIGHT_GRAY);

            JPanel feedbackEntryPanel = new JPanel();
            feedbackEntryPanel.setLayout(new BoxLayout(feedbackEntryPanel, BoxLayout.Y_AXIS));
            feedbackEntryPanel.setBorder(rb);
            feedbackEntryPanel.setBackground(new Color(255, 249, 229)); // Set background color for each entry

            JTextField nameField = new JTextField(name);
            nameField.setFont(new Font("Arial", Font.BOLD, 20));
            nameField.setPreferredSize(new Dimension(380, 50));
            nameField.setForeground(new Color(249, 212, 2));
            nameField.setBorder(null);
            nameField.setBorder(new EmptyBorder(5, 5, 5, 5));
            nameField.setOpaque(false);

            selectedStar = new ImageIcon("images/yellowstar.jpg");
            starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            starPanel.setPreferredSize(new Dimension(380, 50));
            starPanel.setOpaque(false);

            starButtons = new JButton[rate];

            for (int i = 0; i < rate; i++) {
                starButtons[i] = new JButton(selectedStar);
                starButtons[i].setPreferredSize(new Dimension(38, 38));
                starButtons[i].setOpaque(false);
                starButtons[i].setContentAreaFilled(false);
                starButtons[i].setBorderPainted(false);
                starPanel.add(starButtons[i]);
            }

            JTextArea commentArea = new JTextArea(comment);
            commentArea.setPreferredSize(new Dimension(380, 100));
            commentArea.setBorder(new EmptyBorder(5, 5, 5, 5));
            commentArea.setOpaque(false);
            commentArea.setLineWrap(true);
            commentArea.setWrapStyleWord(true);

            feedbackEntryPanel.add(nameField);
            // feedbackEntryPanel.add(ratingField);
            feedbackEntryPanel.add(starPanel);
            feedbackEntryPanel.add(commentArea);
            p1.add(feedbackEntryPanel);
            p1.add(Box.createRigidArea(new Dimension(0, 40)));
            p1.setBorder(new EmptyBorder(10, 40, 10, 40));
        }

        feedbackpanel.add(p1, BorderLayout.CENTER);
        return feedbackpanel;
    }
}
