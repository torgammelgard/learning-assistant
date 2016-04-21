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

    private final static String PRIORITY_LABEL_TEXT = "Priority ";

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
        questionLabel = new JLabel();
        priorityLabel = new JLabel();
        answerList = new JList<>();
        listModel = new DefaultListModel<>();
        init();
    }

    private void init() {
        questionLabel.setText(question);
        questionLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        answerList.setCellRenderer(new ListCellRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel rowLabel = new JLabel();
                rowLabel.setOpaque(true);
                if (isSelected)
                    rowLabel.setBackground(Color.LIGHT_GRAY);
                else
                    rowLabel.setBackground(Color.WHITE);

                rowLabel.setFont(new Font("Courier", Font.PLAIN, 15));
                rowLabel.setPreferredSize(new Dimension(WIDTH * 3/4, 30));
                rowLabel.setText(value);
                return rowLabel;
            }
        });
        if (answers != null) {
            for (String item : answers) {
                listModel.addElement(item);
            }
        }

        answerList.setModel(listModel);

        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(25));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel p = new JPanel(new FlowLayout());
        p.add(questionLabel);

        box.add(priorityLabel);
        box.add(p);
        box.add(Box.createVerticalGlue());
        JScrollPane scrollContainer = new JScrollPane(answerList);
        scrollContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(scrollContainer);
        box.add(Box.createVerticalGlue());

        add(box);
    }
    private JLabel priorityLabel;

    public void showCard(Card card) {
        question = card.getQuestion();
        answers = card.getAnswerAlternatives();
        if (card.getPriority() != null) {
            priorityLabel.setText(PRIORITY_LABEL_TEXT + card.getPriority().toString());
        } else {
            priorityLabel.setText("");
        }

        questionLabel.setText(question);
        listModel.clear();
        if (answers == null)
            return;
        for (String item : answers) {
            listModel.addElement(item);
        }
    }

}
