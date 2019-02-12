package base;

import actions.AboutAction;
import actions.NewContactAction;
import actions.QuitAction;
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

    private void initWindow() {
        this.setTitle("AddressBook");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("Ficher");
        JMenuItem save, about, quit;
        save = new JMenuItem();
        save.setAction(new SaveAction(this));
        about = new JMenuItem();
        about.setAction(new AboutAction());
        quit = new JMenuItem();
        quit.setAction(new QuitAction());
        file.add(save);
        file.add(about);

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



    public void addContact(String nom, String infos) {
        setContact(nom, infos);
        listeNomsContacts.addElement(nom);
    }

    public Properties getContacts() {
        return contacts;
    }

    /*
    TODO :
        Partie TP
            - ex11 : quand on quitte, si modifs, alors demander si on veut sauvegarder
                listen de l'event du bouton & de l'action !!
            - ex12/13 : toolbar avec les mêmes actions que dans la menubar
            - ex14 : trier la liste des entrées du répertoire par ordre alphabétique (cf collections)
            - ex15 : menu popup comme dans la démo (??)
        Partie "en plus"
            - choix de l'emplacement du fichier de l'adressbook
            - internationalisation
        A penser pour rendu :
            - doc_private & doc_public
            - REAMDE qui décrit les cours utilisés & infos qu'on juge nécessaire
            - sources (rajouter le dossier src dans le jar NICOT_AddressBook.jar)
            - code propre (surtout gestion des events & exceptions !)
            - tester le jar puis envoyer [PROJET LP] NICOT - AddressBook à fmichel@lirmm.fr
     */
}
