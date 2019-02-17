package base;

import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

class ContactModel extends DefaultListModel<String> {

    ContactModel(Properties contacts) {
        contacts.forEach((key, value) -> addElement(key.toString()));
        sort();
    }

    void sort() {
        ArrayList<String> list = Collections.list(this.elements());
        Collections.sort(list);
        this.clear();

        for(String s: list) {
            this.addElement(s);
        }
    }

}
