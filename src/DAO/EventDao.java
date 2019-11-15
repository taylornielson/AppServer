package DAO;
import Model.Event;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**Event Database Access Object*/
public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }
/**Adds event and corresponding data to the table*/
public void addEvent(Event e) throws DataAccessException {
        String sql = "INSERT INTO EVENT (eventID, AssociatedUsername, personID, Latitude, Longitude, Country, City, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, e.getEventID());
            stmt.setString(2, e.getUserName());
            stmt.setString(3, e.getPersonID());
            stmt.setString(4, e.getLatitude().toString());
            stmt.setString(5, e.getLongitude().toString());
            stmt.setString(6, e.getCountry());
            stmt.setString(7, e.getCity());
            stmt.setString(8, e.getEventType());
            stmt.setString(9, e.getYear().toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
/**Gets event based on event name*/
    public Event getEvent(String userName) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("AssociatedUsername"),
                        rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
                return event;
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
/**Gets event by event ID */
    public Event getEventByID(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM EVENT WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("AssociatedUsername"),
                        rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
                return event;
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

    public Event[] getEventsByUserName(String userName) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            ArrayList<Event> arrlist = new ArrayList<Event>();
            Event[] arr;
            while (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("AssociatedUsername"),
                        rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
                arrlist.add(event);

            }
            arr = new Event[arrlist.size()];
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
/**Clears table*/
    void clearTable() throws DataAccessException {
        String sql = "DELETE FROM EVENT";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
/**Deletes event and corresponding data from table*/
    public void deleteEvent(String userName) throws DataAccessException {
        String sql = "DELETE FROM EVENT WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting from the database");
        }
    }
}
