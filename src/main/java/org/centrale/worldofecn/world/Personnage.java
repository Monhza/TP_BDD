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

/**
 * @author ECN
 */
public abstract class Personnage extends Creature {

    /**
     * @param world
     */
    public Personnage(World world) {
        super(world);
    }

    @Override
    public void saveToDatabase(Connection connection, String saveName, int idElement) {

        // D'abord, on entre les coordonnées de l'objet dans la table élément de jeu
        super.saveToDatabase(connection, saveName, idElement);

        String query;
        PreparedStatement stmt;

        // On sauvegarde d'abord la creature dans la table correspondant à sa classe mère
        query = "INSERT INTO humanoide (idelement, idsauvegarde, attaque, pv, distanceattaque, parade, degatspoings)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idElement);
            stmt.setString(2, saveName);
            stmt.setInt(3, 50); // On met des valeurs par defaut pour les variables non fournies
            stmt.setInt(4, 50);
            stmt.setInt(5, 50);
            stmt.setInt(6, 50);
            stmt.setInt(7, 50);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
