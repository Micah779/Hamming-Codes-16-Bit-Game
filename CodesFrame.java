import javax.swing.*;
import java.awt.*;

public class CodesFrame extends JFrame {
    private CodesMainPanel mainPanel;
    private CodesControl controlPanel;

    // Constructor
    public CodesFrame() {
        setTitle("Parity Game");
        setSize(800, 600); // Adjusted size to fit both panels
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout to place panels

        // Initialize the panels
        mainPanel = new CodesMainPanel();
        controlPanel = new CodesControl(mainPanel); // Pass mainPanel to controlPanel

        // Add panels to the frame
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CodesFrame());
    }
}
