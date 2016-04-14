package view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        CardPanel cardPanel1 = new CardPanel("What is the capital of Sweden?", new String[]{"Stockholm", "Oslo", "Copenhagen", "Stockholm", "Oslo", "Copenhagen", "Stockholm", "Oslo", "Copenhagen", "Stockholm", "Oslo", "Copenhagen"});
        cardPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(cardPanel1);
        add(Box.createVerticalGlue());
        cardPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JButton testButton = new JButton("THIS IS A TEST BUTTON");
        testButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel1.addAnswerRow();
            }
        });
        add(testButton);
        setPreferredSize(new Dimension(800, 600));

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
