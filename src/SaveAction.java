import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAction extends AbstractAction {

    private AddressBook ab;

    SaveAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, "Sauvegarder");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

}
