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
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class AddressBook extends JFrame {

    private Properties contacts;
    private JList<String> contactsJList;
    private ContactModel contactsNameList;
    private JTextPane infosPanel;
    private final Path appConfigFilePath = Paths.get(System.getProperty("user.home"), ".bryan_addressbook", "config.properties");
    private Properties appConfig;

    // Références car on en a besoin pour modifier la possibilité de sauvegarder & la fermeture
    private JButton saveBtn;
    private SaveAction saveAction;

    // Lang
    private ResourceBundle messages;

    public static void main(String[] args) {
        AddressBook ab = new AddressBook();

        ab.initLang();
        ab.initFiles();
        ab.initContacts();
        ab.initFrame();
        ab.initWindow();
    }

    private void initLang() {
        Locale currentLocale = new Locale("fr", "FR");
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
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
                fatalError(messages.getString("error_config_file_creation"));
            }
        } else {
            try (InputStream in = new FileInputStream(String.valueOf(appConfigFilePath))) {
                appConfig.load(in);
            } catch (IOException e) {
                fatalError(messages.getString("error_config_file_loading"));
            }
        }
    }

    private void fatalError(String s) {
        JOptionPane.showMessageDialog(null, s, messages.getString("error_fatal"), JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    public void chooseAnnuairePath() {
        JOptionPane.showMessageDialog(null, messages.getString("choose_addressbook_dir"), messages.getString("addressbook_dir"), JOptionPane.QUESTION_MESSAGE);
        JFileChooser browser = new JFileChooser();
        browser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        browser.setDialogTitle(messages.getString("addressbook_dir"));
        browser.showSaveDialog(null);

        Path annuairePath = Paths.get(browser.getSelectedFile().getPath(), "annuaire.properties");
        File annuaireFile = new File(String.valueOf(annuairePath));
        try {
            annuaireFile.createNewFile();
        } catch (IOException e) {
            fatalError(messages.getString("error_addressbook_creation"));
        }

        appConfig.setProperty("annuairePath", String.valueOf(annuairePath));
        saveConfig();
    }

    private void saveConfig() {
        FileOutputStream out;
        try {
            out = new FileOutputStream(String.valueOf(appConfigFilePath));
            appConfig.store(out, "AddressBook Configuration");
        } catch (IOException e) {
            fatalError(messages.getString("error_config_file_save"));
        }
    }

    private void initAnnuaireFile() {

        if (appConfig.getProperty("annuairePath") == null) {
            fatalError(messages.getString("error_config_file_invalid"));
        }

        Path annuairePath = Paths.get(appConfig.getProperty("annuairePath"));
        File annuaireFile = new File(String.valueOf(annuairePath));

        if (!annuaireFile.isFile()) {
            fatalError(messages.getString("error_addressbook_invalid"));
        }

    }

    private void initContacts() {
        this.contacts = new Properties();

        try (InputStream in = new FileInputStream(String.valueOf(Paths.get(appConfig.getProperty("annuairePath"))))) {
            contacts.load(in);
        } catch (IOException e) {
            fatalError(messages.getString("error_addressbook_read"));
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
        contactsJList.addMouseListener(new ContextMenuAction(this));

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

        JButton newContact = new JButton(messages.getString("contact_create"));
        newContact.addActionListener(new NewContactAction(this));
        toolbar.add(newContact);

        JButton deleteContact = new JButton(messages.getString("contact_delete"));
        deleteContact.addActionListener(new DeleteContactAction(this));
        toolbar.add(deleteContact);

        saveBtn = new JButton(messages.getString("save"));
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

        JMenu file = new JMenu(messages.getString("file"));
        JMenuItem saveMenuItem, changeAnnuaireFile, about;
        saveMenuItem = new JMenuItem();
        saveAction = new SaveAction(this);
        saveMenuItem.setAction(saveAction);
        changeAnnuaireFile = new JMenuItem();
        changeAnnuaireFile.setAction(new ChangeAnnuaireFileAction(this));
        about = new JMenuItem();
        about.setAction(new AboutAction(this));
        file.add(saveMenuItem);
        file.add(changeAnnuaireFile);
        file.add(about);

        JMenu contacts = new JMenu(messages.getString("contacts"));
        JMenuItem newContact = new JMenuItem();
        newContact.setAction(new NewContactAction(this));
        JMenuItem deleteContact = new JMenuItem();
        deleteContact.setAction(new DeleteContactAction(this));
        contacts.add(newContact);
        contacts.add(deleteContact);


        JMenu lang = new JMenu(messages.getString("lang"));
        ButtonGroup langGroup = new ButtonGroup();

        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Français");
        rbMenuItem.setSelected(true);
        rbMenuItem.addActionListener(new ChangeLangAction(this, new Locale("fr", "FR")));
        langGroup.add(rbMenuItem);
        lang.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("English");
        rbMenuItem.addActionListener(new ChangeLangAction(this, new Locale("en", "US")));
        langGroup.add(rbMenuItem);
        lang.add(rbMenuItem);

        menu.add(file);
        menu.add(contacts);
        menu.add(lang);

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

    public void chooseContactByLocation(Point p) {
        contactsJList.setSelectedIndex(contactsJList.locationToIndex(p));
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void changeLocale(Locale l) {
        setLocale(l);
        messages = ResourceBundle.getBundle("MessagesBundle", l);
        // putain comment on fait
    }

    /*
    TODO :
        Partie "en plus"
            - internationalisation
        A penser pour rendu :
            - javadoc
            - doc_private & doc_public
            - REAMDE qui décrit les cours utilisés & infos qu'on juge nécessaire
            - sources (rajouter le dossier src dans le jar NICOT_AddressBook.jar)
            - tester le jar puis envoyer [PROJET LP] NICOT - AddressBook à fmichel@lirmm.fr
     */
}
