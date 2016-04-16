package view;

import controller.Controller;
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

    public static final String SEARCH = "Search";

    private CtrlButtonPanel ctrlButtonPanel;
    private CardPanel cardPanel;
    private JTextField searchField;
    private JButton searchButton;

    public MainFrame() throws HeadlessException {
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        createMenu();

        cardPanel = new CardPanel();
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(cardPanel);
        add(Box.createVerticalGlue());
        cardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        ctrlButtonPanel = new CtrlButtonPanel();
        add(ctrlButtonPanel);

        JButton testButton = new JButton("THIS IS A TEST BUTTON");
        testButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.addAnswerRow();
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


    public CtrlButtonPanel getCtrlButtonPanel() {
        return ctrlButtonPanel;
    }

    public CardPanel getCardPanel() {
        return cardPanel;
    }

    private void testDB() {

        // Test mongodb driver by printing all database names
        for (String DBName : DBSource.getMongoClient().listDatabaseNames()) {
            System.out.println(DBName);
        }

    }


    public void setController(Controller controller) {
        searchButton.addActionListener(controller);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu arkiv = new JMenu("Arkiv");

        JMenuItem menuItem = new JMenuItem("Exit");
        arkiv.add(menuItem);
        menuBar.add(arkiv);


        searchField = new JTextField();
        menuBar.add(searchField);
        searchButton = new JButton("Search");
        searchButton.setActionCommand(SEARCH);
        menuBar.add(searchButton);
        setJMenuBar(menuBar);
    }

    public String getSearchString() {
        return searchField.getText();
    }
}
