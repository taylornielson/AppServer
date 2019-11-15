package Model;

import java.util.Objects;

/** Represents an Authorization Token
 *
 */
public class AuthToken {
    String authString;
    String AssociatedUsername;
    String personID;

    public AuthToken(String authString, String userName, String personID) {
        this.authString = authString;
        this.AssociatedUsername = userName;
        this.personID = personID;
    }

    public String getAuthString() {
        return authString;
    }

    public void setAuthString(String authString) {
        this.authString = authString;
    }

    public String getAssociatedUsername() {
        return AssociatedUsername;
    }

    public void setAssociatedUsername(String userName) {
        this.AssociatedUsername = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setpassword(String password) {
        password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authString, authToken.authString) &&
                Objects.equals(AssociatedUsername, authToken.AssociatedUsername) &&
                Objects.equals(personID, authToken.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authString, AssociatedUsername, personID);
    }
}
