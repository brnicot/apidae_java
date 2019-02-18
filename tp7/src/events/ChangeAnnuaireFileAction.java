package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ChangeAnnuaireFileAction extends AbstractAction {

    private AddressBook ab;

    public ChangeAnnuaireFileAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("choose_addressbook_path"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.chooseAnnuairePath();
        ab.triggerSaveEvent();
        JOptionPane.showMessageDialog(null, ab.getMessages().getString("change_done"), ab.getMessages().getString("choose_addressbook_path"), JOptionPane.INFORMATION_MESSAGE);
    }
}
