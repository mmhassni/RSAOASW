package com.referencespatiale.serveurcarto.dao;

import com.referencespatiale.serveurcarto.model.QueryLayer;

import java.sql.*;


//Cette class permet de:
//Spécifier les parametres de la base de donnée
//charger le driver
//fournir une connection à la base de donnée avec la methode getConnection
public class  QueryLayerDAOFactory
{

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
    public static QueryLayerDAOFactory getInstance(String url, String nomUtilisateur, String motDePasse) throws QueryLayerDAOConfigurationException
    {

        try
        {
            //chargement du driver
            Class.forName("org.postgresql.Driver");
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
