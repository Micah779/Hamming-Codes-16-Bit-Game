import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CodesMainPanel extends JPanel {
    private JButton[] bits;
    private String[] originalBits;

    int PANEL_WIDTH = 400;
    int PANEL_HEIGHT = 400;

    public CodesMainPanel() {
        bits = new JButton[16];
        originalBits = new String[16];

        // Initialize buttons and add to the main panel
        for (int i = 0; i < 16; i++) {
            bits[i] = new JButton();
            bits[i].addActionListener(new ButtonActionListener());
            this.add(bits[i]);
        }

        // Panel config
        this.setLayout(new GridLayout(4, 4));
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.GRAY);

        generateAndSetSequence();
    }

    private void generateAndSetSequence() {
        String sequence = generateEvenParitySequence();
        sequence = introduceError(sequence);

        for (int i = 0; i < 16; i++) {
            String value = String.valueOf(sequence.charAt(i));
            bits[i].setText(value);
            originalBits[i] = value;
        }

        for (int i = 0; i < 16; i++) {
            if (i == 1 || i == 2 || i == 4 || i == 8) {
                bits[i].setForeground(Color.RED);
            }
        }
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            int index = java.util.Arrays.asList(bits).indexOf(source);
            if (!isPowerOfTwo(index)) {
                if (source.getText().equals("1")) {
                    source.setText("0");
                } else {
                    source.setText("1");
                }
            }
        }
    }

    public void resetButtonValues() {
        generateAndSetSequence();
    }

    private String generateEvenParitySequence() {
        Random rand = new Random();
        char[] sequence = new char[16];
    
        // Initialize sequence with data bits (randomly)
        for (int i = 0; i < 16; i++) {
            if (!isPowerOfTwo(i)) {
                sequence[i] = rand.nextBoolean() ? '1' : '0';
            } else {
                sequence[i] = '0'; // Placeholder for parity bits
            }
        }
    
        // Calculate parity bits
        sequence[1] = calculateParity(sequence, 1); // Odd columns
        sequence[2] = calculateParity(sequence, 2); // Last two columns
        sequence[4] = calculateParity(sequence, 4); // Odd rows
        sequence[8] = calculateParity(sequence, 8); // Last two rows
        
        // Calculate overall parity
        sequence[0] = calculateOverallParity(sequence);
    
        return new String(sequence);
    }

    private boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    private char calculateParity(char[] sequence, int parityBit) {
        int count = 0;
        
        switch (parityBit) {
            case 1: // Odd columns
                for (int i = 3; i < 16; i += 2) {
                    if (sequence[i] == '1') count++;
                }
                break;
            case 2: // Last two columns
                for (int i = 6; i < 16; i++) {
                    if (i != 2 && i != 4 && i != 5 && i != 8 && i != 9 && i != 12 && i != 13 && sequence[i] == '1') 
                    {
                        count++;
                    }
                }
                break;
            case 4: // Odd rows
                for (int i = 5; i < 16; i++) {
                    if (i != 8 && i != 9 && i != 10  && i != 11 && sequence[i] == '1') {
                        count++;
                    }
                }
            case 8: // Last two rows
                for (int i = 9; i < 16; i++) {
                    if (sequence[i] == '1') {
                        count++;
                    }
                }
                break;
        }
        
        return (count % 2 == 0) ? '0' : '1';
    }

    private char calculateOverallParity(char[] sequence) {
        int count = 0;
        for (int i = 3; i < 16; i++) {
            if (!isPowerOfTwo(i) && sequence[i] == '1') count++;
        }
        return (count % 2 == 0) ? '0' : '1';
    }

    private String introduceError(String sequence) {
        Random rand = new Random();
        int[] dataBitIndices = {3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15};
        int errorIndex = dataBitIndices[rand.nextInt(dataBitIndices.length)];
        StringBuilder sequenceWithError = new StringBuilder(sequence);
    
        sequenceWithError.setCharAt(errorIndex, sequenceWithError.charAt(errorIndex) == '0' ? '1' : '0');
    
        return sequenceWithError.toString();
    }

    public boolean isCorrectSequence() {
        char[] sequence = new char[16];
        for (int i = 0; i < 16; i++) {
            sequence[i] = bits[i].getText().charAt(0);
        }

        return detectErrorIndex(sequence) == -1;
    }

    private int detectErrorIndex(char[] sequence) {
        boolean oddColumnsError = (calculateParity(sequence, 1) != sequence[1]);
        boolean lastTwoColumnsError = (calculateParity(sequence, 2) != sequence[2]);
        boolean oddRowsError = (calculateParity(sequence, 4) != sequence[4]);
        boolean lastTwoRowsError = (calculateParity(sequence, 8) != sequence[8]);

        if (oddColumnsError && lastTwoColumnsError && !oddRowsError && lastTwoRowsError) {
            return 11; // Error in the 12th bit (index 11)
        }

        return -1; // No error detected or multiple errors (not handled in this game)
    }
}


