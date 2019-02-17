package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseAction extends WindowAdapter {

    private AddressBook ab;

    public CloseAction(AddressBook ab) {
        this.ab = ab;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(ab.saveAction.isEnabled()) {
            int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous enregistrer les modifications effectuées ?", "Modifications non sauvegardées", JOptionPane.YES_NO_OPTION);
            if(choice == 0) {
                ab.saveAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        }
    }

}
