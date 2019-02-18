package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseAction extends WindowAdapter {

    private AddressBook ab;

    public CloseAction(AddressBook ab) {
        this.ab = ab;
    }

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
