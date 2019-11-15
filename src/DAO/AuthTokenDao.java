package DAO;

import Model.AuthToken;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**AuthToken Database Access Object*/
public class AuthTokenDao {
    private final Connection conn;
    public AuthTokenDao(Connection conn){
        this.conn = conn;
    };
/**Add AuthToken to the table*/
    public void addAuthToken(AuthToken a) throws DataAccessException {
        String sql = "INSERT INTO TOKEN (authString, AssociatedUsername, password) VALUES(?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, a.getAuthString());
            stmt.setString(2, a.getAssociatedUsername());
            stmt.setString(3, a.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
/**gets AuthToken by userName */
    public AuthToken getAuthToken(String authString) throws DataAccessException {
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM TOKEN WHERE authString = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authString);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("authString"), rs.getString("AssociatedUsername"),
                        rs.getString("password"));
                return token;
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
/**Clears table*/
    public void clearTable() throws DataAccessException {
        String sql = "DELETE FROM TOKEN";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
/**Deletes token and corresponding data from row*/
    public void deleteToken(String AuthString) throws DataAccessException {
        String sql = "DELETE FROM TOKEN WHERE AuthString= ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, AuthString);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
}



