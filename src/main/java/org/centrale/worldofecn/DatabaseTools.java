/* --------------------------------------------------------------------------------
 * WoE Tools
 * 
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */
package org.centrale.worldofecn;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

import org.centrale.worldofecn.world.World;

/**
 *
 * @author ECN
 */
public class DatabaseTools {

    private String login;
    private String password;
    private String url;
    private Connection connection;

    /**
     * Load infos
     */
    public DatabaseTools() {
        try {
            // Get Properties file
            ResourceBundle properties = ResourceBundle.getBundle(DatabaseTools.class.getPackage().getName() + ".database");

            // USE config parameters
            login = properties.getString("login");
            password = properties.getString("password");
            String server = properties.getString("server");
            String database = properties.getString("database");
            url = "jdbc:postgresql://" + server + "/" + database;

            // Mount driver
            Driver driver = DriverManager.getDriver(url);
            if (driver == null) {
                Class.forName("org.postgresql.Driver");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.connection = null;
    }

    /**
     * Get connection to the database
     */
    public void connect() {
        if (this.connection == null) {
            try {
                this.connection = DriverManager.getConnection(url, login, password);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Disconnect from database
     */
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * get Player ID
     * @param nomJoueur
     * @param password
     * @return
     */
    public Integer getPlayerID(String nomJoueur, String password) {

        String query = "SELECT clesteam FROM joueur WHERE pseudo = ? AND mdp = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            //pseudo
            stmt.setString(1, nomJoueur);
            //mdp
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next(); //on va à la ligne suivante
            if (rs == null) { //table résultat vide
                return null;
            }
            else { //on suppose résultat unique on test pas ici
                return rs.getInt("clesteam");
            }
        }
        //sinon le prepared statement rale
        catch (Exception e ){
            System.out.println(e);
            return null;
        }
    }


    /**
     * save world to database
     * @param idJoueur
     * @param nomPartie
     * @param nomSauvegarde
     * @param monde
     */
    public void saveWorld(Integer idJoueur, String nomPartie, String nomSauvegarde, World monde) {


        String query = "INSERT INTO partie (nom_save, clesteam)\n" +
                "VALUES (?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nomPartie);
            stmt.setInt(2, idJoueur);
            stmt.executeUpdate();


        }
        //sinon le prepared statement rale
        catch (Exception e ){
            System.out.println(e);

        }

        try {
            monde.saveToDatabase(this.connection, nomPartie, nomSauvegarde);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * get world from database
     * @param idJoueur
     * @param nomPartie
     * @param nomSauvegarde
     * @param monde
     */
    public void readWorld(Integer idJoueur, String nomPartie, String nomSauvegarde, World monde) {

        String query;
        PreparedStatement stmt;
        ResultSet rs;
        String nomSave;

        if (nomSauvegarde == null){
            nomSauvegarde = "auto";
        }

        // Nous avons déjà les informations qu'il nous faut pour charger la carte avec nomSauvegarde
        // On vérifie que la partie appartient bien au joueur
        query = "select idsauvegarde FROM joueur NATURAL JOIN partie NATURAL JOIN sauvegarde " +
                "WHERE clesteam = ? AND (nom_save = ? AND idsauvegarde = ?);";

        try {

            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idJoueur);
            stmt.setString(2, nomPartie);
            stmt.setString(3, nomSauvegarde);

            rs = stmt.executeQuery();

            if (!rs.next()){
                throw new Exception("Cette sauvegarde n'existe pas");
            }

            nomSave = rs.getString("idsauvegarde");

            monde.getFromDatabase(connection, nomPartie, nomSauvegarde);

        }
        //sinon le prepared statement rale
        catch (Exception e ){
            System.out.println(e);


        }
    }

    /**
     * Méthode qui nous permet d'effacer une sauvegarde et un monde de la base de données
     * @param idJoueur
     * @param nomPartie
     * @param nomSauvegarde
     */
    public void removeWorld(Integer idJoueur, String nomPartie, String nomSauvegarde){
        String query;
        PreparedStatement stmt;

        // On fait la liste des tables à modifier en respectant un ordre
        // précis pour ne pas avoir d'erreur de clé étrangère
        String[] listeTables = {"guerrier", "archer", "paysan", "lapin", "loup", "objets",
                "monstre", "humanoide", "elementdejeu", "inventaire",
                "sauvegarde"};


        //Pour la beta, on a pris un petit raccourci en ne mettant pas le nom de la game
        //Dans la clé primaire de la table sauvegarde

        //C'est chiant parce qu'on ne peut avoir qu'une sauvegarde avec un certain nom
        //sur tous les jeux confondus, mais au moins, c'est plus facile à effacer

        for (int i = 0 ; i < listeTables.length; i++){

            query = "DELETE FROM " +
                    listeTables[i] +
                    " WHERE idsauvegarde = ?;";

            try {

                stmt = connection.prepareStatement(query);
                stmt.setString(1, nomSauvegarde);

                stmt.executeUpdate();
            }
            //sinon le prepared statement rale
            catch (Exception e ){
                System.out.println(e);
            }
        }
    }
}
