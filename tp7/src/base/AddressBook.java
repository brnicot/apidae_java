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
import java.util.*;

/**
 * Application core class
 */
public class AddressBook extends JFrame {

    /**
     * Properties containing the contact name & contact details
     */
    private Properties contacts;

    /**
     * JList containing contacts name
     */
    private JList<String> contactsJList;

    /**
     * Model linked to the JList containing contacts name
     */
    private ContactModel contactsNameList;

    /**
     * Text panel of the lower part of the frame. It contains the contact details
     */
    private JTextPane infosPanel;

    /**
     * Config file path
     */
    private final Path appConfigFilePath = Paths.get(System.getProperty("user.home"), ".bryan_addressbook", "config.properties");

    /**
     * Application config
     */
    private Properties appConfig;

    /**
     * Components to update on locale change
     */
    private HashMap<AbstractButton, String> translationTable;

    /**
     * Button used to save contacts
     * Reference needed to modify the ability to save or not the changes
     */
    private JButton saveBtn;

    /**
     * Action triggered to save contacts
     * Reference needed to retrieve if the user is able to save the changes or not
     */
    private SaveAction saveAction;

    /**
     * ResourceBundle containing messages in loaded locale
     */
    private ResourceBundle messages;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        AddressBook ab = new AddressBook();

