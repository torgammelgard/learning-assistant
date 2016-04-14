package view;

import model.Card;
import model.DBSource;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


        testDB();


        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void dispose() {
        System.out.println("Closing Mongo Client");
        DBSource.closeMongoClient();

        super.dispose();
    }

    private void testDB() {

        // Test mongodb driver by printing all database names
        for (String DBName : DBSource.getMongoClient().listDatabaseNames()) {
            System.out.println(DBName);
        }

        Card card1 = new Card();
        card1.setQuestion("What is pi?");
        card1.setAnswerAlternatives(new String[]{"3.14", "34.5", "A donkey"});


        DBSource.addCard(card1);
    }
}
