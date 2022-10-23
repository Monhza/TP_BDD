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
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ECN
 */
public class World {

    private static final int MAXPEOPLE = 20;
    private static final int MAXMONSTERS = 10;
    private static final int MAXOBJECTS = 20;

    private Integer width;
    private Integer height;

    public List<ElementDeJeu> listElements;
    private Joueur player;

    /**
     * Default constructor
     */
    public World() {
        this(20, 20);
    }

    /**
     * Constructor for specific world size
     *
     * @param width : world width
     * @param height : world height
     */
    public World(int width, int height) {
        this.setHeightWidth(height, width);
        init();
        generate();
    }

    /**
     * Initialize elements
     */
    private void init() {
        this.listElements = new LinkedList();
        this.player = new Joueur("Player");
    }

    /**
     *
     * @return
     */
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     *
     * @return
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @param height
     * @param width
     */
    public final void setHeightWidth(Integer height, Integer width) {
        this.setHeight(height);
        this.setWidth(width);
    }

    /**
     * Check element can be created
     *
     * @param element
     * @return
     */
    private ElementDeJeu check(ElementDeJeu element) {
        return element;
    }

    /**
     * Generate personnages
     */
    private void generatePersonnages(int nbElements) {
        Random rand = new Random();
        for (int i = 0; i < nbElements; i++) {
            int itemType = rand.nextInt(3);
            Personnage item = null;
            while (item == null) {
                switch (itemType) {
                    case 0: // Guerrier
                        item = new Guerrier(this);
                        break;
                    case 1: // Archer
                        item = new Archer(this);
                        break;
                    case 2: // Paysan
                        item = new Paysan(this);
                        break;
                }
                item = (Personnage) check(item);
            }
            // Add to list
            this.listElements.add(item);
        }
    }

    /**
     * Fonction générer qui permet de produire un personnage selon son type
     * @param typePerso
     */
    private ElementDeJeu generateOnePersonnage(int typePerso) {
        int itemType = typePerso;
        Personnage item = null;
        while (item == null) {
            switch (itemType) {
                case 0: // Guerrier
                    item = new Guerrier(this);
                    break;
                case 1: // Archer
                    item = new Archer(this);
                    break;
                case 2: // Paysan
                    item = new Paysan(this);
                    break;
            }
            item = (Personnage) check(item);
        }

        return item;

    }

    /**
     * Generate Monsters
     */
    private void generateMonsters(int nbElements) {
        Random rand = new Random();

        // Generate monsters
        for (int i = 0; i < nbElements; i++) {
            int itemType = rand.nextInt(2);
            Monstre item = null;
            while (item == null) {
                switch (itemType) {
                    case 0: // Lapin
                        item = new Lapin(this);
                        break;
                    case 1: // Loup
                        item = new Loup(this);
                        break;
                }
                item = (Monstre) check(item);
            }
            // Add to list
            this.listElements.add(item);
        }
    }

    /**
     * Generate Monsters
     * Le type est indiqué par la variable typeMonstre
     */
    private ElementDeJeu generateOneMonster(int typeMonstre) {

        int itemType = typeMonstre;
        Monstre item = null;
        while (item == null) {
            switch (itemType) {
                case 0: // Lapin
                    item = new Lapin(this);
                    break;
                case 1: // Loup
                    item = new Loup(this);
                    break;
            }
            item = (Monstre) check(item);
        }

        return item;

    }

    /**
     * Generate Objects
     */
    private void generateObjects(int nbElements) {
        Random rand = new Random();

        // Generate objects
        for (int i = 0; i < nbElements; i++) {
            int itemType = rand.nextInt(2);
            Objet item = null;
            while (item == null) {
                switch (itemType) {
                    case 0: // Potion de soin
                        item = new PotionSoin(this);
                        break;
                    case 1: // Arme
                        item = new PotionSoin(this);
                        break;
                }
                item = (Objet) check(item);
            }
            // Add to list
            this.listElements.add(item);
        }
    }


    /**
     * Generate Objects
     * Du type qu'on lui demande
     */
    private ElementDeJeu generateOneObject(int typeObjet) {

        int itemType = typeObjet;
        Objet item = null;
        while (item == null) {
            switch (itemType) {
                case 0: // Potion de soin
                    item = new PotionSoin(this);
                    break;
                case 1: // Arme
                    item = new PotionSoin(this);
                    break;
            }
            item = (Objet) check(item);
        }
        // Add to list
        return item;

    }

