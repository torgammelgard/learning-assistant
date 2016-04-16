package view;

import model.Card;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by torgammelgard on 2016-04-12.
 */
public class CardPanel extends JPanel {

    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;
    private String question;
    private String[] answers;
    private JLabel questionLabel;
    private JList<String> answerList;
    private DefaultListModel<String> listModel;

    public CardPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        Border border2 = BorderFactory.createDashedBorder(Color.GREEN);
        setBorder(BorderFactory.createCompoundBorder(border2, border1));
    }

    public CardPanel(String question, String[] answers) {
        this();
        this.question = question;
        this.answers = answers;
        init();
    }

    public CardPanel(Card card) {
        this();
        this.question = card.getQuestion();
        this.answers = card.getAnswerAlternatives();
        init();
    }

    private void init() {
        questionLabel = new JLabel(question);
        questionLabel.setFont(new Font("Courier", Font.PLAIN, 18));
        answerList = new JList<>();
        answerList.setCellRenderer(new ListCellRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel rowLabel = new JLabel();
                rowLabel.setOpaque(true);
                if (isSelected)
                    rowLabel.setBackground(Color.GREEN);
                else
                    rowLabel.setBackground(Color.WHITE);

                rowLabel.setFont(new Font("Courier", Font.PLAIN, 15));
                rowLabel.setBorder(BorderFactory.createDashedBorder(Color.RED, 2, 3, 1, false));
                rowLabel.setPreferredSize(new Dimension(WIDTH * 3/4, 30));
                rowLabel.setText(value);
                return rowLabel;
            }
        });
        listModel = new DefaultListModel<>();
        for (String item : answers) {
            listModel.addElement(item);
        }

        answerList.setModel(listModel);

        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(25));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(questionLabel);
        box.add(Box.createVerticalGlue());
        JScrollPane scrollContainer = new JScrollPane(answerList);
        scrollContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(scrollContainer);
        box.add(Box.createVerticalGlue());

        add(box);
    }
    public void showCard(Card card) {
        question = card.getQuestion();
        answers = card.getAnswerAlternatives();

        questionLabel.setText(question);
        listModel.clear();
        if (answers == null)
            return;
        for (String item : answers) {
            listModel.addElement(item);
        }
    }

    public void addAnswerRow() {
        listModel.addElement("");
        answerList.invalidate();
        answerList.ensureIndexIsVisible(listModel.getSize()-1);
    }
}
