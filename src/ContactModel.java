import javax.swing.DefaultListModel;
import java.util.Properties;

public class ContactModel extends DefaultListModel<String> {

    public ContactModel(Properties contacts) {
        contacts.forEach((key, value) -> addElement(key.toString()));
    }

}
