package app;

import controller.Controller;
import model.NoteService;
import model.NoteServiceImpl;
import util.Logger;
import view.MainFrame;

import javax.swing.*;
import java.util.Locale;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class App {

    public static void main(String[] args) {
        handle(args);
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            NoteService noteService = new NoteServiceImpl();
            Controller controller = new Controller(mainFrame, noteService);
        });
    }

    // TODO refactor
    private static void handle(String[] args) {
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("lang")) {
                final String lang = args[1];
                switch (lang) {
                    case "sv":
                    case "en":
                        Logger.log("Setting language to " + lang);
                        Locale.setDefault(new Locale(lang));
                        break;
                    default:
                        Logger.log("Language " + lang + " not supported.");
                }

            }
        }
    }
}
