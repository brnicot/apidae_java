package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveAction extends AbstractAction {

    private AddressBook ab;

    public SaveAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME,  ab.getMessages().getString("save"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        FileOutputStream out;

        try {
            out = new FileOutputStream(String.valueOf(ab.getAnnuairePath()));
            ab.getContacts().store(out, "My AdressBook");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, ab.getMessages().getString("error_addressbook_creation"), ab.getMessages().getString("fatal_error"), JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        ab.setSaveableState(false);
    }

}
