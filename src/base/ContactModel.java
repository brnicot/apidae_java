package base;

import javax.swing.DefaultListModel;
import java.util.Properties;

class ContactModel extends DefaultListModel<String> {

    ContactModel(Properties contacts) {
        contacts.forEach((key, value) -> addElement(key.toString()));
    }

}
