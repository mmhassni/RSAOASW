package com.referencespatiale.serveurcarto.dao;

import org.json.simple.JSONObject;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;

public interface QueryLayerDAOInterface
{
    //retourne les bdd d'un sgbd
    JSONObject getDataBasesName(String bddName);

    //retourne les tables d'une bdd passée en parametre
    JSONObject getTablesName(String bddName);

    //retourne tout les dépandences d'une table donnée
    JSONObject getTablesDependances(String tableName);

    //retourne tout les résultats d'une requette spatiale passé en parametre
    JSONObject getRequestResult(String request,String idTable, String fieldGeom,int wkid);

    //retourne tout les résultats d'une requette quelquonque passé en parametre
    JSONObject getRequestResult(String request);



}
