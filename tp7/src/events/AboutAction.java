package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action showing the about dialog
 */
public class AboutAction extends AbstractAction {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;

    /**
     * Instantiates a new About action.
     *
     * @param ab Address Book cross reference
     */
    public AboutAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("about"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    /**
     * Triggered when the about menu item is pressed
     * @param actionEvent Performed event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, ab.getMessages().getString("about_detail"));
    }

}
