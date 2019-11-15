package Services;
import DAO.Database;
import DAO.EventDao;
import DAO.PersonDao;
import DAO.UserDao;
import Model.Event;
import Model.Person;
import Model.User;
import Services.Clear;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;
public class ClearTest {
    private Database db;
    PersonDao pDao;
    UserDao uDao;
    EventDao eDao;

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
        pDao.addPerson(new Person("123", "taylor", " tay", "niel", "M", "kasdjf;aklsdj", "klkasddf;a", ";ksdffakl"));
        uDao.addUser(new User("taylor", "pass", "email", "tay", "niel", "m", "123"));
        eDao.addEvent(new Event("555", "taylor", "123", 123.2, 45.7, "USA", "Boise", "birth", 2000));
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
    public void clearPass() throws Exception {
        Connection conn = db.openConnection();
        PersonDao pDao = new PersonDao(conn);
        Clear myClear = new Clear();
        myClear.clear();
        Person test = pDao.getPersonByID("123");
        db.closeConnection(true);
        assertNull(test);
    }

    @Test
    public void clearFail() throws Exception {
        Connection conn = db.openConnection();
        PersonDao pDao = new PersonDao(conn);
        Clear myClear = new Clear();
        Person test = pDao.getPersonByID("123");
        db.closeConnection(true);
        assertNotNull(test);
    }

}