import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("./addressbook.ab");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ab.contacts.store(out, "My AdressBook");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