    /**
     * Generate Player
     */
    private void generatePlayer(int itemType) {
        Personnage item = null;
        while (item == null) {
            switch (itemType) {
                case 0: // Guerrier
                    item = new Guerrier(this);
                    break;
                case 1: // Archer
                    item = new Archer(this);
                    break;
                case 2: // Paysan
                    item = new Paysan(this);
                    break;
            }
            item = (Personnage) check(item);
        }
        // Add to list
        this.listElements.add(item);
    }

    /**
     * Generate elements randomly
     */
    private void generate() {
        Random rand = new Random();

        generatePlayer(1);

        generatePersonnages(MAXPEOPLE);
        generateMonsters(MAXMONSTERS);
        generateObjects(MAXOBJECTS);
    }

    /**
     * Set Player name
     *
     * @param name
     */
    public void setPlayer(String name) {
        this.player.setNom(name);
    }

    /**
     * Save world to database
     *
     * @param connection
     * @param gameName
     * @param saveName
     */
    public void saveToDatabase(Connection connection, String gameName, String saveName) throws Exception {
        String query;
        PreparedStatement stmt;
        ResultSet rs;
        String elementNomSaveEnregistre;
        boolean doesSaveExit = false;
        int idElement = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Donne la date et l'heure pour la sauvegarde
        Random rand = new Random();

        //Si la sauvegarde n'a pas de nom, alors, c'est une sauvegarde auto
        if (saveName == null){
            saveName = "auto";
        }

        if (connection == null) {
            throw new Exception("Pas connecte a la BDD");
        }

        //On vérifie d'abord que la sauvegarde n'existe pas encore
        query = "SELECT idsauvegarde FROM sauvegarde;";

        try {
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                elementNomSaveEnregistre = rs.getString("idsauvegarde");
                if (saveName.equals(elementNomSaveEnregistre)) {
                    doesSaveExit = true; // Si on trouve la meme sauvegarde, on le note
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

        if (doesSaveExit){
            //Si la sauvegarde existe deja, on va devoir l'écraser
            //Mais pour l'instant, on déclenche juste une erreur
            throw new Exception("Une sauvegarde de ce nom existe deja");
        }

        //On enregistre le pseudo du joueur
        query = "INSERT INTO perso (nom_save, nomperso)\n" +
                "VALUES (?, ?);";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, gameName);
            stmt.setString(2, player.getNom());

            stmt.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }



        //Maintenant, on sauvegarde le jeu
        //On initialise la sauvegarde
        query = "INSERT INTO sauvegarde (idsauvegarde, heuresauvegarde, nom_save)\n" +
                "VALUES (?, ?, ?);";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, saveName);
            stmt.setTimestamp(2, timestamp);
            stmt.setString(3, gameName);

