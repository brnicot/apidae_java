package dessin;

import java.awt.*;

public class Rectangle extends ObjetGraphique {

    /**
     * Attribut Rectangle de Java encapsulé
     *
     * @see java.awt.Rectangle
     */
    private java.awt.Rectangle forme;

    /**
     * Constructeur prenant le point de coordonnée x et y comme origine du rectangle ainsi qu'une largeur et hauteur
     *
     * @param x       Coordonnée X de l'origine du rectangle
     * @param y       Coordonnée Y de l'origine du rectangle
     * @param largeur Largeur du rectangle
     * @param hauteur Hauteur du rectangle
     */
    public Rectangle(int x, int y, int largeur, int hauteur) {
        this.forme = new java.awt.Rectangle(x, y, largeur, hauteur);
    }

    /**
     * Constructeur prenant le nom du rectangle, le point de coordonnée x et y comme origine du rectangle, ainsi que la largeur et hauteur de celui-ci
     *
     * @param name    Nom de l'objet graphique
     * @param x       Coordonnée X de l'origine du rectangle
     * @param y       Coordonnée Y de l'origine du rectangle
     * @param largeur Largeur du rectangle
     * @param hauteur Hauteur du rectangle
     * @see Rectangle#Rectangle(int, int, int, int)
     */
    public Rectangle(String name, int x, int y, int largeur, int hauteur) {
        this(x, y, largeur, hauteur);
        this.setNom(name);
    }

    /**
     * Constructeur prenant un point comme origine du rectangle, ainsi que la largeur et hauteur du rectangle
     *
     * @param p       Origine du rectangle
     * @param largeur Largeur du rectangle
     * @param hauteur Hauteur du rectangle
     * @see Rectangle#Rectangle(int, int, int, int)
     */
    public Rectangle(Point p, int largeur, int hauteur) {
        this((int) p.getX(), (int) p.getY(), largeur, hauteur);
    }

    /**
     * Constructeur prenant un point comme origine du rectangle, ainsi que la largeur et hauteur du rectangle et sa couleur
     *
     * @param p       Origine du rectangle
     * @param largeur Largeur du rectangle
     * @param hauteur Hauteur du rectangle
     * @param c       Couleur du rectangle
     * @see Rectangle#Rectangle(int, int, int, int)
     * @see Rectangle#setCouleur(Color)
     */
    public Rectangle(Point p, int largeur, int hauteur, Color c) {
        this((int) p.getX(), (int) p.getY(), largeur, hauteur);
        this.setCouleur(c);
    }

    /**
     * Constructeur vide
     *
     * @see ObjetGraphique#ObjetGraphique()
     * @see Rectangle#forme
     * @see java.awt.Rectangle
     */
    public Rectangle() {
        super();
        this.forme = new java.awt.Rectangle();
    }

    /**
     * Dessine le rectangle en tenant compte de sa couleur dans le graphique spécifié
     *
     * @param graphique Graphique cible
     * @see dessin.ObjetGraphique#dessineToi(java.awt.Graphics)
     * @see Graphics#drawRect(int, int, int, int)
     */
    @Override
    public void dessineToi(Graphics graphique) {
        graphique.setColor(getCouleur());
        graphique.drawRect((int) this.forme.getX(), (int) this.forme.getY(), (int) this.forme.getWidth(), (int) this.forme.getHeight());
    }

    /**
     * Permet de déterminer si un point fait parti du rectangle
     *
     * @param x Coordonnée x du point
     * @param y Coordonnée y du point
     * @see dessin.ObjetGraphique#contient(int, int)
     * @see java.awt.Rectangle#contains(int, int)
     */
    @Override
    public boolean contient(int x, int y) {
        return this.forme.contains(x, y);
    }

}
