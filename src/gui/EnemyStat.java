package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class EnemyStat extends Statistics {

    private JLabel notificationLabel;

    public void addNotificationLabel() {
        notificationLabel = new JLabel("Waiting for ship deployment...");
        notificationLabel.setHorizontalAlignment(JLabel.CENTER);
        notificationLabel.setFont(notificationLabel.getFont().deriveFont(14.0F));
        notificationLabel.setBorder(BorderFactory.createEtchedBorder());
        add(notificationLabel);
    }

    public void updateNotificationLabel(String newLabel) {
        notificationLabel.setText(newLabel);
    }
}
