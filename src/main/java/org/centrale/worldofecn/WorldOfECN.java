/* --------------------------------------------------------------------------------
 * ECN Tools
 *
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */

package org.centrale.worldofecn;

import org.centrale.worldofecn.world.World;

/**
 * @author ECN
 */
public class WorldOfECN {

    /**
     * main program
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        World world = new World();
        world.setPlayer("Saegusa");


        // Test phase
        DatabaseTools database = new DatabaseTools();

        // Save world
        database.connect();
        Integer playerId = database.getPlayerID("Saegusa", "Mayumi");
        database.saveWorld(playerId, "new_game", "save_1", world);

        // Retrieve World
        database.readWorld(playerId, "new_game", "save_1", world);

        // Delete World
        database.removeWorld(playerId, "new_game", "save_1");

        database.disconnect();
    }
}
