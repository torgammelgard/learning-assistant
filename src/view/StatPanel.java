package view;

import model.DBSource;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Created by torgammelgard on 2016-04-20.
 */
public class StatPanel extends JPanel {
    private JLabel avgLabel, totalLabel;

    public StatPanel() {
        avgLabel = new JLabel();
        totalLabel = new JLabel();

        setBackground(MainFrame.BACKGROUND_COLOR);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JLabel title = new JLabel("Statistics");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Garamond", Font.PLAIN, 24));
        add(title, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;

        JLabel l1 = new JLabel("Average : ");
        l1.setHorizontalAlignment(SwingConstants.RIGHT);
        add(l1, c);

        c.gridx = 1;
        add(avgLabel, c);

        c.gridx = 0;
        c.gridy++;
        JLabel l2 = new JLabel("Total : ");
        l2.setHorizontalAlignment(SwingConstants.RIGHT);
        add(l2, c);

        c.gridx = 1;
        add(totalLabel, c);

        setOpaque(true);
        setPreferredSize(new Dimension(150, 150)); // only the height will be used in this layout
    }

    public void setAvgLabelText(String avg) {
        avgLabel.setText(avg);
    }
    public void setTotalLabelText(String total) {
        totalLabel.setText(total);
    }

    public void updateForCollection(String collection) {
        Map<String, Integer> stats = DBSource.getStats(collection);

        int total = 0;

        for (String key : stats.keySet()) {
            System.out.println("Number of " + key + " is " + stats.get(key));
            total += stats.get(key);
        }

        setTotalLabelText(String.valueOf(total));
    }
}
