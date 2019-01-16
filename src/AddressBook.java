import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

        try (InputStream in = new FileInputStream("./addressbook.ab")) {
            contacts.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFrame() {
        initMenu();

        repertoire = new ContactModel(this.contacts);
        JList<String> liste = new JList<>(repertoire);

        liste.addListSelectionListener(listSelectionEvent -> {
            if (!listSelectionEvent.getValueIsAdjusting()) {
                choixContact(liste.getSelectedValue());
            }
        });

        KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        InputMap listeInputMap = liste.getInputMap();
        listeInputMap.put(ctrlA, "none");

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(liste);
        this.add(scroll, BorderLayout.NORTH);

        visualisationInfos = new JTextPane();
        this.add(visualisationInfos, BorderLayout.CENTER);

        visualisationInfos.addCaretListener(caretEvent ->
                updateContact(liste.getSelectedValue(), visualisationInfos.getText())
        );
    }

    private void initMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("Ficher");
        menu.add(file);

        JMenu contacts = new JMenu("Contacts");
        JMenuItem new_contact = new JMenuItem("Nouveau contact");
        new_contact.setAction(new NewContactAction(this));

        menu.add(contacts);
        contacts.add(new_contact);

        setJMenuBar(menu);
    }

    private void updateContact(String selectedValue, String text) {
        this.contacts.setProperty(selectedValue, text);
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

    void addContact(String nom, String infos) {
        updateContact(nom, infos);
        repertoire.addElement(nom);
    }
}
