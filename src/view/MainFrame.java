package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        getContentPane().setLayout(new BorderLayout());

        add(new FormPanel(new String[]{"Ett", "Två"}));

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
