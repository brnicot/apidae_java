import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

public class tpmenu extends JFrame {

    public static void main(String[] args) {
        tpmenu t = new tpmenu();

        t.initMenu();
        //t.initToolbar();
        t.initWindow();
    }

    public void initWindow() {
        this.setTitle("TP Menus");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void initToolbar() {
        JToolBar jtb = new JToolBar();
        jtb.add(new JButton(new actionPerso()));
        add(jtb);
    }

    public void initMenu() {
        JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;

        i1 = new JMenuItem("A text-only menu item");
        i1.setAction(new actionPerso());

        i2 = new JMenuItem("Both text and icon", new ImageIcon("icon.gif"));
        i3 = new JMenuItem(" ", new ImageIcon("icon.gif"));
        i4 = new JRadioButtonMenuItem("A radio button menu item");
        i5 = new JRadioButtonMenuItem("Another one");
        i6 = new JCheckBoxMenuItem("A check box menu item");
        i7 = new JCheckBoxMenuItem("Another one");
        i8 = new JMenu("A submenu");
        i9 = new JMenuItem("An item in the submenu");
        i10 = new JMenuItem("Another item");

        JMenu aMenu, anotherMenu;
        aMenu = new JMenu("A Menu");
        aMenu.setMnemonic('A');
        anotherMenu = new JMenu("Another menu");

        aMenu.add(i1);
        aMenu.add(i2);
        aMenu.add(i3);
        aMenu.addSeparator();
        aMenu.add(i4);
        aMenu.add(i5);
        aMenu.addSeparator();
        aMenu.add(i6);
        aMenu.add(i7);
        aMenu.addSeparator();
        aMenu.add(i8);

        i8.add(i9);
        i8.add(i10);

        JMenuBar maBarre = new JMenuBar();
        maBarre.add(aMenu);
        maBarre.add(anotherMenu);

        setJMenuBar(maBarre);


    }

}
