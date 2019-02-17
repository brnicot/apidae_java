package dessin;

import java.awt.*;

public class Cercle extends ObjetGraphique {

    /**
     * Point représentant le centre du cercle
     */
    private Point center;


    /**
     * Rayon du cercle
     */
    private int radius;

    /**
     * Constructeur prenant un point comme centre du cercle et un rayon
     *
     * @param centre Point étant le centre de notre cercle
     * @param rayon  Rayon de notre cercle
     */
    public Cercle(Point centre, int rayon) {
        this.center = centre;
        this.radius = rayon;
    }

    /**
     * Constructeur prenant un nom pour le cercle, un point comme centre et le rayon
     *
     * @param name   Nom de l'objet graphique
     * @param centre Point étant le centre de notre cercle
     * @param rayon  Rayon de notre cercle
     */
    public Cercle(String name, Point centre, int rayon) {
        this.setNom(name);
        this.center = centre;
        this.radius = rayon;
    }

    /**
     * Constructeur prenant un point comme centre du cercle, un rayon, et une couleur
     *
     * @param centre  Point étant le centre du cercle
     * @param rayon   Rayon du cercle
     * @param couleur Couleur du cercle
     */
    public Cercle(Point centre, int rayon, Color couleur) {
        this(centre, rayon);
        this.setCouleur(couleur);
    }

    /**
     * Dessine le cercle dans le graphique spécifié en paramètre en prenant en compte sa couleur
     *
     * @param graphique Graphique où dessiner le cercle
     * @see dessin.ObjetGraphique#dessineToi(java.awt.Graphics)
     * @see Graphics#drawOval(int, int, int, int)
     */
    @Override
    public void dessineToi(Graphics graphique) {
        graphique.setColor(getCouleur());
        graphique.drawOval((int) this.center.getX() - this.radius, (int) this.center.getY() - this.radius, this.radius * 2, this.radius * 2);
    }

    /**
     * Permet de déterminer si un point fait parti du cercle
     *
     * @param x Coordonnée x du point
     * @param y Coordonnée y du point
     * @see dessin.ObjetGraphique#contient(int, int)
     */
    @Override
    public boolean contient(int x, int y) {
        return (Math.pow(x - this.center.getX(), 2) + Math.pow(y - this.center.getY(), 2)) <= Math.pow(this.radius, 2);
    }
}
