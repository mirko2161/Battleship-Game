package utils;

import gui.MainFrame;
import gui.UserStat;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;

public class FileSaverAndLoader { // TODO: limit file save/load to .save files

    private final MainFrame mainFrame;
    private final JFileChooser fileChooser;

    public FileSaverAndLoader(MainFrame mainFrame) {
        this.fileChooser = new JFileChooser();
        this.mainFrame = mainFrame;
    }

    public void saveToFile() throws IOException {
        if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);

            mainFrame.getEnemyMap().getSaveOrLoad().saveMap(os);
            mainFrame.getUserMap().getSaveOrLoad().saveMap(os);

            os.close();
        }
    }

    public void loadFromFile() throws IOException, ClassNotFoundException {
        if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            mainFrame.getContentPane().removeAll(); // could call New Game, but need to move instruction window first
            GridLayout layout = new GridLayout(2, 2);
            mainFrame.setLayout(layout);
            mainFrame.addPanels();
            ((UserStat) mainFrame.getUserStat()).removeButtons();

            mainFrame.getEnemyMap().setShipsPlaced(true);
            mainFrame.getUserMap().setShipsPlaced(true);

            File file = fileChooser.getSelectedFile();
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            mainFrame.getEnemyMap().getSaveOrLoad().loadMap(ois);
            mainFrame.getUserMap().getSaveOrLoad().loadMap(ois);

            ois.close();
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
