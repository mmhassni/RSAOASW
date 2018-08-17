package com.referencespatiale.serveurcarto.dao;

public class QueryLayerDAOException extends RuntimeException
{

    /*
     * Constructeurs
     */
    public QueryLayerDAOException( String message ) {
        super( message );
    }

    public QueryLayerDAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public QueryLayerDAOException( Throwable cause ) {
        super( cause );
    }
}
