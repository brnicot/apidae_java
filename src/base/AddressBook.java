package base;

import actions.NewContactAction;
import actions.SaveAction;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AddressBook extends JFrame {

    private Properties contacts;
    private ContactModel listeNomsContacts;
    private JTextPane visualisationInfos;

    public static void main(String[] args) {
        AddressBook ab = new AddressBook();

        ab.initContacts();
        ab.initFrame();
        ab.initWindow();
    }

    private void initContacts() {
        this.contacts = new Properties();

        try (InputStream in = new FileInputStream("./addressbook.ab")) {
            contacts.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFrame() {
        initMenu();

        listeNomsContacts = new ContactModel(this.contacts);
        JList<String> listeContacts = new JList<>(listeNomsContacts);

        listeContacts.addListSelectionListener(listSelectionEvent -> {
            if (!listSelectionEvent.getValueIsAdjusting()) {
                choixContact(listeContacts.getSelectedValue());
            }
        });

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(listeContacts);
        this.add(scroll, BorderLayout.NORTH);

        visualisationInfos = new JTextPane();
        this.add(visualisationInfos, BorderLayout.CENTER);

        visualisationInfos.addCaretListener(caretEvent ->
                setContact(listeContacts.getSelectedValue(), visualisationInfos.getText())
        );
    }

    private void initMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("Ficher");
        JMenuItem save = new JMenuItem();
        save.setAction(new SaveAction(this));
        file.add(save);

        JMenu contacts = new JMenu("Contacts");
        JMenuItem new_contact = new JMenuItem();
        new_contact.setAction(new NewContactAction(this));
        contacts.add(new_contact);

        menu.add(file);
        menu.add(contacts);

        setJMenuBar(menu);
    }

    private void setContact(String selectedValue, String text) {
        this.contacts.setProperty(selectedValue, text);
    }

    private void choixContact(String selectedContact) {
        setVisualisationInfosText(contacts.getProperty(selectedContact));
    }

    private void setVisualisationInfosText(String s) {
        this.visualisationInfos.setText(s);
    }

    private void initWindow() {
        this.setTitle("AddressBook v0.0.1 - Bryan NICOT");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addContact(String nom, String infos) {
        setContact(nom, infos);
        listeNomsContacts.addElement(nom);
    }

    public Properties getContacts() {
        return contacts;
    }
}
