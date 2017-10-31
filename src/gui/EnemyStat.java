package gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class EnemyStat extends Statistics {

    private JLabel notificationLabel;

    public EnemyStat() {
        addNotificationLabel();
    }

    private void addNotificationLabel() {
        notificationLabel = new JLabel("Waiting for ship deployment...");
        notificationLabel.setHorizontalAlignment(JLabel.CENTER);
        float textSize = 18.0f;
        notificationLabel.setFont(notificationLabel.getFont().deriveFont(textSize));

        JPanel notificationPanel = new JPanel(); // for borders and spacing
        notificationPanel.setLayout(new BorderLayout());

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 5, 10, 5);
        Border etchedBorder = BorderFactory.createEtchedBorder();
        notificationPanel.setBorder(BorderFactory.createCompoundBorder(etchedBorder, emptyBorder));

        notificationPanel.add(notificationLabel, BorderLayout.CENTER);
        super.add(notificationPanel, BorderLayout.SOUTH);
    }

    public void updateNotificationLabel(String newLabel) {
        notificationLabel.setText(newLabel);
    }

}
