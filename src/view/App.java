package view;

import controller.Controller;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            Controller controller = new Controller(mainFrame);
        });
    }
}
