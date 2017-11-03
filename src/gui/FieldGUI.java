package gui;

import java.awt.event.MouseListener;
import javax.swing.JButton;

public class FieldGUI extends JButton {

    private Field field;

    FieldGUI(int row, int column) {
        field = new Field(row, column);
    }

    public void removeAllMouseListeners() {
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
