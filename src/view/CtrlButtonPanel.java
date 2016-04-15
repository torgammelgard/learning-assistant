package view;

import controller.Controller;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-15.
 */
public class CtrlButtonPanel extends JPanel {

    public static final String FORWARD = "Forward";
    public static final String BACKWARD = "Backward";
    public static final String EDIT = "Edit";
    public static final String DELETE = "Delete";
    public static final String NEW = "New";

    private JButton forwardBtn;
    private JButton backwardBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton newBtn;

    private JLabel cardIndexLabel;
    private JLabel collectionNameLabel;

    public CtrlButtonPanel() {
        collectionNameLabel = new JLabel();
        add(collectionNameLabel);

        cardIndexLabel = new JLabel("- / -");
        add(cardIndexLabel);

        forwardBtn = new JButton(FORWARD);
        backwardBtn = new JButton(BACKWARD);
        editBtn = new JButton(EDIT);
        deleteBtn = new JButton(DELETE);
        newBtn = new JButton(NEW);

        add(deleteBtn);
        add(editBtn);
        add(newBtn);
        add(backwardBtn);
        add(forwardBtn);
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
        newBtn.setActionCommand(NEW);

        forwardBtn.addActionListener(controller);
        backwardBtn.addActionListener(controller);
        editBtn.addActionListener(controller);
        deleteBtn.addActionListener(controller);
        newBtn.addActionListener(controller);
    }
}
