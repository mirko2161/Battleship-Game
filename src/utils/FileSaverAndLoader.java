package utils;

import gui.MainFrame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;

public class FileSaverAndLoader { // TODO: limit file save/load to .save files

    private final MainFrame mainFrame;

    public FileSaverAndLoader(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void saveToFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileOutputStream fs = new FileOutputStream(file);
            try (ObjectOutputStream os = new ObjectOutputStream(fs)) {
                mainFrame.getEnemyMap().getSaveOrLoad().saveMap(os);
                mainFrame.getUserMap().getSaveOrLoad().saveMap(os);
                mainFrame.getEnemyInfo().saveInfo(os);
                mainFrame.getUserInfo().saveInfo(os);
            }
        }
    }

    public void loadFromFile() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            mainFrame.addPanels();
            mainFrame.beginGame();

            File file = fileChooser.getSelectedFile();
            FileInputStream fis = new FileInputStream(file);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                mainFrame.getEnemyMap().getSaveOrLoad().loadMap(ois);
                mainFrame.getUserMap().getSaveOrLoad().loadMap(ois);
                mainFrame.getEnemyInfo().loadInfo(ois);
                mainFrame.getUserInfo().loadInfo(ois);
            }
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
