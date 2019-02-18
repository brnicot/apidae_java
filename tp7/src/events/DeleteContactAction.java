package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteContactAction extends AbstractAction {

    private AddressBook ab;

    public DeleteContactAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("contact_delete"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.deleteSelectedContact();
    }
}
