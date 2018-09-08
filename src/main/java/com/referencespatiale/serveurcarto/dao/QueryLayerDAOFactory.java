package com.referencespatiale.serveurcarto.dao;

import java.io.IOException;
import java.io.InputStream;

import java.sql.*;
import java.util.Properties;


//Cette class permet de:
//Spécifier les parametres de la base de donnée
//charger le driver
//fournir une connection à la base de donnée avec la methode getConnection
public class  QueryLayerDAOFactory
{
    private static final String FICHIER_PROPERTIES       = "dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";


    private String url = "";
    private String username = "";
    private String password = "";

    QueryLayerDAOFactory()
    {

    }

    QueryLayerDAOFactory( String url, String username, String password )
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /* Méthode chargée de récupérer les informations de connexion à la base de données, charger le driver JDBC et retourner une instance de la Factory */
    public static QueryLayerDAOFactory getInstance() throws QueryLayerDAOConfigurationException
    {
        Properties properties = new Properties();

        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new QueryLayerDAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try
        {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );

        } catch ( IOException e ) {
            throw new QueryLayerDAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }

        try {
            Class.forName( driver );
        }
        catch ( ClassNotFoundException e )
        {
            throw new QueryLayerDAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }


        //Creation de l'instance
        QueryLayerDAOFactory instance = new QueryLayerDAOFactory( url, nomUtilisateur, motDePasse );
        return instance;
    }




    //Méthode chargée de fournir une connexion à la base de données
    //notons que le driver ne peut pas etre chargé si le driver n'a pas été chargé avec le constructeur
     Connection getConnection() throws SQLException
     {
        return DriverManager.getConnection( url, username, password );
     }



    /* Méthodes de récupération de l'implémentation des différents DAO (un seul pour le moment) */
    public QueryLayerDAOInterface getQueryLayerDaoImpl()
    {
        return new QueryLayerDAOImpl( this );
    }




}
