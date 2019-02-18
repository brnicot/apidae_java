package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutAction extends AbstractAction {

    private AddressBook ab;

    public AboutAction(AddressBook ab) {
        this.ab = ab;
        putValue(Action.NAME, ab.getMessages().getString("about"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, ab.getMessages().getString("about_detail"));
    }

}
