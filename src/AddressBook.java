import com.sun.jndi.cosnaming.IiopUrl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Properties;

public class AddressBook extends JFrame {

    private Properties contacts;
    private ContactModel repertoire;
    private JTextPane visualisationInfos;

    public static void main(String[] args) {
        AddressBook ab = new AddressBook();

        ab.initContacts();
        ab.initFrame();
        ab.initWindow();
    }

    private void initContacts() {
        this.contacts = new Properties();
        this.contacts.setProperty("toto", "0506080970");
        this.contacts.setProperty("tata", "8596741258");
    }

    private void initFrame() {
        repertoire = new ContactModel(this.contacts);
        JList<String> liste = new JList<>(repertoire);

        liste.addListSelectionListener(listSelectionEvent -> {
            if(!listSelectionEvent.getValueIsAdjusting()) {
                choixContact(liste.getSelectedValue());
            }
        });

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(liste);
        this.add(scroll, BorderLayout.NORTH);

        visualisationInfos = new JTextPane();
        this.add(visualisationInfos, BorderLayout.CENTER);
    }

    private void choixContact(String selectedContact) {
        setVisualisationInfosText(contacts.getProperty(selectedContact));
    }

    private void setVisualisationInfosText(String s) {
        this.visualisationInfos.setText(s);
    }

    private void initWindow() {
        this.setTitle("Mon répertoire téléphonique");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
