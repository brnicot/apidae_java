package toto;

import dessin.Cercle;
import dessin.ObjetGraphique;
import dessin.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class TotoJPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Liste contenant les objets graphiques de notre panel
     */
    private ArrayList<ObjetGraphique> figures = new ArrayList<ObjetGraphique>();


    /**
     * Constructeur vide créant la tête à toto, gère la création des boutons et des clics souris
     */
    public TotoJPanel() {
        // Les éléments doivent être ordonnés du plus petit au plus grand
        figures.add(new Cercle("left_eye", new Point(160, 150), 20));
        figures.add(new Cercle("right_eye", new Point(240, 150), 20));
        figures.add(new Rectangle("mouth", 150, 220, 100, 40));
        figures.add(new Cercle("head", new Point(200, 200), 100));

        creationBoutons();
        gestionSouris();
    }

    /**
     * Gère les évènements liés à la souris
     * @see Component#addMouseListener(MouseListener)
     * @see MouseAdapter
     * @see MouseEvent
     */
    private void gestionSouris() {
        addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        super.mouseClicked(mouseEvent);
                        gestionObjetClique(mouseEvent);
                    }
                }
        );
    }

    /**
     * Fait changer la couleur de l'objet graphique concerné par le clic de la souris et rafraîchit la frame
     * @param me évènement de la souris
     * @see ObjetGraphique#listeCouleurs
     * @see ObjetGraphique#setCouleur(Color)
     * @see TotoJPanel#refreshParentFrame()
     */
    private void gestionObjetClique(MouseEvent me) {
        for (ObjetGraphique o : figures) {
            if (o.contient(me.getX(), me.getY())) {
                ArrayList<Color> listeCouleurs = ObjetGraphique.listeCouleurs;
                Color targetColor = listeCouleurs.get((listeCouleurs.indexOf(o.getCouleur()) + 1) % listeCouleurs.size());

                o.setCouleur(targetColor);
                break;
            }
        }

        refreshParentFrame();
    }

    /**
     * Permet de dessiner tous les objets graphiques actifs de notre panel
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * @see TotoJPanel#figures
     * @see ObjetGraphique#getEtat()
     * @see ObjetGraphique#dessineToi(Graphics)
     */
    public void paintComponent(Graphics g) {
        for (ObjetGraphique o : figures) {
            if (o.getEtat()) {
                o.dessineToi(g);
            }
        }
    }

    /**
     * Crée un bouton par objet graphique de notre panel permettant d'activer ou désactiver ceux-ci, et gère les events de ces boutons
     * @see JButton
     * @see java.awt.event.ActionListener
     */
    private void creationBoutons() {
        for (ObjetGraphique o : figures) {
            JButton currButton = new JButton(o.getNom());
            this.add(currButton);

            currButton.addActionListener(
                    e -> {
                        JButton pressedButton = (JButton) e.getSource();
                        String bodypart = pressedButton.getActionCommand();

                        rechercheObjetAPermuter(bodypart);
                    }
            );
        }
    }

    /**
     * Change l'état d'un objet graphique à partir du nom de celui-ci puis rafraîchit la frame
     * @param bodypart Nom de l'objet graphique à permuter d'état
     */
    private void rechercheObjetAPermuter(String bodypart) {
        for (ObjetGraphique o1 : figures) {
            if (o1.getNom().equals(bodypart)) {
                o1.permuterEtat();
                break;
            }
        }

        refreshParentFrame();
    }

    /**
     * Rafraîchit la frame parente de notre JPanel
     */
    private void refreshParentFrame() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        SwingUtilities.updateComponentTreeUI(parent);
    }

}
