package controller;

import model.CardRepository;
import model.DBSource;
import model.entities.Card;
import model.entities.CardImpl;
import view.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static model.Prioritizable.Priority;

/**
 * Created by torgammelgard on 2016-04-15.
 */
@ApplicationScoped
public class Controller implements ActionListener {

    private int deckSize;
    private String deckName;
    private int currentIndex;
    private List<Card> collection;
    private CtrlButtonPanel ctrlButtonPanel;
    private CardPanel cardPanel;
    private MainFrame mainFrame;
    private StatPanel statPanel;

    public Controller() {}

    public Controller(MainFrame view) {
        if (view == null) {
            System.exit(1);
        }
        this.mainFrame = view;
        this.mainFrame.setController(this);
        this.ctrlButtonPanel = view.getCtrlButtonPanel();
        this.ctrlButtonPanel.connectToController(this);
        this.statPanel = view.getStatPanel();
        this.cardPanel = view.getCardPanel();
        this.ctrlButtonPanel.setCollections(DBSource.getCollectionNames());
        this.deckName = ctrlButtonPanel.getSelectedCollection();
        this.collection = DBSource.getCollection(deckName);
    }

    /**
     * Changes the deck of cards (to another collection)
     *
     * @param deckName
     */
    private void changeDeck(String deckName) {
        collection = DBSource.getCollection(deckName);
        deckSize = collection.size();
        this.deckName = deckName;
        ctrlButtonPanel.setCollectionName(deckName);

        if (deckSize > 0) {
            ctrlButtonPanel.setCardIndexLabel(1, deckSize);
            currentIndex = 1;
            cardPanel.showCard(collection.get(currentIndex - 1));
        } else {
            ctrlButtonPanel.setCardIndexLabel(0, deckSize);
            currentIndex = 0;
            cardPanel.showCard(new CardImpl());
        }
        statPanel.updateForCollection(deckName);
    }

    /**
     * Shows the previous card in the collection
     */
    private void prevCard() {
        if (currentIndex > 1) {
            currentIndex--;
        } else
            return;

        cardPanel.showCard(collection.get(currentIndex - 1));
        ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
    }

    /**
     * Shows the next card in the collection
     */
    private void nextCard() {
        if (currentIndex < deckSize) {
            currentIndex++;
        } else
            return;

        cardPanel.showCard(collection.get(currentIndex - 1));
        ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
    }

    @Inject
    private CardRepository cardRepository;

    /**
     * Deletes a card and updates panels
     */
    private void deleteCard() {
        if (deckSize < 1)
            return;
        cardRepository.deleteCard(collection.get(currentIndex - 1), deckName);
        changeDeck(deckName);
        statPanel.updateForCollection(deckName);
    }

    /**
     * Adds a new card and updates panels
     */
    private void newCard() {
        AddEditCardPanel addEditCardPanel = new AddEditCardPanel();
        JScrollPane scrollPane = new JScrollPane(addEditCardPanel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "New card",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DBSource.addCard(addEditCardPanel.getCard(), deckName);

            changeDeck(deckName);
            currentIndex = collection.size();
            cardPanel.showCard(collection.get(currentIndex - 1));
            ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
        }
        statPanel.updateForCollection(deckName);
    }

    /**
     * Updates card and updates panels
     */
    private void editCard() {
        if (currentIndex == 0)
            return;
        int currentIndexBackup = currentIndex;
        Card cardToEdit = collection.get(currentIndex - 1);
        AddEditCardPanel addEditCardPanel = new AddEditCardPanel();
        addEditCardPanel.setInputs(cardToEdit);
        JScrollPane scrollPane = new JScrollPane(addEditCardPanel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "Edit card",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DBSource.editCard(collection.get(currentIndex - 1), addEditCardPanel.getCard(), deckName);

            changeDeck(deckName);
            currentIndex = currentIndexBackup;
            cardPanel.showCard(collection.get(currentIndex - 1));
            ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
        }
        statPanel.updateForCollection(deckName);
    }

    /**
     * Queries the database using a search string and a filter
     */
    private void search() {
        String searchString = mainFrame.getSearchString();
        mainFrame.clearSearch();
        if (!searchString.equals("")) {
            List<Integer> filter = findPriorityFilter(searchString);
            searchString = fixSearchString(searchString);
            collection = DBSource.search(searchString, filter, deckName);
            ctrlButtonPanel.setCollectionName(deckName + "[" + searchString + "]");
            deckSize = collection.size();
            if (deckSize > 0) {
                currentIndex = 1;
                cardPanel.showCard(collection.get(currentIndex - 1));
            } else {
                currentIndex = 0;
                cardPanel.showCard(new CardImpl());
            }
            ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
        }

    }

    /**
     * Helps with fixing the search string by trimming of brackets
     *
     * @param s
     * @return
     */
    private String fixSearchString(String s) {
        int start = s.indexOf("[");
        if (start > -1)
            return s.substring(0, start);
        else
            return s;
    }

    /**
     * Finds the Priority filter to be used in the search
     *
     * @param s
     * @return a list of integers referring the which priorities that should be used
     */
    private List<Integer> findPriorityFilter(String s) {
        List<Integer> priorityFilter = new ArrayList<>();
        int start = -1;
        int end = -1;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ('['))
                start = i;
            else if (chars[i] == (']'))
                end = i;
        }
        if (start > -1 && end > -1) {
            String sub = s.substring(start + 1, end);
            String[] prioStrArr = sub.split(",");
            for (String prioStr : prioStrArr) {
                switch (prioStr.trim()) {
                    case "LOW":
                        priorityFilter.add(Priority.LOW.ordinal());
                        break;
                    case "MEDIUM":
                        priorityFilter.add(Priority.MEDIUM.ordinal());
                        break;
                    case "HIGH":
                        priorityFilter.add(Priority.HIGH.ordinal());
                        break;
                    default:
                        break;
                }
            }
        } else if (start == -1) {
            // if there wasn't a [...] in the search string we search for all priorities
            priorityFilter.add(0);
            priorityFilter.add(1);
            priorityFilter.add(2);
        }

        return priorityFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CtrlButtonPanel.BACKWARD:
                prevCard();
                break;
            case CtrlButtonPanel.FORWARD:
                nextCard();
                break;
            case CtrlButtonPanel.EDIT:
                editCard();
                break;
            case CtrlButtonPanel.DELETE:
                deleteCard();
                break;
            case CtrlButtonPanel.ADD:
                newCard();
                break;
            case CtrlButtonPanel.COLLECTION_NAMES:
                String collection = ctrlButtonPanel.getSelectedCollection();
                changeDeck(collection);
                break;
            case MainFrame.SEARCH:
                search();
                break;
            default:
                break;
        }
    }
}
