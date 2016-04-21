package view;

import model.Card;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by torgammelgard on 2016-04-12.
 */
public class CardPanel extends JPanel {

    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;

    private final static String PRIORITY_LABEL_TEXT = "Priority ";

    private String question;
    private ArrayList<AnswerJTextField> answers;
    private JLabel questionLabel;
    private JLabel priorityLabel;
    private JList<AnswerJTextField> answerList;
    private DefaultListModel<AnswerJTextField> listModel;

    public CardPanel() {
        setBackground(MainFrame.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        Border border2 = BorderFactory.createDashedBorder(Color.GREEN);
        setBorder(BorderFactory.createCompoundBorder(border2, border1));
        questionLabel = new JLabel();
        priorityLabel = new JLabel();
        answerList = new JList<>();
        listModel = new DefaultListModel<>();
        answers = new ArrayList<>();
        init();
    }

    /**
     * Initializes (some labels and JList)
     */
    private void init() {
        questionLabel.setText(question);
        questionLabel.setFont(new Font("Courier", Font.PLAIN, 20));

        if (answers != null) {
            for (AnswerJTextField a : answers) {
                listModel.addElement(a);
            }
        }

        answerList.setModel(listModel);
        answerList.setCellRenderer(new AnswerCellRenderer());

        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(25));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel p = new JPanel(new FlowLayout());
        p.add(questionLabel);

        priorityLabel.setBorder(BorderFactory.createDashedBorder(Color.RED));
        priorityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(priorityLabel);
        box.add(p);
        box.add(Box.createVerticalGlue());
        JScrollPane scrollContainer = new JScrollPane(answerList);
        scrollContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(scrollContainer);
        box.add(Box.createVerticalGlue());

        add(box);
    }

    /**
     * Shows a card by updating components
     *
     * @param card
     */
    public void showCard(Card card) {
        question = card.getQuestion();
        ArrayList<String> answerAlternatives = new ArrayList<>(card.getAnswerAlternatives());
        answers.clear();
        int c = 0;
        AnswerJTextField ans;
        for (String s : answerAlternatives) {
            ans = new AnswerJTextField(s);
            if (c++ == 0)
                ans.setCorrect(true);
            answers.add(ans);
        }

        // randomize the order of the answers
        Collections.shuffle(answers);

        if (card.getPriority() != null) {
            priorityLabel.setText(PRIORITY_LABEL_TEXT + card.getPriority().toString());
        } else {
            priorityLabel.setText("");
        }

        questionLabel.setText(question);
        listModel.clear();
        if (answers == null)
            return;
        for (AnswerJTextField a : answers) {
            listModel.addElement(a);
        }
    }

}
