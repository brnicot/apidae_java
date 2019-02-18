package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action triggered when the user deletes a contact
 */
public class DeleteContactAction extends AbstractAction {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;

    /**
     * Instantiates a new Delete contact action.
     *
     * @param ab Address book cross reference
     */
    public DeleteContactAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("contact_delete"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    /**
     * Deletes currently selected contact
     * @param actionEvent Performed event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.deleteSelectedContact();
    }
}
