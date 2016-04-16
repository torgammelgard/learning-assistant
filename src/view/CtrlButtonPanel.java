package view;

import controller.Controller;

import javax.swing.*;
import java.util.List;

/**
 * Created by torgammelgard on 2016-04-15.
 */
public class CtrlButtonPanel extends JPanel {

    public static final String FORWARD = "Forward";
    public static final String BACKWARD = "Backward";
    public static final String EDIT = "Edit";
    public static final String DELETE = "Delete";
    public static final String ADD = "Add";
    public static final String COLLECTION_NAMES = "Collection names";

    private JButton forwardBtn;
    private JButton backwardBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton newBtn;

    private JComboBox<String> collectionNamesComboBox;
    private JLabel cardIndexLabel;
    private JLabel collectionNameLabel;

    public CtrlButtonPanel() {
        collectionNameLabel = new JLabel();
        add(collectionNameLabel);

        collectionNamesComboBox = new JComboBox<>();
        add(collectionNamesComboBox);

        cardIndexLabel = new JLabel("- / -");
        add(cardIndexLabel);

        forwardBtn = new JButton(FORWARD);
        backwardBtn = new JButton(BACKWARD);
        editBtn = new JButton(EDIT);
        deleteBtn = new JButton(DELETE);
        newBtn = new JButton(ADD);

        add(deleteBtn);
        add(editBtn);
        add(newBtn);
        add(backwardBtn);
        add(forwardBtn);
    }

    public String getSelectedCollection() {
        return (String) collectionNamesComboBox.getSelectedItem();
    }

    public void setCollections(List<String> collectionNames) {
        if (collectionNames.size() == 0)
            return;
        for (String name : collectionNames) {
            collectionNamesComboBox.addItem(name);
        }
    }

    public void setCardIndexLabel(int index, int size) {
        cardIndexLabel.setText(String.valueOf(index) + " / " + String.valueOf(size));
    }

    public void setCollectionName(String collectionName) {
        collectionNameLabel.setText(collectionName);
    }

    public void connectToController(Controller controller) {
        forwardBtn.setActionCommand(FORWARD);
        backwardBtn.setActionCommand(BACKWARD);
        editBtn.setActionCommand(EDIT);
        deleteBtn.setActionCommand(DELETE);
        newBtn.setActionCommand(ADD);
        collectionNamesComboBox.setActionCommand(COLLECTION_NAMES);

        forwardBtn.addActionListener(controller);
        backwardBtn.addActionListener(controller);
        editBtn.addActionListener(controller);
        deleteBtn.addActionListener(controller);
        newBtn.addActionListener(controller);
        collectionNamesComboBox.addActionListener(controller);
    }
}
