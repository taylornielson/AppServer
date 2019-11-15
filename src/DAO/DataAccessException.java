package DAO;

import Response.ErrorMessage;

public class DataAccessException extends Exception {
    DataAccessException(String message)
    {
        super(message);
    }

}
