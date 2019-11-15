package DAO;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventDaoTest {
        private Database db;
        private Event myEvent;

        @BeforeEach
        public void setUp() throws Exception {
            //here we can set up any classes or variables we will need for the rest of our tests
            //lets create a new database
            db = new Database();
            //and a new event with random data

            myEvent = new Event("myEventID", "Taylor", "myPersonID", 8.76, 9.99, "USA", "Boise", "Birth", 1996);
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
            Event compareTest = null;
            myEvent = new Event("myEventID", "Taylor", "myPersonID", 8.76, 9.99, "USA", "Boise", "Birth", 1996);
            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                eDao.addEvent(myEvent);

                //So lets use a find method to get the event that we just put in back out
                compareTest = eDao.getEventByID(myEvent.getEventID());
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
            assertFalse(myEvent.equals(compareTest));
        }
        //Test Insert Negative
        @Test
        public void insertFail() throws Exception {
            //We want to make sure insert works
            //First lets create an Event that we'll set to null. We'll use this to make sure what we put
            //in the database is actually there.
            Event compareTest = null;
            int numRows = 5;

            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                if (compareTest != null) {
                    eDao.addEvent(compareTest);
                }
                //So lets use a find method to get the event that we just put in back out
                compareTest = eDao.getEventByID(myEvent.getEventID());
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
            Event compareTest = null;
            Event compareTest1 = null;
            Event myEvent1 = new Event("myEventID1", "Taylor1", "myPersonID1", 9.76, 10.99, "USA1", "Boise1", "Birth1", 1997);

            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                eDao.addEvent(myEvent);
                eDao.addEvent(myEvent1);
                //So lets use a find method to get the event that we just put in back out
                compareTest = eDao.getEventByID(myEvent.getEventID());
                compareTest1 = eDao.getEventByID(myEvent1.getEventID());
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
            assertFalse(myEvent.equals(compareTest));
            assertFalse(myEvent1.equals(compareTest1));
        }
        //Query Negative

        @Test
        public void queryFail() throws Exception {
            Event compareTest = null;
            Event compareTest1 = null;
            Event myEvent1 = new Event("myEventID1", "Taylor1", "myPersonID1", 9.76, 10.99, "USA1", "Boise1", "Birth1", 1997);

            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                eDao.addEvent(myEvent);
                eDao.addEvent(myEvent1);
                //So lets use a find method to get the event that we just put in back out
                compareTest = eDao.getEventByID(myEvent.getEventID());
                compareTest1 = eDao.getEventByID("I have a cool ID");
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
            assertFalse(myEvent.equals(compareTest));
            assertFalse(myEvent1.equals(compareTest1));
        }
        //Positive Clear

        @Test
        public void clearPass() throws Exception {
            //We want to make sure insert works
            //First lets create an Event that we'll set to null. We'll use this to make sure what we put
            //in the database is actually there.
            Event compareTest = null;
            Event compareTest1 = null;
            Event myEvent1 = new Event("myEventID1", "Taylor1", "myPersonID1", 9.76, 10.99, "USA1", "Boise1", "Birth1", 1997);
            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                eDao.addEvent(myEvent);
                eDao.addEvent(myEvent1);
                //So lets use a find method to get the event that we just put in back out
                eDao.clearTable();
                compareTest = eDao.getEventByID(myEvent.getEventID());
                compareTest1 = eDao.getEventByID(myEvent1.getEventID());
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
            Event compareTest = null;
            Event compareTest1 = null;
            Event myEvent1 = new Event("myEventID1", "Taylor1", "myPersonID1", 9.76, 10.99, "USA1", "Boise1", "Birth1", 1997);

            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                EventDao eDao = new EventDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                eDao.addEvent(myEvent);
                eDao.addEvent(myEvent1);
                //So lets use a find method to get the event that we just put in back out
                compareTest = eDao.getEventByID(myEvent.getEventID());
                compareTest1 = eDao.getEventByID(myEvent1.getEventID());
                eDao.clearTable();
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
            assertFalse(myEvent.equals(compareTest));
            assertFalse(myEvent1.equals(compareTest1));
        }
    }




