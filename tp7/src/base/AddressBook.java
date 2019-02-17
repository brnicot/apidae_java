package base;

import events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AddressBook extends JFrame {

    private Properties contacts;
    private JList<String> contactsJList;
    private ContactModel contactsNameList;
    private JTextPane infosPanel;
    private final Path appConfigFilePath = Paths.get(System.getProperty("user.home"), ".bryan_adressbook", "config.properties");
    private Properties appConfig;

    // Références car on en a besoin pour modifier la possibilité de sauvegarder & la fermeture
    private JButton saveBtn;
    private SaveAction saveAction;

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
        JOptionPane.showMessageDialog(null, s, "Erreur fatale !", JOptionPane.ERROR_MESSAGE);
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
        FileOutputStream out;
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

        contactsNameList = new ContactModel(this.contacts);
        contactsJList = new JList<>(contactsNameList);

        contactsJList.addListSelectionListener(listSelectionEvent -> {
            // on s'assure que la valeur n'est pas null car sinon quand on sort() la sélection se fait sur un truc null et tout plante
            if (!listSelectionEvent.getValueIsAdjusting() && getSelectedContact() != null) {
                chooseContact(getSelectedContact());
            }
        });

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(contactsJList);
        this.add(scroll, BorderLayout.NORTH);

        infosPanel = new JTextPane();
        this.add(infosPanel, BorderLayout.CENTER);

        infosPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                setContactInfos(getSelectedContact(), infosPanel.getText());
            }
        });

        initToolbar();
    }

    private void initToolbar() {
        JToolBar toolbar = new JToolBar();

        JButton newContact = new JButton("Créer un contact");
        newContact.addActionListener(new NewContactAction(this));
        toolbar.add(newContact);

        JButton deleteContact = new JButton("Supprimer le contact");
        deleteContact.addActionListener(new DeleteContactAction(this));
        toolbar.add(deleteContact);

        saveBtn = new JButton("Sauvegarder");
        saveBtn.addActionListener(saveAction);
        toolbar.add(saveBtn);

        this.add(toolbar, BorderLayout.SOUTH);
        setSaveableState(false);
    }

    private void initWindow() {
        this.setTitle("AddressBook");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new CloseAction(this));
        this.setVisible(true);
    }

    private void initMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("Ficher");
        JMenuItem saveMenuItem, about;
        saveMenuItem = new JMenuItem();
        saveAction = new SaveAction(this);
        saveMenuItem.setAction(saveAction);
        about = new JMenuItem();
        about.setAction(new AboutAction());
        file.add(saveMenuItem);
        file.add(about);

        JMenu contacts = new JMenu("Contacts");
        JMenuItem newContact = new JMenuItem();
        newContact.setAction(new NewContactAction(this));
        JMenuItem deleteContact = new JMenuItem();
        deleteContact.setAction(new DeleteContactAction(this));
        contacts.add(newContact);
        contacts.add(deleteContact);

        menu.add(file);
        menu.add(contacts);

        setJMenuBar(menu);
    }

    private void setContactInfos(String selectedValue, String text) {
        this.contacts.setProperty(selectedValue, text);
        setSaveableState(true);
    }

    private void chooseContact(String selectedContact) {
        setInfosPanelText(contacts.getProperty(selectedContact));
    }

    private void setInfosPanelText(String s) {
        this.infosPanel.setText(s);
    }

    public Path getAnnuairePath() {
        return Paths.get(appConfig.getProperty("annuairePath"));
    }

    public Properties getContacts() {
        return contacts;
    }

    public void addContact(String nom, String infos) {
        setContactInfos(nom, infos);
        contactsNameList.addElement(nom);
    }

    public void sort() {
        contactsNameList.sort();
    }

    private String getSelectedContact() {
        return contactsJList.getSelectedValue();
    }

    private void removeContact(int i) {
        contactsNameList.remove(i);
    }

    public void deleteSelectedContact() {
        getContacts().remove(getSelectedContact());

        int indexASupprimer = contactsJList.getSelectedIndex();
        contactsJList.setSelectedIndex(0);
        removeContact(indexASupprimer);

        setSaveableState(true);
    }

    public void setSaveableState(boolean b) {
        saveAction.setEnabled(b);
        saveBtn.setEnabled(b);
    }

    public boolean getSaveableState() {
        return saveAction.isEnabled();
    }

    public void triggerSaveEvent() {
        saveAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    /*
    TODO :
        Partie Tp
            - icones
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
