/* --------------------------------------------------------------------------------
 * WoE
 *
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */

package org.centrale.worldofecn.world;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

/**
 * @author ECN
 */
public abstract class ElementDeJeu {
    private Point2D position;

    /**
     * generate element in the world
     *
     * @param world
     */
    public ElementDeJeu(World world) {
        super();

        Random rand = new Random();
        this.position = new Point2D(rand.nextInt(world.getWidth()), rand.nextInt(world.getHeight()));
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    /**
     * @param connection
     */
    public void saveToDatabase(Connection connection, String saveName, int idElement) {
        String query;
        PreparedStatement stmt;

        // D'abord, on entre les coordonnées de l'objet dans la table correspondante
        query = "INSERT INTO elementdejeu (idelement, idsauvegarde, positionx, positiony)\n" +
                "VALUES (?, ?, ?, ?);";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idElement);
            stmt.setString(2, saveName);
            stmt.setInt(3, this.getPosition().getX());
            stmt.setInt(4, this.getPosition().getY());


            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     */
    public void getFromDatabase(ResultSet rs) {

        int Xtemp = 0;
        int Ytemp = 0;

        try {
            Xtemp = rs.getInt("positionx");
            Ytemp = rs.getInt("positiony");

        } catch (Exception e) {
            System.out.println(e);
        }


        this.setPosition(new Point2D(Xtemp, Ytemp));

        // Nous n'avons pas encore implémenté les autres paramètres qu'il sera donc
        // Inutile de donner à l'instance
    }

}
