package view;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by torgammelgard on 2016-04-15.
 */
public class AddEditCardPanel extends JPanel {

    private final int INPUTFIELD_LENGTH = 30;
    private Font font = new Font("Garamond", Font.PLAIN, 18);

    private Card newCard;
    private ArrayList<JLabel> labels;
    private ArrayList<JTextField> jTextFields;
    private int numAnswers = 1;
    private int lastRowY;
    private JButton addAnswerButton;
    private JButton deleteAnswerButton;

    public AddEditCardPanel() {
        newCard = new Card();
        this.labels = new ArrayList<>();
        this.jTextFields = new ArrayList<>();

        labels.add(new JLabel("Question"));
        labels.add(new JLabel("Answer " + String.valueOf(numAnswers)));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        for (JLabel label : labels) {
            addRow(label, c);
        }
        c.gridx = 1;
        lastRowY = c.gridy;

        addAnswerButton = new JButton("Add alternative answer");
        addAnswerButton.addActionListener(e ->
                addAnswerField()
        );
        add(addAnswerButton, c);

        deleteAnswerButton = new JButton("Delete alternative answer");
        deleteAnswerButton.addActionListener(e ->
                deleteAnswerField()
        );
        c.gridx = 0;
        add(deleteAnswerButton, c);
    }

    private void addRow(JLabel label, GridBagConstraints c) {
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setFont(font);
        c.anchor = GridBagConstraints.LINE_END;
        add(label, c);
        c.gridx++;

        // input field
        JTextField tf = new JTextField(INPUTFIELD_LENGTH);
        this.jTextFields.add(tf);
        add(tf, c);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridy++;
        c.gridx = 0;
    }

    private void addAnswerField() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = lastRowY++;
        remove(addAnswerButton);
        JLabel label = new JLabel("Answer " + String.valueOf(++numAnswers));
        addRow(label, c);
        labels.add(label);
        c.gridy++;
        c.gridx = 1;
        add(addAnswerButton, c);
        revalidate();
        repaint();
    }

    private void deleteAnswerField() {
        if (jTextFields.size() > 2) {
            remove(jTextFields.get(jTextFields.size() - 1));
            jTextFields.remove(jTextFields.size() - 1);
        }
        revalidate();
        repaint();

    }

    private JTextField getJTextField(int index) {
        if (index < jTextFields.size())
            return jTextFields.get(index);
        else
            return null;
    }

    public Card getCard() {
        String question = getJTextField(0).getText();
        String[] answers = getInputs();
        newCard = new Card();
        newCard.setQuestion(question);
        newCard.setAnswerAlternatives(answers);
        return newCard;
    }

    private String[] getInputs() {
        // get all answers (the first is the question so this is not included)
        String[] inputs = new String[jTextFields.size() - 1];
        for (int i = 1; i < jTextFields.size(); i++) {
            inputs[i - 1] = jTextFields.get(i).getText();
        }
        return inputs;
    }

    public void setInputs(Card cardToEdit) {
        jTextFields.get(0).setText(cardToEdit.getQuestion());

        // one answer row is already present, so start at index 1
        for (int i = 1; i < cardToEdit.getAnswerAlternatives().length; i++) {
            addAnswerField();
        }

        // fill the answer alternatives
        for (int i = 1; i < jTextFields.size(); i++) {
            jTextFields.get(i).setText(cardToEdit.getAnswerAlternatives()[i - 1]);
        }
    }

}
