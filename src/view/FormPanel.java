package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class FormPanel extends JPanel {

    private final int INPUTFIELD_LENGTH = 30;

    private Font font = new Font("Garamond", Font.PLAIN, 18);

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
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        for (String label : labels) {
            // label
            JLabel l = new JLabel(label, SwingConstants.RIGHT);
            //l.setBackground(Color.GREEN);
            //l.setOpaque(true);
            l.setFont(font);
            c.anchor = GridBagConstraints.LINE_END;
            add(l, c);
            c.gridx++;

            // input field
            JTextField tf = new JTextField(INPUTFIELD_LENGTH);
            add(tf, c);
            c.anchor = GridBagConstraints.LINE_START;
            c.gridy++;
            c.gridx = 0;
        }
    }

}
