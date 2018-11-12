package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class FormPanel extends JPanel {

    private final int INPUTFIELD_LENGTH = 30;

    private Font font = new Font("Garamond", Font.PLAIN, 18);
    private ArrayList<String> labels;
    private ArrayList<JTextField> jTextFields;

    public FormPanel() {
        setFont(font);
    }

    private void fontPicker() {
        // TODO
        String[] fonts;
        GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    public FormPanel(String[] labels) {
        this();
        this.labels = new ArrayList<>();
        this.jTextFields = new ArrayList<>();

        Collections.addAll(this.labels, labels);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        for (String label : labels) {
            // label
            JLabel l = new JLabel(label, SwingConstants.RIGHT);
            l.setFont(font);
            c.anchor = GridBagConstraints.LINE_END;
            add(l, c);
            c.gridx++;

            // input field
            JTextField tf = new JTextField(INPUTFIELD_LENGTH);
            this.jTextFields.add(tf);
            add(tf, c);
            c.anchor = GridBagConstraints.LINE_START;
            c.gridy++;
            c.gridx = 0;
        }
    }

    public JTextField getJTextField(int index) {
        if (index < jTextFields.size())
            return jTextFields.get(index);
        else
            return null;
    }

    public String[] getInputs() {
        // get all answers (the first is the question so this is not included)
        String[] inputs = new String[jTextFields.size() - 1];
        for (int i = 1; i < jTextFields.size(); i++) {
            inputs[i - 1] = jTextFields.get(i).getText();
        }
        return inputs;
    }

    public void setInputs(String[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            jTextFields.get(i).setText(inputs[i]);
        }
    }
}
