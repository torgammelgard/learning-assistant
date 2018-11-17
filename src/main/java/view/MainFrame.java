package view;

import controller.Controller;
import model.NoteService;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import static view.Theme.BACKGROUND_COLOR2;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class MainFrame extends JFrame {

    public static final String SEARCH = "Search";

    private CtrlButtonPanel ctrlButtonPanel;
    private StatPanel statPanel;
    private CardPanel cardPanel;
    private JTextField searchField;
    private BetterButton searchButton;

    private NoteService noteService;

    public MainFrame() throws HeadlessException {
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        getContentPane().setBackground(BACKGROUND_COLOR2);
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
        testButton.addActionListener(e -> noteService.getStats("countries"));

        statPanel = new StatPanel();
        add(statPanel);

        add(testButton);


        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void dispose() {
        System.out.println("Closing Mongo Client");
        //DBSource.closeMongoClient();

        super.dispose();
    }


    public CtrlButtonPanel getCtrlButtonPanel() {
        return ctrlButtonPanel;
    }

    public StatPanel getStatPanel() { return statPanel;}

    public CardPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * A test function for the database connection
     */
    private void testDB() {

        // Test mongodb driver by printing all database names
        for (String DBName : noteService.getDatabaseNames()) {
            System.out.println(DBName);
        }

    }


    /**
     * Sets the controller (just a searchButton for now)
     *
     * @param controller the controller
     */
    public void setController(Controller controller) {
        searchButton.addActionListener(controller);
    }

    /**
     * Creates the menu (really just the search field and search button)
     */
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        searchField = new JTextField();
        menuBar.add(searchField);
        searchButton = new BetterButton(SEARCH);
        searchButton.setActionCommand(SEARCH);
        menuBar.add(searchButton);
        setJMenuBar(menuBar);
    }

    /**
     *
     * @return search string
     */
    public String getSearchString() {
        return searchField.getText();
    }

    /**
     * Clears the search field
     */
    public void clearSearch() {
        searchField.setText("");
    }
}
