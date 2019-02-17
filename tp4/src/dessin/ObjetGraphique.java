package dessin;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ObjetGraphique {

    /**
     * Liste des couleurs possibles pour un objet graphique
     */
    public static ArrayList<Color> listeCouleurs = new ArrayList<Color>(
            Arrays.asList(Color.black, Color.blue, Color.green, Color.red, Color.orange, Color.white)
    );
    /**
     * Nom de l'objet graphique
     *
     * @see ObjetGraphique#getNom()
     * @see ObjetGraphique#setNom(String)
     */
    private String nom;
    /**
     * Couleur de l'objet graphique
     *
     * @see ObjetGraphique#getCouleur()
     * @see ObjetGraphique#setCouleur(Color)
     */
    private Color couleur;
    /**
     * État de l'objet (affiché ou non)
     *
     * @see ObjetGraphique#getEtat()
     * @see ObjetGraphique#permuterEtat()
     */
    private boolean estActif;

    /**
     * Constructeur vide créant un objet graphique noir actif
     */
    public ObjetGraphique() {
        super();
        this.couleur = Color.black;
        this.estActif = true;
    }

    /**
     * Constructeur permettant la création d'un objet graphique de la couleur passée en paramètre
     *
     * @param couleur Couleur souhaitée
     */
    public ObjetGraphique(Color couleur) {
        super();
        this.couleur = couleur;
    }

    /**
     * Permute l'état de l'objet graphique
     *
     * @see ObjetGraphique#estActif
     * @see ObjetGraphique#getEtat()
     */
    public void permuterEtat() {
        this.estActif = !this.estActif;
    }

    /**
     * Retourne l'état courant de notre objet graphique
     *
     * @see ObjetGraphique#estActif
     * @see ObjetGraphique#permuterEtat()
     */
    public boolean getEtat() {
        return this.estActif;
    }

    /**
     * @return Nom de l'objet graphique
     * @see ObjetGraphique#nom
     * @see ObjetGraphique#setNom(String)
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom Nouveau nom de l'objet graphique
     * @see ObjetGraphique#nom
     * @see ObjetGraphique#getNom()
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return Couleur actuelle de l'objet graphique
     */
    public Color getCouleur() {
        return couleur;
    }

    /**
     * Change la couleur de l'objet graphique
     *
     * @param couleur Couleur désirée pour notre objet graphique
     */
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    /**
     * Permet de dessiner les différents objets graphiques
     *
     * @param graphique Graphique cible où dessiner les objets
     */
    public abstract void dessineToi(Graphics graphique);


    /**
     * Permet de déterminer si un point fait parti de l'objet graphique
     *
     * @param x Coordonnée X du point
     * @param y Coordonnée Y du point
     * @return Vrai si le point donné est dans notre objet graphique, faux sinon
     */
    public abstract boolean contient(int x, int y);

}
