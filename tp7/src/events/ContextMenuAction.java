package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContextMenuAction extends MouseAdapter {

    private AddressBook ab;
    private JPopupMenu popupMenu;

    public ContextMenuAction(AddressBook ab) {
        this.ab = ab;

        popupMenu = new JPopupMenu();
        JMenuItem deleteContact = new JMenuItem(ab.getMessages().getString("contact_delete"));
        deleteContact.setAction(new DeleteContactAction(ab));
        popupMenu.add(deleteContact);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            ab.chooseContactByLocation(e.getPoint());
            popupMenu.show(ab, (int) ab.getMousePosition().getX(), (int) ab.getMousePosition().getY());
        }
    }

}
