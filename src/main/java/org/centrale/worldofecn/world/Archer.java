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
 * @author ECN
 */
public class Archer extends Personnage {

    /**
     * @param world
     */
    public Archer(World world) {
        super(world);
    }

    /**
     * @param connection
     */
    @Override
    public void saveToDatabase(Connection connection, String saveName, int idElement) {
        String query;
        PreparedStatement stmt;

        // On sauvegarde d'abord la creature dans la table correspondant à sa classe mère
        super.saveToDatabase(connection, saveName, idElement);


        //Maintenant, on entre les valeurs de l'instance dans sa classe spécifique
        query = "INSERT INTO archer (idelement, idsauvegarde, nbfleches)\n" +
                "VALUES (?, ?, ?);";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idElement);
            stmt.setString(2, saveName);
            stmt.setInt(3, 50); // On met des valeurs par defaut pour les variables non fournies

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
        super.getFromDatabase(rs);
    }
}
