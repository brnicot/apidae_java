package toto;

import javax.swing.*;

public class Toto extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut
     */
    private Toto() {
        init();
    }

    /**
     * Main et paramétrage de notre fenêtre
     *
     * @param args arguments de lancement du programme
     */
    public static void main(String[] args) {
        JFrame frame = new Toto();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // améliorer la doc
        // reuse des constructeurs
        // constante de classe pour les couleurs (final)
        // probleme de refresh = utiliser repaint()
        // pas dans le main !!
        JPanel monTotoPanel = new TotoJPanel();
        frame.add(monTotoPanel);

        frame.setVisible(true);
    }

    /**
     * Initialisation de la fenêtre
     */
    private void init() {
        setTitle("Toto");
    }

}
