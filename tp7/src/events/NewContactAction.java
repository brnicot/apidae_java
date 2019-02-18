package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action triggered when the user creates a new contact
 */
public class NewContactAction extends AbstractAction {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;


    /**
     * Instantiates a new New contact action.
     *
     * @param ab Address book cross reference
     */
    public NewContactAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("contact_create"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    /**
     * Adds the new contact & sorts the contacts list
     * @param actionEvent Performed event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String nom = JOptionPane.showInputDialog(null, ab.getMessages().getString("contact_name"), ab.getMessages().getString("contact_create"), JOptionPane.QUESTION_MESSAGE);
        String infos = JOptionPane.showInputDialog(null, ab.getMessages().getString("contact_detail"), ab.getMessages().getString("contact_create"), JOptionPane.QUESTION_MESSAGE);

        ab.addContact(nom, infos);
        ab.sort();
    }

}
