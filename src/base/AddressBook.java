package base;

import actions.AboutAction;
import actions.NewContactAction;
import actions.QuitAction;
import actions.SaveAction;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AddressBook extends JFrame {

    private Properties contacts;
    private ContactModel listeNomsContacts;
    private JTextPane visualisationInfos;
    private final Path appConfigFilePath = Paths.get(System.getProperty("user.home"), ".bryan_adressbook", "config.properties");
    private Properties appConfig;

    public static void main(String[] args) {
        AddressBook ab = new AddressBook();

        ab.initFiles();
        ab.initContacts();
        ab.initFrame();
        ab.initWindow();
    }

    private void initFiles() {
        initConfigFile();
        initAnnuaireFile();
    }

    private void initConfigFile() {
        File appConfigFile = new File(String.valueOf(appConfigFilePath));
        appConfigFile.getParentFile().mkdirs();
        appConfig = new Properties();

        if (!appConfigFile.isFile()) {
            try {
                appConfigFile.createNewFile();
                chooseAnnuairePath();
            } catch (IOException e) {
                fatalError("Impossible de créer le fichier de configuration ! Essayer de lancer l'application en mode admin.");
            }
        } else {
            try (InputStream in = new FileInputStream(String.valueOf(appConfigFilePath))) {
                appConfig.load(in);
            } catch (IOException e) {
                fatalError("Erreur lors du chargement de la configuration de l'application.");
            }
        }
    }

    private void fatalError(String s) {
        JOptionPane.showMessageDialog(null, s, "Erreur fatale", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    private void chooseAnnuairePath() {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner LE DOSSIER où sera sauvegardé votre annuaire", "Emplacement de l'annuaire", JOptionPane.QUESTION_MESSAGE);
        JFileChooser browser = new JFileChooser();
        browser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        browser.setDialogTitle("Emplacement du fichier annuaire");
        browser.showSaveDialog(null);

        Path annuairePath = Paths.get(browser.getSelectedFile().getPath(), "annuaire.properties");
        File annuaireFile = new File(String.valueOf(annuairePath));
        try {
            annuaireFile.createNewFile();
        } catch (IOException e) {
            fatalError("Impossible d'écrire le fichier annuaire.");
        }

        appConfig.setProperty("annuairePath", String.valueOf(annuairePath));
        saveConfig();
    }

    private void saveConfig() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(String.valueOf(appConfigFilePath));
            appConfig.store(out, "AdressBook Configuration");
        } catch (IOException e) {
            fatalError("Erreur lors de la sauvegarde de la configuration de l'application.");
        }
    }

    private void initAnnuaireFile() {

        if (appConfig.getProperty("annuairePath") == null) {
            fatalError("Config de l'application invalide.");
        }

        Path annuairePath = Paths.get(appConfig.getProperty("annuairePath"));
        File annuaireFile = new File(String.valueOf(annuairePath));

        if (!annuaireFile.isFile()) {
            fatalError("Fichier annuaire invalide.");
        }

    }

    private void initContacts() {
        this.contacts = new Properties();

        try (InputStream in = new FileInputStream(String.valueOf(Paths.get(appConfig.getProperty("annuairePath"))))) {
            contacts.load(in);
        } catch (IOException e) {
            fatalError("Erreur lors de la lecture des contacts.");
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

    public Path getAnnuairePath() {
        return Paths.get(appConfig.getProperty("annuairePath"));
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
            - modif de l'emplacement du fichier de l'adressbook
            - internationalisation
        A penser pour rendu :
            - doc_private & doc_public
            - REAMDE qui décrit les cours utilisés & infos qu'on juge nécessaire
            - sources (rajouter le dossier src dans le jar NICOT_AddressBook.jar)
            - code propre (surtout gestion des events & exceptions !)
            - tester le jar puis envoyer [PROJET LP] NICOT - AddressBook à fmichel@lirmm.fr
     */
}
