package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteContactAction extends AbstractAction {

    private AddressBook ab;

    public DeleteContactAction(AddressBook ab) {
        this.ab = ab;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
