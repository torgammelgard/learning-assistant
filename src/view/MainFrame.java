package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        Card card1 = new Card("What is the capital of Sweden?", new String[]{"Stockholm", "Oslo", "Copenhagen", "Stockholm", "Oslo", "Copenhagen", "Stockholm", "Oslo", "Copenhagen", "Stockholm", "Oslo", "Copenhagen"});

        add(Box.createGlue());
        add(card1);
        add(Box.createGlue());

        setPreferredSize(new Dimension(800, 600));

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
