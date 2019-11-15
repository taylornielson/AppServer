package DAO;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**Person Access Object*/

public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

     /**Adds Person and info to the table */
        public void addPerson(Person p) throws DataAccessException {
            String sql = "INSERT INTO PERSON (personID, associatedUsername, firstName, lastName, gender, " +
                    "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                //Using the statements built-in set(type) functions we can pick the question mark we want
                //to fill in and give it a proper value. The first argument corresponds to the first
                //question mark found in our sql String
                stmt.setString(1, p.getPersonID());
                stmt.setString(2, p.getUserName());
                stmt.setString(3, p.getFirstName());
                stmt.setString(4, p.getLastName());
                stmt.setString(5, p.getGender());
                stmt.setString(6, p.getFatherID());
                stmt.setString(7, p.getMotherID());
                stmt.setString(8, p.getSpouseID());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }

/**Gets Person Object by username*/
        public Person getPerson(String userName) throws DataAccessException {
            Person person;
            ResultSet rs = null;
            String sql = "SELECT * FROM PERSON WHERE associatedUsername = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userName);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                            rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                            rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                    return person;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Error encountered while finding event");
            } finally {
                if(rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }
/**Gets Person Object by personID*/
        public Person getPersonByID(String personID) throws DataAccessException {
            Person person;
            ResultSet rs = null;
            String sql = "SELECT * FROM PERSON WHERE personID = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, personID);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    person = new Person(rs.getString("personID"), rs.getString("AssociatedUsername"),
                            rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                            rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                    return person;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Error encountered while finding event");
            } finally {
                if(rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

    public Person[] getPeopleByID(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM PERSON WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            Person[] arr;
            ArrayList<Person> arrlist = new ArrayList<Person>();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("AssociatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                arrlist.add(person);
            }
            arr = new Person[arrlist.size()];
            for (int i = 0; i < arr.length; ++i){
                arr[i] = arrlist.get(i);
            }
            return arr;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
/**Clears Table*/
        public void clearTable() throws DataAccessException {
            String sql = "DELETE FROM PERSON";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while inserting into the database");
            }
        }
/**Delete person and info from table*/
        public void deletePerson(String userName) throws DataAccessException {
            String sql = "DELETE FROM PERSON WHERE AssociatedUsername = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userName);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while Deleting from the database");
            }
        }
}
