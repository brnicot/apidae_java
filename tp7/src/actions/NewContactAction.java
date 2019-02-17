package actions;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewContactAction extends AbstractAction {

    private AddressBook ab;

    public NewContactAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, "Créer un contact");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String nom = JOptionPane.showInputDialog(null, "Nom du contact", "Création d'un contact", JOptionPane.QUESTION_MESSAGE);
        String infos = JOptionPane.showInputDialog(null, "Informations du contact", "Création d'un contact", JOptionPane.QUESTION_MESSAGE);

        ab.addContact(nom, infos);
        ab.sort();
    }

}
