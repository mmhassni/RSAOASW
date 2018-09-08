package com.referencespatiale.serveurcarto.web.controller;


import com.referencespatiale.serveurcarto.dao.QueryLayerDAOInterface;
import com.referencespatiale.serveurcarto.dao.QueryLayerDAOFactory;
import com.referencespatiale.serveurcarto.dao.QueryLayerDAOImpl;
import com.referencespatiale.serveurcarto.model.QueryLayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @CrossOrigin
    @RequestMapping(value = "requestAny/{request}",method = RequestMethod.GET)
    public JSONObject getRequest(@PathVariable String request)
    {

        //System.out.println(request);
        queryLayerDAOFactory = QueryLayerDAOFactory.getInstance();
        queryLayerDAOImpl = new QueryLayerDAOImpl(queryLayerDAOFactory);
        //request = stringConverter(request);
        //System.out.println(request);

        return queryLayerDAOImpl.getRequestResult(request);

    }

    @CrossOrigin
    @RequestMapping(value = "changeProperties/{url}&{driver}&{nomutilisateur}&{motdepasse}",method = RequestMethod.GET)
    public String changeProperties(@PathVariable String url,@PathVariable String driver,@PathVariable String nomutilisateur,@PathVariable String motdepasse)
    {
        String retour = "failed";

        try {

            Properties properties = new Properties();
            properties.put("url", url);
            properties.put("driver", driver);
            properties.put("nomutilisateur", nomutilisateur);
            properties.put("motdepasse", motdepasse);

            properties.store(System.out, "Writing properties to System.out stream");

            FileOutputStream fos = new FileOutputStream("target/classes/dao.properties");
            properties.store(fos, "Writing properties to a file");
            fos.close();
            retour = "succes";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            retour = "failed";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retour;

    }





    public String stringConverter(String sqlRequest)
    {

        String sqlRequestNew = sqlRequest.replace("-"," ");
        return sqlRequestNew;

    }

}