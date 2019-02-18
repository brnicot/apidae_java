package events;

import base.AddressBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 * Action triggered when the user changes the app language
 */
public class ChangeLangAction extends AbstractAction {

    /**
     * Cross reference to the address book
     */
    private AddressBook ab;

    /**
     * Locale to use when triggered
     */
    private Locale locale;

    /**
     * Instantiates a new Change lang action.
     *
     * @param ab Address book cross reference
     * @param l  Locale to use when triggered
     */
    public ChangeLangAction(AddressBook ab, Locale l) {
        this.ab = ab;
        this.locale = l;
    }

    /**
     * Triggers the app language change
     * @param actionEvent Performed event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ab.changeLocale(locale);
    }
}
