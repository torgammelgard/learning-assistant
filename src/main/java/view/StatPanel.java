package view;

import model.NoteService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Created by torgammelgard on 2016-04-20.
 */
public class StatPanel extends JPanel {
    private JLabel totalLabel;
    private JLabel priorityInfoLabel;

    private NoteService noteService;

    public StatPanel() {
        totalLabel = new JLabel();
        priorityInfoLabel = new JLabel();

        setBackground(Theme.BACKGROUND_COLOR);

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
        c.gridy++;
        JLabel l2 = new JLabel("Total : ");
        l2.setHorizontalAlignment(SwingConstants.RIGHT);
        add(l2, c);

        c.gridx = 1;
        add(totalLabel, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy++;
        priorityInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(priorityInfoLabel, c);

        setOpaque(true);
        setPreferredSize(new Dimension(150, 150)); // only the height will be used in this layout
    }

    /**
     * Sets the label referring to 'Total'
     *
     * @param total
     */
    public void setTotalLabelText(String total) {
        totalLabel.setText(total);
    }

    /**
     * Sets the label referring to 'Info'
     *
     * @param info
     */
    public void setPriorityInfoLabel(String info) {priorityInfoLabel.setText(info);}

    /**
     * Updates the StatPanel for a certain collection
     *
     * @param collection
     */
    public void updateForCollection(String collection) {
        Map<String, Integer> stats = noteService.getStats(collection);

        int total = 0;

        StringBuilder sb = new StringBuilder();

        for (String key : stats.keySet()) {
            sb.append(key == null ? "Priority not set" : key).append(" : ").append(stats.get(key)).append(", ");
            total += stats.get(key);
        }

        setPriorityInfoLabel(sb.toString());
        setTotalLabelText(String.valueOf(total));
    }
}
