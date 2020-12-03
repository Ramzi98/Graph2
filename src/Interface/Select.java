package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.REMAINDER;

public class Select extends JFrame {
    private JPanel panel1;
    private JButton partLButton;
    private JButton partLlButton;
    public Select() {
        this.setTitle("Graph manipulation");
        this.pack();
        this.setBackground(Color.BLACK);
        this.setSize(300, 80);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        final JPanel addpane = new JPanel();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = REMAINDER;
        gbc.fill = HORIZONTAL;
        addpane.add(partLButton, gbc);
        addpane.add(partLlButton, gbc);
        this.setContentPane(addpane);
        partLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Interface s = new Interface();
                    s.setVisible(true);
                    dispose();
                } catch (Exception ex) {
                }
            }
        });
        partLlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Part2 s = new Part2();
                    s.setVisible(true);
                    dispose();
                } catch (Exception ex) {
                }
            }
        });
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Select s = new Select();
            s.setVisible(true);
        });
    }
}