            stmt.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }

        //On sauve le monde
        query = "INSERT INTO monde (idmonde, taillex, tailley, nom_save)\n" +
                "VALUES (?, ?, ?, ?);";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, rand.nextInt(1000)); // On met l'ID du monde au hasard, c'est une beta
            stmt.setInt(2, this.width);
            stmt.setInt(3, this.height);
            stmt.setString(4, gameName);

            stmt.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }


        // On sauve tous les éléments du monde
        for (ElementDeJeu element : listElements){

            // D'abord, on entre les coordonnées de l'objet dans la table correspondante
            query = "INSERT INTO elementdejeu (idelement, idsauvegarde, positionx, positiony)\n" +
                    "VALUES (?, ?, ?, ?);";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setInt(1, idElement);
                stmt.setString(2, saveName);
                stmt.setInt(3, element.getPosition().getX());
                stmt.setInt(4, element.getPosition().getY());


                stmt.executeUpdate();

            }catch (Exception e){
                System.out.println(e);
            }

            // Puis on appelle la méthode spécifique à chaque classe
            element.saveToDatabase(connection, saveName, idElement);

            idElement++;
        }

    }

    /**
     * Get world from database
     *
     * @param connection
     * @param gameName
     * @param saveName
     */
    public void getFromDatabase(Connection connection, String gameName, String saveName) {
        String query;
        PreparedStatement stmt;
        ResultSet rs;
        int i;
        int nbElements;
        ElementDeJeu elemTemp;

        if (connection != null) {
            // Remove old data
            this.setHeightWidth(0, 0);
            init();


            //On charge la taille du monde
            query = "select taillex, tailley FROM monde " +
                    "WHERE nom_save = ?;";

            try {

                stmt = connection.prepareStatement(query);
                stmt.setString(1, gameName);

                rs = stmt.executeQuery();
                rs.next();

                this.setWidth(rs.getInt("taillex"));
                this.setHeight(rs.getInt("tailley"));

            }
            catch (Exception e ){
                System.out.println("ouhde");
                System.out.println(e);
            }



            //On récupère le pseudo du joueur
            query = "select nomperso FROM perso " +
                    "WHERE nom_save = ?;";

            try {

                stmt = connection.prepareStatement(query);
                stmt.setString(1, gameName);

                rs = stmt.executeQuery();
                rs.next();

                player.setNom(rs.getString("nomperso"));

            }
            catch (Exception e ){
                System.out.println(e);
            }


            //On charge tous les éléments de jeu
            String[] humanoides = {"guerrier", "archer", "paysan"};
            String[] monstres = {"lapin", "loup"};

            //D'abord les humanoïdes
            for (i = 0; i < humanoides.length ; i++){

                query = "select * FROM elementdejeu NATURAL JOIN humanoide NATURAL JOIN " +
                        humanoides[i] + " " +
                        "WHERE idsauvegarde = ?;";

                try {

                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, saveName);

                    rs = stmt.executeQuery();

                    // Pour chaque personnage de la table, on crée une
                    // nouvelle instance que l'on initie avec les paramètres originels
                    while (rs.next()){

                        //i est le type de personnage
                        elemTemp = this.generateOnePersonnage(i);

                        // Après avoir initialisé le personnage, on lui rétablit ses
                        // valeurs d'origine
                        elemTemp.getFromDatabase(rs);

                        // Enfin, on ajoute l'élément à la liste
                        listElements.add(elemTemp);

                    }


                }
                catch (Exception e ){
                    System.out.println(e);
                }

            }


            //Ensuite les monstres
            for (i = 0; i < monstres.length ; i++){

                query = "select * FROM elementdejeu NATURAL JOIN monstre NATURAL JOIN " +
                        monstres[i] + " " +
                        "WHERE idsauvegarde = ?;";

                try {

                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, saveName);

                    rs = stmt.executeQuery();

                    // Pour chaque monstre de la table, on crée une
                    // nouvelle instance que l'on initie avec les paramètres originels
                    while (rs.next()){

                        //i est le type de monstre
                        elemTemp = this.generateOneMonster(i);

                        // Après avoir initialisé le monstre, on lui rétablit ses
                        // valeurs d'origine
                        elemTemp.getFromDatabase(rs);

                        // Enfin, on ajoute l'élément à la liste
                        listElements.add(elemTemp);

                    }


                }
                catch (Exception e ){
                    System.out.println(e);
                    System.out.println("okoko");
                }

            }

            // Puis les objets
            // Seulement les potions pour ce cas précis
            query = "select * FROM elementdejeu NATURAL JOIN objets " +
                    "WHERE idsauvegarde = ?;";

            try {

                stmt = connection.prepareStatement(query);
                stmt.setString(1, saveName);

                rs = stmt.executeQuery();

                // Pour chaque objet de la table, on crée une
                // nouvelle instance que l'on initie avec les paramètres originels
                while (rs.next()){

                    //0 est le type d'objet Potion
                    elemTemp = this.generateOneObject(0);

                    // Après avoir initialisé l'objet, on lui rétablit ses
                    // valeurs d'origine
                    elemTemp.getFromDatabase(rs);

                    // Enfin, on ajoute l'élément à la liste
                    listElements.add(elemTemp);

                }


            }
            catch (Exception e ){
                System.out.println(e);
            }

            // Nous n'initialisons pas ici le personnage parce qu'il est dans la liste des
            // personnages

            // Une maj est à attendre dans la base de données et dans le code pour pouvoir mettre
            // cette fonctionnalité en place




        }
    }
}
