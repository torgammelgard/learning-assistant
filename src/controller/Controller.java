package controller;

import model.Card;
import model.DBSource;
import view.*;

import javax.swing.*;
import java.awt.*;
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
        ctrlButtonPanel = view.getCtrlButtonPanel();
        ctrlButtonPanel.connectToController(this);

        cardPanel = view.getCardPanel();

        changeDeck("countries"); // remove this later
    }

    // TODO maybe batch sizes,
    private void changeDeck(String deckName) {
        collection = DBSource.getCollection(deckName);
        deckSize = collection.size();
        this.deckName = deckName;
        ctrlButtonPanel.setCardIndexLabel(1, deckSize);
        ctrlButtonPanel.setCollectionName(deckName);
        currentIndex = 1;
        cardPanel.showCard(collection.get(currentIndex - 1));
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
        DBSource.deleteCard(collection.get(currentIndex - 1), deckName);
        changeDeck(deckName);
    }

    private void newCard() {
        //FormPanel formPanel = new FormPanel(new String[]{"Question", "Answer"});
        AddCardDialog addCardDialog = new AddCardDialog(mainFrame);
        addCardDialog.pack();
        addCardDialog.setLocationRelativeTo(mainFrame);
        addCardDialog.setVisible(true);
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
                break;
            case CtrlButtonPanel.DELETE:
                deleteCard();
                break;
            case CtrlButtonPanel.NEW:
                newCard();
                break;
            default:
                break;
        }
    }
}
