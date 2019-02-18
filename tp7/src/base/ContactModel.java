package base;

import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * Customized DefaultListModel to handle contacts loading & sorting
 * @see javax.swing.DefaultListSelectionModel
 */
class ContactModel extends DefaultListModel<String> {

    /**
     * Instantiates a new Contact model.
     *
     * @param contacts the contacts
     */
    ContactModel(Properties contacts) {
        contacts.forEach((key, value) -> addElement(key.toString()));
        sort();
    }

    /**
     * Sort contacts list
     */
    void sort() {
        ArrayList<String> list = Collections.list(this.elements());
        Collections.sort(list);
        this.clear();

        for(String s: list) {
            this.addElement(s);
        }
    }

}
