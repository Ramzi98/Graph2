package Interface;

import Flow.FlowNetwork;
import m1graf2020.Exceptiongraf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Part2 extends JFrame {
    public static void main(String[] args) {
        Part2 frame = new Part2();
        frame.setVisible(true);
    }
    JFileChooser j = new JFileChooser();
    String filename = "";
    JButton button = new JButton("Import");
    JButton nextBtn = new JButton("Next");
    JButton backBtn = new JButton("Back");
    JPanel panel1 = new JPanel();
    JLabel dotLabel = new JLabel();
    Part2() {
        super("Part ll");
        dotLabel.setText("Select DOT file : ");
        setSize(300, 200);
        setLocation(500, 280);
        panel1.setLayout(null);

        button.setBounds(110, 30, 150, 20);
        nextBtn.setBounds(140, 130, 80, 20);
        dotLabel.setBounds(10, 30, 100, 20);
        backBtn.setBounds(50, 130, 80, 20);
        panel1.add(dotLabel);
        panel1.add(button);
        panel1.add(nextBtn);
        panel1.add(backBtn);
        nextBtn.setEnabled(false);
        getContentPane().add(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        nextButton();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    public void nextButton() {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                j.showSaveDialog(null);
                filename = j.getSelectedFile().getName();
                button.setText(filename);
                button.setEnabled(false);
                nextBtn.setEnabled(true);
            }
        });
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
                Select s = new Select();
                s.setVisible(true);
            }
        });
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
                Frame test = new Frame("Imported DOT file");
                test.setVisible(true);
                test.setSize(700, 500);
                test.setResizable(false);
                BufferedImage image = null;
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - test.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - test.getHeight()) / 2);
                test.setLocation(x, y);
                try {
                    FlowNetwork g = new FlowNetwork("DOT\\"+filename);
                    int[][] mat = g.toMatrix();
                    g.ford_fulkerson_execute(mat);
                    System.out.println(filename);
                    String name = filename.substring(0, filename.length() - 4);
                    DotFileToPDF1(name);
                    sleep(500);
                    test.dispose();
                    Imageslider obj = new Imageslider();
                    obj.setVisible(true);

                } catch (Exceptiongraf | IOException | InterruptedException exceptiongraf) {
                    exceptiongraf.printStackTrace();
                }
            }
        });
    }
    public void DotFileToPDF1(String graphname) throws IOException {
        try {
            File folder = new File("DOT\\");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    Runtime.getRuntime().exec("dot -Tpng DOT\\"+file.getName()+" -o img\\"+file.getName().replaceFirst("[.][^.]+$", "")+".png");
                    Runtime.getRuntime().exec("dot -Tpdf DOT\\"+file.getName()+" -o PDF\\"+file.getName().replaceFirst("[.][^.]+$", "")+".pdf");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


class Imageslider extends JFrame implements ActionListener {
    ImageIcon s[];
    JButton b1, b2;
    JLabel l;
    int i;
    JPanel p;
    BufferedImage image = null;
    public Imageslider() throws IOException {
        setLayout(new BorderLayout());
        setSize(800, 700);
        setTitle("Imported DOT File");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        JPanel p = new JPanel(new FlowLayout());
        b1 = new JButton("<<");
        b2 = new JButton(">>");
        p.add(b1);
        p.add(b2);
        add(p, BorderLayout.SOUTH);
        b1.addActionListener(this);
        b2.addActionListener(this);
        File directory=new File("img\\");
        File[] listOfFiles = directory.listFiles();
        int fileCount = directory.list().length;
        s = new ImageIcon[fileCount];
        int j = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                s[j] = new ImageIcon("img\\"+file.getName());
                j++;
            }
        }
        l = new JLabel("",JLabel.CENTER);
        add(l,BorderLayout.CENTER);
        l.setIcon(s[0]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1) {

            if(i==0)
            {
                JOptionPane.showMessageDialog(null,"You can't go back anymore.");
            }
            else
            {
                i=i-1;
                l.setIcon(s[i]);
            }
        }
        if(e.getSource()==b2)
        {
            if(i==s.length-1)
            {
                JOptionPane.showMessageDialog(null,"Algorithm is finished, go back to see the previous steps.");
            }
            else
            {
                i=i+1;
                l.setIcon(s[i]);
            }
        }
    }
}
