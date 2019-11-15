package Services;
import DAO.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Response.EventResponse;
import Response.EventsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class EventTest {
        private Database db;
        PersonDao pDao;
        UserDao uDao;
        EventDao eDao;
        AuthTokenDao aDao;
        @BeforeEach
        public void setUp() throws Exception {
            //here we can set up any classes or variables we will need for the rest of our tests
            //lets create a new database
            db = new Database();
            Connection conn = db.openConnection();
            db.createTables();

            pDao = new PersonDao(conn);
            uDao = new UserDao(conn);
            eDao = new EventDao(conn);
            aDao = new AuthTokenDao(conn);
            pDao.addPerson(new Person("123", "taylor", " tay", "niel", "M", "kasdjf;aklsdj", "klkasddf;a", ";ksdffakl"));
            uDao.addUser(new User("taylor", "pass", "email", "tay", "niel", "m", "123"));
            eDao.addEvent(new Event("555", "taylor", "123", 123.2, 45.7, "USA", "Boise", "birth", 2000));
            aDao.addAuthToken(new AuthToken("6789","taylor", "123"));
            db.closeConnection(true);
        }
        @AfterEach
        public void tearDown() throws Exception {
            //here we can get rid of anything from our tests we don't want to affect the rest of our program
            //lets clear the tables so that any data we entered for testing doesn't linger in our files
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
        }
        @Test
        public void EventPass() throws Exception {
            Services.Event event = new Services.Event();
            EventResponse evResp = (EventResponse) event.event("555", "6789");
            Assertions.assertEquals("USA", evResp.getCountry());
        }
        @Test
        public void EventFail() throws Exception {
            try {
                Services.Event event = new Services.Event();
                EventResponse evResp = (EventResponse) event.event("545", "6789");
                Assertions.assertEquals("USA", evResp.getCountry());
            }catch(Exception e){
                Assertions.assertEquals("Invalid eventID parameter error", e.getMessage());
            }
        }
    @Test
    public void EventsPass() throws Exception {
        Services.Event event = new Services.Event();
        EventsResponse evResp = (EventsResponse) event.events("6789");
        Assertions.assertEquals("USA", evResp.getData()[0].getCountry());
    }
    @Test
    public void EventsFail() throws Exception {
        try {
            Services.Event event = new Services.Event();
            EventsResponse evResp = (EventsResponse) event.events("6785");
            Assertions.assertEquals("USA", evResp.getData()[0].getCountry());
        }catch(Exception e){
            Assertions.assertEquals("Not a valid Token error", e.getMessage());
        }
    }
}
