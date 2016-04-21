package view;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-21.
 */
class AnswerJTextField extends JTextField {

    static final int INPUTFIELD_LENGTH = 30;

    private boolean isCorrect;

    AnswerJTextField() {
        super(INPUTFIELD_LENGTH);
        isCorrect = false;
    }

    public AnswerJTextField(String text) {
        this();
        setText(text);
    }

    public void setCorrect(boolean b) {
        isCorrect = b;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