        ab.translationTable = new HashMap<AbstractButton, String>();
        ab.initLang();
        ab.initFiles();
        ab.initContacts();
        ab.initFrame();
        ab.initWindow();
    }

    /**
     * Sets default lang & loading default translations
     */
    private void initLang() {
        Locale currentLocale = new Locale("fr", "FR");
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }

    /**
     * Loads config & address book files
     */
    private void initFiles() {
        initConfigFile();
        initAnnuaireFile();
    }

    /**
     * Method managing the load of the config file and handling different possible errors
     */
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

    /**
     * Showing an error message & closing the program
     * @param s
     */
    private void fatalError(String s) {
        JOptionPane.showMessageDialog(null, s, messages.getString("error_fatal"), JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    /**
     * Show the user a dialog to choose the directory where the address book will be saved
     */
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

    /**
     * Saves the application config on the disk
     */
    private void saveConfig() {
        FileOutputStream out;
        try {
            out = new FileOutputStream(String.valueOf(appConfigFilePath));
            appConfig.store(out, "AddressBook Configuration");
        } catch (IOException e) {
            fatalError(messages.getString("error_config_file_save"));
        }
    }

    /**
     * Loads the address book file & handling errors
     */
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

    /**
     * Loads contacts from address book file
     */
    private void initContacts() {
        this.contacts = new Properties();

        try (InputStream in = new FileInputStream(String.valueOf(Paths.get(appConfig.getProperty("annuairePath"))))) {
            contacts.load(in);
        } catch (IOException e) {
            fatalError(messages.getString("error_addressbook_read"));
        }
    }

    /**
     * Init the main frame containing the JList / Model & the infos panel
     */
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
                if(keyEvent.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                    setContactInfos(getSelectedContact(), infosPanel.getText());
                }
            }
        });

        initToolbar();
    }

    /**
     * Init the application toolbar
     */
    private void initToolbar() {
        JToolBar toolbar = new JToolBar();

        JButton newContact = new JButton(messages.getString("contact_create"));
        translationTable.put(newContact, "contact_create");
        newContact.addActionListener(new NewContactAction(this));
        toolbar.add(newContact);

        JButton deleteContact = new JButton(messages.getString("contact_delete"));
        translationTable.put(deleteContact, "contact_delete");
        deleteContact.addActionListener(new DeleteContactAction(this));
        toolbar.add(deleteContact);

        saveBtn = new JButton(messages.getString("save"));
        translationTable.put(saveBtn, "save");
        saveBtn.addActionListener(saveAction);
        toolbar.add(saveBtn);

        this.add(toolbar, BorderLayout.SOUTH);
        setSaveableState(false);
    }

    /**
     * Init the application window
     */
    private void initWindow() {
        this.setTitle("AddressBook");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new CloseAction(this));
        this.setVisible(true);
    }

    /**
     * Init the application menu and binds it to the app
     */
    private void initMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu(messages.getString("file"));
        translationTable.put(file, "file");

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

        translationTable.put(saveMenuItem, "save");
        translationTable.put(changeAnnuaireFile, "choose_addressbook_path");
        translationTable.put(about, "about");

        JMenu contacts = new JMenu(messages.getString("contacts"));
        translationTable.put(contacts, "contacts");

        JMenuItem newContact = new JMenuItem();
        newContact.setAction(new NewContactAction(this));
        JMenuItem deleteContact = new JMenuItem();
        deleteContact.setAction(new DeleteContactAction(this));
        contacts.add(newContact);
        contacts.add(deleteContact);

        translationTable.put(newContact, "contact_create");
        translationTable.put(deleteContact, "contact_delete");


        JMenu lang = new JMenu(messages.getString("lang"));
        translationTable.put(lang, "lang");
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

    /**
     * Sets the contact details to the given contact name
     * @param selectedValue Contact name
     * @param text Details
     */
    private void setContactInfos(String selectedValue, String text) {
        this.contacts.setProperty(selectedValue, text);
        setSaveableState(true);
    }

    /**
     * Triggered when the selected contact changes ; and call method to display its informations
     * @param selectedContact Selected contact name
     * @see AddressBook#setInfosPanelText(String)
     */
    private void chooseContact(String selectedContact) {
        setInfosPanelText(contacts.getProperty(selectedContact));
    }

    /**
     * Fills the information panel with the given text
     * @param s Text to display in the information panel
     */
    private void setInfosPanelText(String s) {
        this.infosPanel.setText(s);
    }

    /**
     * Gets address book path.
     *
     * @return the annuaire path
     */
    public Path getAnnuairePath() {
        return Paths.get(appConfig.getProperty("annuairePath"));
    }

    /**
     * Gets contacts.
     *
     * @return the contacts
     */
    public Properties getContacts() {
        return contacts;
    }

    /**
     * Add contact.
     *
     * @param nom   contact name
     * @param infos contact details
     */
    public void addContact(String nom, String infos) {
        setContactInfos(nom, infos);
        contactsNameList.addElement(nom);
    }

    /**
     * Sorts contacts list
     */
    public void sort() {
        contactsNameList.sort();
    }

    /**
     * Returns selected contact when called
     * @return Selected contact name
     */
    private String getSelectedContact() {
        return contactsJList.getSelectedValue();
    }

    /**
     * Remove contacts from address book
     * @param i Index of the contact to remove
     */
    private void removeContact(int i) {
        contactsNameList.remove(i);
    }

    /**
     * Delete selected contact.
     */
    public void deleteSelectedContact() {
        getContacts().remove(getSelectedContact());

        int indexASupprimer = contactsJList.getSelectedIndex();
        contactsJList.setSelectedIndex(0);
        removeContact(indexASupprimer);

        setSaveableState(true);
    }

    /**
     * Sets saveable state.
     *
     * @param b true to enable the ability to save ; false otherwise
     */
    public void setSaveableState(boolean b) {
        saveAction.setEnabled(b);
        saveBtn.setEnabled(b);
    }

    /**
     * Gets saveable state.
     *
     * @return the saveable state
     */
    public boolean getSaveableState() {
        return saveAction.isEnabled();
    }

    /**
     * Trigger save event.
     */
    public void triggerSaveEvent() {
        saveAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    /**
     * Choose contact given the mouse location
     *
     * @param p the p
     */
    public void chooseContactByLocation(Point p) {
        contactsJList.setSelectedIndex(contactsJList.locationToIndex(p));
    }

    /**
     * Gets translation messages
     *
     * @return the messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * Change locale.
     *
     * @param l the l
     */
    public void changeLocale(Locale l) {
        messages = ResourceBundle.getBundle("MessagesBundle", l);
        updateSwingComponents();
        revalidate();
    }

    /**
     * Sets buttons & menu items translated text
     */
    private void updateSwingComponents() {
        Set componentSet = translationTable.entrySet();

        for (Object o : componentSet) {
            Map.Entry mentry = (Map.Entry) o;
            AbstractButton c = (AbstractButton) mentry.getKey();
            c.setText(messages.getString(mentry.getValue().toString()));
        }
    }

    /*
    TODO :
        A penser pour rendu :
            - REAMDE qui décrit les cours utilisés & infos qu'on juge nécessaire
            - sources (rajouter le dossier src dans le jar NICOT_AddressBook.jar)
            - tester le jar puis envoyer [PROJET LP] NICOT - AddressBook à fmichel@lirmm.fr
     */
}
