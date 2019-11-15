package Services;
import DAO.*;
import FillJson.JsonReader;
import FillJson.Location;
import Model.Event;
import Model.Person;
import Model.User;
import Response.*;
import com.google.gson.Gson;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;


/**Populates the server's database with generated data for the specified user name.
* The required "username" parameter must be a user already registered with the server. If there is
* any data in the database already associated with the given user name, it is deleted. The
* optional generations parameter lets the caller specify the number of generations of ancestors
* to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
* persons each with associated events).*/

 public class Fill {
    public Response fill(String s, int i) throws Exception {
        if (i < 0) {
            throw new Exception("Invalid Generations parameter error");
        }
        Database db = new Database();
        Connection conn = db.openConnection();
        UserDao uDao = new UserDao(conn);
        User tempUser = uDao.getUser(s);
        if (tempUser == null) {
            throw new Exception("Invalid username parameter error");
        }

        //Clear data related to person
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        pDao.deletePerson(s);
        eDao.deleteEvent(s);

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        Person tempPerson = new Person(tempUser.getPersonID(), tempUser.getUserName(), tempUser.getFirstName(), tempUser.getLastName(), tempUser.getGender());
        ArrayList<Event> events = new ArrayList<Event>();
        ArrayList<Person> persons = new ArrayList<Person>();
        CreateParents(tempPerson, i +1, 1996, s, events, persons);
        int x = persons.size();
        int y = events.size();
        for (int z = 0; z < x; ++z){
            pDao.addPerson(persons.get(z));
        }
        for (int z = 0; z < y; ++z){
            eDao.addEvent(events.get(z));
        }
        db.closeConnection(true);
        return new FillResponse("Successfully added "+ x +" persons and "+ y +" events to the database.",x,y);
    }
    private void CreateParents(Person person, int gens, int year, String s, ArrayList<Event> events, ArrayList<Person> persons) throws DataAccessException, IOException, ParseException {
        JsonReader json = new JsonReader();
        CreateEvents(person, year, events);
        String motherID = GetRandomID();
        String fatherID = GetRandomID();
        person.setFatherID(fatherID);
        person.setMotherID(motherID);
        Person father = new Person(fatherID, s, json.getRandomFatherName(), json.getRandomSurName(), "m");
        Person mother = new Person(motherID, s, json.getRandomMotherName(), father.getLastName(),"f");
        father.setSpouseID(motherID);
        mother.setSpouseID(fatherID);
        gens--;
        if (gens > 1) {
            wedCouple(father, mother,s,year, events);
            year = year -20;
            CreateParents(mother, gens, year, s, events, persons);
            CreateParents(father, gens, year, s, events, persons);
        }else if (gens == 1){
            wedCouple(father, mother,s, year, events);
            year = year -20;
            CreateEvents(father, year, events);
            CreateEvents(mother, year, events);
            persons.add(mother);
            persons.add(father);
        }
        persons.add(person);

    }

    private void CreateEvents(Person p, int year, ArrayList<Event> events) throws DataAccessException {
        JsonReader json = new JsonReader();
        Location tempLoc = json.getRandomLoc();
        Event tempEvent = new Model.Event(GetRandomID(), p.getUserName(), p.getPersonID(), tempLoc.getLatitude(), tempLoc.getLongitude(), tempLoc.getCountry(), tempLoc.getCity(), "birth", year);
        events.add(tempEvent);
        if (year != 1996) {
            tempLoc = json.getRandomLoc();
            tempEvent = new Model.Event(GetRandomID(), p.getUserName(), p.getPersonID(), tempLoc.getLatitude(), tempLoc.getLongitude(), tempLoc.getCountry(), tempLoc.getCity(), "death", year + 30);
            events.add(tempEvent);
        }
    }

    private void wedCouple(Person father, Person mother, String s, Integer year, ArrayList<Event> events) throws DataAccessException {
        JsonReader json = new JsonReader();
        Location wedLoc = json.getRandomLoc();
        Event fatherEvent = new Event(GetRandomID(),s, mother.getPersonID(), wedLoc.getLatitude(), wedLoc.getLongitude(), wedLoc.getCountry(), wedLoc.getCity(), "marriage", year);
        Event motherEvent = new Event(GetRandomID(),s,father.getPersonID(), wedLoc.getLatitude(), wedLoc.getLongitude(), wedLoc.getCountry(), wedLoc.getCity(), "marriage", year);
        events.add(motherEvent);
        events.add(fatherEvent);
    }

    private String GetRandomID(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

}









