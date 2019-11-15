package Services;

import DAO.AuthTokenDao;
import DAO.Database;
import DAO.EventDao;
import Model.AuthToken;
import Response.*;

import java.sql.Connection;

/**Contains the Event and Events services*/
public class Event {
/**Returns the single Event object with the specified ID.*/
        public Response event(String ID, String authToken) throws Exception {
            Database db = new Database();
            Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                AuthTokenDao aDao = new AuthTokenDao(conn);
                AuthToken testToken = aDao.getAuthToken(authToken);
                Model.Event test = eDao.getEventByID(ID);
                if (testToken != null) {
                    if (test != null && test.getUserName().equals(testToken.getAssociatedUsername())) {
                        if (test.getUserName().equals(testToken.getAssociatedUsername())) {
                            EventResponse presponse = new EventResponse(test.getUserName(), test.getEventID(), test.getPersonID(), test.getLatitude(), test.getLongitude(), test.getCountry(), test.getCity(), test.getEventType(), test.getYear());
                            db.closeConnection(true);
                            return presponse;
                        }
                    } else {
                        db.closeConnection(false);
                        throw new Exception("Invalid eventID parameter error");
                    }
                } else {
                    db.closeConnection(false);
                    throw new Exception("Invalid Auth Token error");
                }
                db.closeConnection(false);
                return null;
        }

        /**Returns ALL family members of the current user. The current user is
         * determined from the provided auth token.
         */
        public Response events(String authToken) throws Exception {
            Database db = new Database();
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);
            AuthToken testToken = aDao.getAuthToken(authToken);
            if (testToken != null) {
                Model.Event[] events = eDao.getEventsByUserName(testToken.getAssociatedUsername());
                EventsResponse eventsResponse = new EventsResponse(events);
                db.closeConnection(true);
                return eventsResponse;
            }
            else{
                db.closeConnection(false);
                throw new Exception("Not a valid Token error");
            }
        }
    }




