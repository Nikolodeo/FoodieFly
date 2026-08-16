package FoodieFly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class Catagory {
    private CustomButton previousButton;  // Store the previously clicked button
    private Consumer<String> categoryClickListener;

    // Constructor to pass the listener
    public Catagory(Consumer<String> categoryClickListener) {
        this.categoryClickListener = categoryClickListener;
    }
    // Create a JPanel for the vertical menu
    public JPanel createCatagoryPanel() {

        // Create a custom rounded border
        RoundedBorder rb = new RoundedBorder(30, Color.BLACK);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); // Set vertical layout
        menuPanel.setOpaque(false);
        menuPanel.setBackground(new Color(0, 0, 0));
        menuPanel.setPreferredSize(new Dimension(200, 600));

        // Create buttons and add action listeners to them
        CustomButton button1 = createButton("CAKE", rb);
        CustomButton button2 = createButton("COOKIE", rb);
        CustomButton button3 = createButton("BREAD", rb);
        CustomButton button4 = createButton("DRINK", rb);

        // Add buttons to the panel
        menuPanel.add(button1);
        menuPanel.add(button2);
        menuPanel.add(button3);
        menuPanel.add(button4);

        return menuPanel;
    }

    // Helper method to create a button with specific settings
    private CustomButton createButton(String text, RoundedBorder border) {
        CustomButton button = new CustomButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(180, 80));  // Set size to 180x80
        button.setMaximumSize(new Dimension(200, 100));   // Ensure it stays the set size
        button.setBorder(border);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setFocusPainted(false);  // Disable focus painting to prevent visual artifacts

        // Set an ActionListener to handle click events
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset the previous button's background color if any
                if (previousButton != null) {
                    previousButton.setSelected(false);
                    previousButton.repaint();
                }

                // Set the clicked button's background color and make it opaque
                button.setSelected(true);
                button.repaint();

                // Update the previous button to the current button
                previousButton = button;
                
                // Notify the listener of the category click
                categoryClickListener.accept(text);
            }
        });

        return button;
    }
}

class CustomButton extends JButton {
    private boolean selected = false;

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);  // Prevents the button from filling background by default
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (selected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0xFFFFE0));  // Set the fill color to yellow
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Match the border's rounded corners
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
