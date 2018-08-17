package com.referencespatiale.serveurcarto.dao;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.sql.*;
import static com.referencespatiale.serveurcarto.dao.DAOUtilitaire.*;

//c'est une classe qui contient l'ensemble des fonction qui interogent une base de donnée passé en parametre
//pour interoger une base de donnée il faut (logiquement) la passer en parametre
public class QueryLayerDAOImpl implements QueryLayerDAOInterface
{


    //On récupére notre base de donnée
    private QueryLayerDAOFactory daoFactory = QueryLayerDAOFactory.getInstance("jdbc:postgresql://localhost/EHTP","postgres","212Tilifeureusse");


    public QueryLayerDAOImpl( QueryLayerDAOFactory daoFactory )
    {

        this.daoFactory = daoFactory;
    }

    public void changeFactory ()
    {
        try
        {
            fermetureSilencieuse(this.daoFactory.getConnection());
            this.daoFactory = daoFactory;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }


    @Override
    public JSONObject getDataBasesName(String bddName)
    {
        return null;
    }

    @Override
    public JSONObject getTablesName(String bddName)
    {

        return null;
    }

    @Override
    public JSONObject getTablesDependances(String tableName)
    {

        return null;
    }

    @Override
    public JSONObject getRequestResult(String request)
    {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        JSONParser parser = new JSONParser(); //va nous permettre de convertir les objets string en objet json
        JSONObject fichierJson = new JSONObject(); //le grand objet
        JSONObject feature =  null; // l'objet correspondant à un seul enregistrement
        JSONObject geometry = null; // l'objet correspondant à la géometrie
        JSONArray features =  new JSONArray(); //l'array contenant tout les objets feature
        String nomChampCourant = "";


        try
        {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, request, false );
            resultSet = preparedStatement.executeQuery();

        /* Parcours de la ligne de données de l'éventuel ResulSet retourné et récupération des differents champs*/
            while( resultSet.next() ) {

                //on initialise les objets
                feature = new JSONObject();
                geometry = new JSONObject();



                for(int i = 0; i <= resultSetCount(resultSet);i++)
                {

                    nomChampCourant = resultSetNameAttribut(resultSet,i);

                    if(nomChampCourant != null && !"geomjson".equals(nomChampCourant) && !"geom".equals(nomChampCourant))
                    {

                        feature.put(nomChampCourant,resultSet.getString(nomChampCourant));
                    }
                    if(nomChampCourant != null && "geomjson".equals(nomChampCourant))
                    {
                        try
                        {
                            feature.put("geometry",(JSONObject)parser.parse(resultSet.getString(nomChampCourant)));
                        }
                        catch (Exception e)
                        {
                        }
                    }

                }
                //lorsqu'on finira de parcourir l'enregistrement
                features.add(feature);
                //fichierJson.put("prop",resultSet.getString("gid"));

            }

            //on met le seul attribut de l'objet fichierJson à la fin
            fichierJson.put("features",features);

            return fichierJson;
        }
        catch ( SQLException e )
        {
            throw new QueryLayerDAOException( e );
        }
        finally
        {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }


    }




}
