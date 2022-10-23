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

/**
 * @author kwyhr
 */
public class PotionSoin extends Objet {

    /**
     *
     */
    public PotionSoin(World world) {
        super(world);
    }

    /**
     * @param connection
     */
    @Override
    public void saveToDatabase(Connection connection, String saveName, int idElement) {
        String query;
        PreparedStatement stmt;

        // On sauvegarde d'abord l'objet dans la table correspondant à sa classe mère
        super.saveToDatabase(connection, saveName, idElement);

        //Maintenant, on entre les valeurs de l'instance dans sa classe spécifique
        query = "INSERT INTO objets (idelement, idsauvegarde)\n" +
                "VALUES (?, ?);";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idElement);
            stmt.setString(2, saveName);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     *
     */
    @Override
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
