package view;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-21.
 */
public class BetterButton extends JButton {
    public BetterButton() {
    }

    public BetterButton(String text) {
        this();
        ImageIcon imageIcon;
        switch (text) {
            case CtrlButtonPanel.EDIT:
                imageIcon = new ImageIcon("src/res/edit.png");
                break;
            case CtrlButtonPanel.ADD:
                imageIcon = new ImageIcon("src/res/add.png");
                break;
            case CtrlButtonPanel.DELETE:
                imageIcon = new ImageIcon("src/res/delete.png");
                break;
            case CtrlButtonPanel.FORWARD:
                imageIcon = new ImageIcon("src/res/forward.png");
                break;
            case CtrlButtonPanel.BACKWARD:
                imageIcon = new ImageIcon("src/res/backward.png");
                break;
            case MainFrame.SEARCH:
                imageIcon = new ImageIcon("src/res/search.png");
                break;
            default:
                imageIcon = new ImageIcon();
        }
        setIcon(imageIcon);
        setText(text);
    }
}
