package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by torgammelgard on 2016-04-21.
 */
public class AnswerCellRenderer extends JLabel implements ListCellRenderer {
    public AnswerCellRenderer() {
        setOpaque(true);
        setFont(new Font("Garamond", Font.PLAIN, 18));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        AnswerJTextField a = (AnswerJTextField) value;

        if (isSelected) {
            setBackground(Color.GRAY);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.BLACK);
        }

        if (a.isCorrect()) {
            setForeground(Color.BLUE);
        }
        setText(a.getText());

        return this;
    }
}
