import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodesControl extends JPanel { 
    private static final int CONTROL_WIDTH = 400;
    private static final int CONTROL_HEIGHT = 50;

    // Constructor
    public CodesControl(CodesMainPanel mainPanel) {
        // Creating control panel buttons
        JButton check = new JButton("Check");
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.isCorrectSequence()) {
                    JOptionPane.showMessageDialog(CodesControl.this, "The sequence is correct!");
                } else {
                    JOptionPane.showMessageDialog(CodesControl.this, "The sequence is incorrect!");
                }
            }
        });

        JButton reset = new JButton("New Sequence");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.resetButtonValues();
            }
        });

        // Adding buttons to the control panel
        setLayout(new FlowLayout());
        this.add(check);
        this.add(reset);

        // Control panel parameters
        this.setPreferredSize(new Dimension(CONTROL_WIDTH, CONTROL_HEIGHT));
        this.setBackground(Color.GRAY);
    }
}
