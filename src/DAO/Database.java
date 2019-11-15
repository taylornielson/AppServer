package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

    public class Database {
        private Connection conn;

        //Whenever we want to make a change to our database we will have to open a connection and use
        //Statements created by that connection to initiate transactions
        public Connection openConnection() throws DataAccessException {
            try {
                //The Structure for this Connection is driver:language:path
                //The path assumes you start in the root of your project unless given a non-relative path
                final String CONNECTION_URL = "jdbc:sqlite:C:\\Users\\Nielson\\familymap.sqlite";

                // Open a database connection to the file given in the path
                conn = DriverManager.getConnection(CONNECTION_URL);

                // Start a transaction
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Unable to open connection to database");
            }

            return conn;
        }

        public Connection getConnection() throws DataAccessException {
            if(conn == null) {
                return openConnection();
            } else {
                return conn;
            }
        }

        //When we are done manipulating the database it is important to close the connection. This will
        //End the transaction and allow us to either commit our changes to the database or rollback any
        //changes that were made before we encountered a potential error.

        //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
        //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
        //OR PROBLEMS YOU ENCOUNTER
        public void closeConnection(boolean commit) throws DataAccessException {
            try {
                if (commit) {
                    //This will commit the changes to the database
                    conn.commit();
                } else {
                    //If we find out something went wrong, pass a false into closeConnection and this
                    //will rollback any changes we made during this connection
                    conn.rollback();
                }

                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Unable to close database connection");
            }
        }

        public Boolean createTables() throws DataAccessException, SQLException {

            try (Statement stmt = conn.createStatement()){
                //First lets open our connection to our database.

                //We pull out a statement from the connection we just established
                //Statements are the basis for our transactions in SQL
                //Format this string to be exactly like a sql create table command

                String sqlUser = "CREATE TABLE IF NOT EXISTS USER" +
                        "(" +
                        "personID text not null unique, " +
                        "userName text not null, " +
                        "password text not null, " +
                        "email text not null, " +
                        "firstName text not null, " +
                        "lastName text not null, " +
                        "gender text not null, " +
                        "PRIMARY KEY (personID)" +
                        ")";

                String sql = "CREATE TABLE IF NOT EXISTS PERSON" +
                        "(" +
                        "personID text not null unique, " +
                        "AssociatedUsername text not null, "  +
                        "firstName text not null, " +
                        "lastName text not null, " +
                        "gender text not null, " +
                        "fatherID text, " +
                        "motherID text, " +
                        "spouseID text, "  +
                        "PRIMARY KEY (personID), " +
                        "FOREIGN KEY (AssociatedUsername) references User(userName), " +
                        "FOREIGN KEY (firstname) references User(firstName), " +
                        "FOREIGN KEY (lastName) references User(lastName), " +
                        "FOREIGN KEY (gender) references User(gender)" +
                        ")";

                String sqlEvent = "CREATE TABLE IF NOT EXISTS EVENT" +
                        "(" +
                        "eventID text not null unique, " +
                        "AssociatedUsername text not null, "  +
                        "personID text," +
                        "Latitude double," +
                        "longitude double, " +
                        "Country text, " +
                        "City text, "  +
                        "EventType text, " +
                        "Year, " +
                        "PRIMARY KEY (eventID), " +
                        "FOREIGN KEY (AssociatedUsername) references User(userName), " +
                        "FOREIGN KEY (personID) references User(personID)" +
                        ")";

                String sqlToken = "CREATE TABLE IF NOT EXISTS TOKEN" +
                        "(" +
                        "authString text not null unique, " +
                        "AssociatedUsername text not null, "  +
                        "password text, " +
                        "PRIMARY KEY (authString), " +
                        "FOREIGN KEY (AssociatedUsername) references User(userName) " +
                        ")";

                stmt.executeUpdate(sqlUser);
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sqlEvent);
                stmt.executeUpdate(sqlToken);
                return true;
                //if we got here without any problems we successfully created the table and can commit
            } catch (SQLException e) {
                //if our table creation caused an error, we can just not commit the changes that did happen
                throw new DataAccessException("SQL Error encountered while creating tables");
            }

        }

        public Boolean clearTables() throws DataAccessException
        {

            try (Statement stmt = conn.createStatement()){
                String sql = "DELETE FROM PERSON";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM USER";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM EVENT";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM TOKEN";
                stmt.executeUpdate(sql);
                return true;
            } catch (SQLException e) {
                throw new DataAccessException("SQL Error encountered while clearing tables");
            }
        }
    }

