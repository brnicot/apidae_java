package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Action triggered on app exit
 */
public class CloseAction extends WindowAdapter {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;

    /**
     * Instantiates a new Close action.
     *
     * @param ab Address book cross reference
     */
    public CloseAction(AddressBook ab) {
        this.ab = ab;
    }

    /**
     * Triggered when the user closes the app and suggests the user to save unsaved changes
     * @param e Performed event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if(ab.getSaveableState()) {
            int choice = JOptionPane.showConfirmDialog(null, ab.getMessages().getString("save_unsaved_changes"), ab.getMessages().getString("unsaved_changes"), JOptionPane.YES_NO_OPTION);
            if(choice == 0) {
                ab.triggerSaveEvent();
            }
        }
    }

}
