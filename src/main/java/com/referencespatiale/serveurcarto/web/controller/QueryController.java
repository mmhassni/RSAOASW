package com.referencespatiale.serveurcarto.web.controller;


import com.referencespatiale.serveurcarto.dao.QueryLayerDAOInterface;
import com.referencespatiale.serveurcarto.dao.QueryLayerDAOFactory;
import com.referencespatiale.serveurcarto.dao.QueryLayerDAOImpl;
import com.referencespatiale.serveurcarto.model.QueryLayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;


@RestController
public class QueryController
{

    //C'est l'équivalent à une instance de la base de donnée
    private QueryLayerDAOFactory queryLayerDAOFactory;

    private QueryLayerDAOImpl queryLayerDAOImpl;

    QueryController()
    {


    }
    public void changerBDD(String nomNoubelleBDD)
    {

    }

    @CrossOrigin(origins = "http://127.0.0.1:8081")
    @RequestMapping(value = "requestAny/{request}]",method = RequestMethod.GET)
    public JSONObject getRequest(@PathVariable String request)
    {

        //System.out.println(request);
        queryLayerDAOFactory = QueryLayerDAOFactory.getInstance("jdbc:postgresql://localhost/EHTP","postgres","212tilifeureusse");
        queryLayerDAOImpl = new QueryLayerDAOImpl(queryLayerDAOFactory);
        //request = stringConverter(request);
        //System.out.println(request);

        return queryLayerDAOImpl.getRequestResult(request);

    }

    public String stringConverter(String sqlRequest)
    {

        String sqlRequestNew = sqlRequest.replace("-"," ");
        return sqlRequestNew;

    }

}