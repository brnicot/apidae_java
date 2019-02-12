package actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class QuitAction extends AbstractAction {

    public QuitAction() {
        putValue(Action.NAME, "Quitter");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

}
