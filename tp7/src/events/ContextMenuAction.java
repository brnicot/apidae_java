package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Action triggered when the user right-clicks on the contacts list
 */
public class ContextMenuAction extends MouseAdapter {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;

    /**
     * Popup menu to display on right click
     */
    private JPopupMenu popupMenu;

    /**
     * Instantiates a new Context menu action.
     *
     * @param ab Address book cross reference
     */
    public ContextMenuAction(AddressBook ab) {
        this.ab = ab;

        popupMenu = new JPopupMenu();
        JMenuItem deleteContact = new JMenuItem(ab.getMessages().getString("contact_delete"));
        deleteContact.setAction(new DeleteContactAction(ab));
        popupMenu.add(deleteContact);
    }

    /**
     * Choose the contact depending of the mouse location & displays the popup menu
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            ab.chooseContactByLocation(e.getPoint());
            popupMenu.show(ab, (int) ab.getMousePosition().getX(), (int) ab.getMousePosition().getY());
        }
    }

}
