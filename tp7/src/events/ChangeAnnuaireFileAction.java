package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ChangeAnnuaireFileAction extends AbstractAction {

    private AddressBook ab;

    public ChangeAnnuaireFileAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, "Changer chemin de l'annuaire");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.chooseAnnuairePath();
        ab.triggerSaveEvent();
        JOptionPane.showMessageDialog(null, "Changement effectué avec succès", "Changement chemin annuaire", JOptionPane.INFORMATION_MESSAGE);
    }
}
