package SortMyFiles;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sort My Files");
        JPanel pane = new JPanel();
        pane.add(new GUI().mainPanel);
        frame.setContentPane(pane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        //frame.setSize(800,600);
        frame.setLocationRelativeTo(null);//center it
        frame.setVisible(true);
    }
}
