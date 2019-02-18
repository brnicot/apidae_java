package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class ChangeLangAction extends AbstractAction {

    private AddressBook ab;
    private Locale locale;

    public ChangeLangAction(AddressBook ab, Locale l) {
        this.ab = ab;
        this.locale = l;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.changeLocale(locale);
    }
}
