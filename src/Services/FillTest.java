package Services;
import DAO.Database;
import DAO.EventDao;
import DAO.PersonDao;
import DAO.UserDao;
import Model.Event;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Response.ErrorMessage;
import Response.FillResponse;
import Response.RegisterResponse;
import Services.Fill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class FillTest {
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
        db.clearTables();
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
    public void FillPass(){
        try {

            Fill fill = new Fill();
            FillResponse fillResp = (FillResponse) fill.fill("taylor", 4);
            assertEquals(31, fillResp.getX());
        }catch(Exception e){
            e.getMessage();
        }
    }
    @Test
    public void FillFail(){
        try {

            Fill fill = new Fill();
            FillResponse fillResp = (FillResponse) fill.fill("taylor", -1);
            assertEquals(31, fillResp.getX());
        }catch(Exception e){
           Assertions.assertEquals("Invalid Generations parameter error", e.getMessage());
        }
    }
}
