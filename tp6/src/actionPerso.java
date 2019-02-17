import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class actionPerso extends AbstractAction {

    public actionPerso() {
        putValue(Action.NAME, "A text-only menu item");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }
}
