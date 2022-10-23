/* --------------------------------------------------------------------------------
 * WoE
 *
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */

package org.centrale.worldofecn.world;

import java.sql.Connection;

/**
 * @author kwyhr
 */
public abstract class Objet extends ElementDeJeu {

    /**
     * @param world
     */
    public Objet(World world) {
        super(world);
    }

    @Override
    public void saveToDatabase(Connection connection, String saveName, int idElement) {
        super.saveToDatabase(connection, saveName, idElement);
    }
}
