package Services;

import DAO.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Response.*;

import java.sql.Connection;

/** Clears all data from the database (just like the /clear API), and then loads the
* posted user, person, and event data into the database.*/
public class Load {
    public Response load(LoadRequest lr) throws Exception {
        try {
            User[] users = lr.getUsers();
            Person[] persons = lr.getPersons();
            Event[] events = lr.getEvents();
            int x = users.length, y = persons.length, z = events.length;
            Database db = new Database();
            Connection conn = db.openConnection();
            Boolean commit = db.clearTables();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDao eDao = new EventDao(conn);
            for (int i = 0; i < users.length; ++i) {
                uDao.addUser(users[i]);
            }
            for (int i = 0; i < persons.length; ++i) {
                pDao.addPerson(persons[i]);
            }
            for (int i = 0; i < events.length; ++i) {
                eDao.addEvent(events[i]);
            }
            Event test = eDao.getEvent(events[0].getUserName());
            db.closeConnection(commit);
            return new LoadResponse("Successfully added " + x + " users, " + y + " persons, and " + z + " events to the database", x, y, z);
        }catch(DataAccessException e){
            throw new Exception("Invalid request data (missing values, invalid values, etc.) error");
        }
    }
}
