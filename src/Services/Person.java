package Services;
import DAO.*;
import Model.AuthToken;
import Model.User;
import Request.PersonRequest;
import Response.*;

import java.sql.Connection;

/**Contains Person and Persons services*/
public class Person {
/**Returns the single Person object with the specified ID*/
    public Response person(String ID, String authToken) throws Exception {
        Database db = new Database();
        Connection conn = db.openConnection();
        PersonDao pDao = new PersonDao(conn);
        AuthTokenDao aDao = new AuthTokenDao(conn);
        AuthToken testToken = aDao.getAuthToken(authToken);
        Model.Person test = pDao.getPersonByID(ID);
        if (testToken != null){
            if(test != null){
                Model.Person person = pDao.getPersonByID(ID);
                if (person.getUserName().equals(testToken.getAssociatedUsername())){
                    PersonResponse presponse = new PersonResponse(person.getUserName(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender());
                    if(person.getFatherID() != null){ presponse.setFatherID(person.getFatherID());}
                    if(person.getMotherID() != null){ presponse.setMotherID(person.getMotherID());}
                    if(person.getSpouseID() != null){ presponse.setSpouseID(person.getSpouseID());}
                    db.closeConnection(true);
                    return presponse;
                }
            }
            else{
                db.closeConnection(false);
                throw new Exception("Invalid personID parameter");
            }
        }
        else {
            db.closeConnection(false);
            throw new Exception("Invalid Auth Token error");
        }

        db.closeConnection(false);
        throw new Exception("error");
    }

/**Returns ALL family members of the current user. The current user is
* determined from the provided auth token.
*/
     public Response persons(String authToken) throws Exception {
         Database db = new Database();
         Connection conn = db.openConnection();
         PersonDao pDao = new PersonDao(conn);
         AuthTokenDao aDao = new AuthTokenDao(conn);
         AuthToken testToken = aDao.getAuthToken(authToken);
         if (testToken != null) {
             PersonsResponse personsResponse = new PersonsResponse(pDao.getPeopleByID(testToken.getAssociatedUsername()));
             db.closeConnection(true);
             return personsResponse;
         }
         else{
             db.closeConnection(false);
             throw new Exception("Not a valid Token error");
         }
    }
}



