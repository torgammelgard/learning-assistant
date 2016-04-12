package view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by torgammelgard on 2016-04-12.
 */
public class Card extends JPanel {

    private String question;
    private String[] answers;
    private JLabel questionLabel;
    private JCheckBox[] answerCheckBoxes;

    public Card() {
        setPreferredSize(new Dimension(400, 200));
        setMaximumSize(new Dimension(400, 200));
        Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        Border border2 = BorderFactory.createDashedBorder(Color.GREEN);
        setBorder(BorderFactory.createCompoundBorder(border2, border1));
    }

    public Card(String question, String[] answers) {
        this();
        this.question = question;
        this.answers = answers;
        init(question, answers);
    }

    private void init(String question, String[] answers) {
        questionLabel = new JLabel(question);
        questionLabel.setFont(new Font("Courier", Font.PLAIN, 18));
        answerCheckBoxes = new JCheckBox[answers.length];

        for (int i = 0; i < answers.length; i++) {
            answerCheckBoxes[i] = new JCheckBox(answers[i]);
            answerCheckBoxes[i].setFont(new Font("Courier", Font.PLAIN, 12));
        }

        add(questionLabel);
        Box box = Box.createVerticalBox();
        for (JCheckBox checkBox : answerCheckBoxes) {
            box.add(checkBox);
        }
        add(new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }
}
