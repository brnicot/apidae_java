package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action triggered when the user has or wants to change the address book path
 */
public class ChangeAnnuaireFileAction extends AbstractAction {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;

    /**
     * Instantiates a new Change annuaire file action.
     *
     * @param ab Address book cross reference
     */
    public ChangeAnnuaireFileAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("choose_addressbook_path"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
    }

    /**
     * Triggered when the change address book path menu item is pressed
     * @param actionEvent Performed event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.chooseAnnuairePath();
        ab.triggerSaveEvent();
        JOptionPane.showMessageDialog(null, ab.getMessages().getString("change_done"), ab.getMessages().getString("choose_addressbook_path"), JOptionPane.INFORMATION_MESSAGE);
    }
}
