package view;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-15.
 */
public class AddCardDialog extends JDialog {
    public AddCardDialog(MainFrame frame) {
        super(frame, true);

        JTextField textField = new JTextField(10);
        Object[] array = {"Q1", "Q2", textField};
        Object[] options = {"Val A", "Val B"};
        JOptionPane optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
        setContentPane(optionPane);

    }
}
