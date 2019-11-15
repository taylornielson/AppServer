package DAO;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person myPerson;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data

        myPerson = new Person("thisisID", "thisIsname", "this is FName",
                "this is Lname", "m", "dad", "mom",
                "wifey");
        //and make sure to initialize our tables since we don't know if our database files exist yet
        db.openConnection();
        db.createTables();
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

    //Test Insert Positive
    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.addPerson(myPerson);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.getPersonByID(myPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertTrue(myPerson.equals(compareTest));
    }
    //Test Insert Negative
    @Test
    public void insertFail() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;
        int numRows = 5;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            if (compareTest != null) {
                pDao.addPerson(compareTest);
            }
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.getPersonByID(myPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
    }
    //Query Positive
    @Test
    public void queryPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;
        Person compareTest1 = null;
        myPerson = new Person("thisisID", "thisIsname", "this is FName",
                "this is Lname", "m", "dad", "mom",
                "wifey");
        Person myPerson1 = new Person("thisisID 1", "thisIsname 1", "this is FName 1",
                "this is Lname 1", "m", "dad 1", "mom 1",
                "wifey 1");

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.addPerson(myPerson);
            pDao.addPerson(myPerson1);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.getPersonByID(myPerson.getPersonID());
            compareTest1 = pDao.getPersonByID(myPerson1.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        assertNotNull(compareTest1);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertTrue(myPerson.equals(compareTest));
        assertTrue(myPerson1.equals(compareTest1));
    }
    //Query Negative

    @Test
    public void queryFail() throws Exception {
        Person compareTest = null;
        Person compareTest1 = null;
        myPerson = new Person("thisisID", "thisIsname", "this is FName",
                "this is Lname", "m", "dad", "mom",
                "wifey");
        Person myPerson1 = new Person("thisisID 1", "thisIsname 1", "this is FName 1",
                "this is Lname 1", "m", "dad 1", "mom 1",
                "wifey 1");

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.addPerson(myPerson);
            pDao.addPerson(myPerson1);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.getPersonByID(myPerson.getPersonID());
            compareTest1 = pDao.getPersonByID("I have a cool ID");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        assertNull(compareTest1);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertTrue(myPerson.equals(compareTest));
        assertFalse(myPerson1.equals(compareTest1));
    }
    //Positive Clear
    @Test
    public void clearPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;
        Person compareTest1 = null;
        myPerson = new Person("thisisID", "thisIsname", "this is FName",
                "this is Lname", "m", "dad", "mom",
                "wifey");
        Person myPerson1 = new Person("thisisID 1", "thisIsname 1", "this is FName 1",
                "this is Lname 1", "m", "dad 1", "mom 1",
                "wifey 1");

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.addPerson(myPerson);
            pDao.addPerson(myPerson1);
            //So lets use a find method to get the event that we just put in back out
            pDao.clearTable();
            compareTest = pDao.getPersonByID(myPerson.getPersonID());
            compareTest1 = pDao.getPersonByID(myPerson1.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNull(compareTest);
        assertNull(compareTest1);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
    }
    //Negative Clear
    @Test
    public void clearFail() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;
        Person compareTest1 = null;
        myPerson = new Person("thisisID", "thisIsname", "this is FName",
                "this is Lname", "m", "dad", "mom",
                "wifey");
        Person myPerson1 = new Person("thisisID 1", "thisIsname 1", "this is FName 1",
                "this is Lname 1", "m", "dad 1", "mom 1",
                "wifey 1");

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.addPerson(myPerson);
            pDao.addPerson(myPerson1);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.getPersonByID(myPerson.getPersonID());
            compareTest1 = pDao.getPersonByID(myPerson1.getPersonID());
            pDao.clearTable();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        assertNotNull(compareTest1);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertTrue(myPerson.equals(compareTest));
        assertTrue(myPerson1.equals(compareTest1));
    }
}


