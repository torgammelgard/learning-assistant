package controller;

import model.Card;
import model.DBSource;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by torgammelgard on 2016-04-15.
 */
public class Controller implements ActionListener {

    private int deckSize;
    private String deckName;
    private int currentIndex;
    private List<Card> collection;
    private CtrlButtonPanel ctrlButtonPanel;
    private CardPanel cardPanel;
    private MainFrame mainFrame;

    public Controller(MainFrame view) {
        mainFrame = view;
        mainFrame.setController(this);
        ctrlButtonPanel = view.getCtrlButtonPanel();
        ctrlButtonPanel.connectToController(this);

        cardPanel = view.getCardPanel();

        ctrlButtonPanel.setCollections(DBSource.getCollectionNames());
    }

    // TODO maybe batch sizes,
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
            cardPanel.showCard(new Card());
        }
    }

    private void prevCard() {
        if (currentIndex > 1) {
            currentIndex--;
        } else
            return;

        cardPanel.showCard(collection.get(currentIndex - 1));
        ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
    }

    private void nextCard() {
        if (currentIndex < deckSize) {
            currentIndex++;
        } else
            return;

        cardPanel.showCard(collection.get(currentIndex - 1));
        ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
    }

    private void deleteCard() {
        if (deckSize < 1)
            return;
        DBSource.deleteCard(collection.get(currentIndex - 1), deckName);
        changeDeck(deckName);
    }

    private void newCard() {
        AddEditCardPanel addEditCardPanel = new AddEditCardPanel();
        JScrollPane scrollPane = new JScrollPane(addEditCardPanel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "New Card",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DBSource.addCard(addEditCardPanel.getCard(), deckName);

            changeDeck(deckName);
            currentIndex = collection.size();
            cardPanel.showCard(collection.get(currentIndex - 1));
            ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
        }
    }

    private void editCard() {
        if (currentIndex == 0)
            return;
        int currentIndexBackup = currentIndex;
        Card cardToEdit = collection.get(currentIndex - 1);
        AddEditCardPanel addEditCardPanel = new AddEditCardPanel();
        addEditCardPanel.setInputs(cardToEdit);
        JScrollPane scrollPane = new JScrollPane(addEditCardPanel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "Edit Card",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DBSource.editCard(collection.get(currentIndex - 1), addEditCardPanel.getCard(), deckName);

            changeDeck(deckName);
            currentIndex = currentIndexBackup;
            cardPanel.showCard(collection.get(currentIndex - 1));
            ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
        }
    }

    private void search() {
        String searchString = mainFrame.getSearchString();
        mainFrame.clearSearch();
        if (!searchString.equals("")) {
            collection = DBSource.search(searchString, deckName);
            ctrlButtonPanel.setCollectionName(deckName + "[" + searchString + "]");
            deckSize = collection.size();
            if (deckSize > 0) {
                currentIndex = 1;
                cardPanel.showCard(collection.get(currentIndex - 1));
            } else {
                currentIndex = 0;
                cardPanel.showCard(new Card());
            }
            ctrlButtonPanel.setCardIndexLabel(currentIndex, deckSize);
        }

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
                changeDeck(ctrlButtonPanel.getSelectedCollection());
                break;
            case MainFrame.SEARCH:
                search();
                break;
            default:
                break;
        }
    }
}
