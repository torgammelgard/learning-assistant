package view;

import model.Card;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-15.
 */
public class AddEditCardPanel extends JPanel {

    private Card newCard;
    private FormPanel formPanel;

    public AddEditCardPanel() {
        formPanel = new FormPanel(new String[]{"Question", "Answer"});
        add(formPanel);
        newCard = new Card();
    }

    public Card getCard() {
        String question = formPanel.getJTextField(0).getText();
        String[] answers = formPanel.getInputs();
        newCard = new Card();
        newCard.setQuestion(question);
        newCard.setAnswerAlternatives(answers);
        return newCard;
    }

    public void setInputs(String[] inputs) {
        formPanel.setInputs(inputs);
    }
}