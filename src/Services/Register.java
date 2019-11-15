package Services;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.*;

import java.sql.Connection;
import java.util.UUID;

/**Creates a new user account, generates 4 generations of ancestor data for the new
* user, logs the user in, and returns an auth token.
*/
 public class Register {
    public Response register(RegisterRequest r) throws Exception {
        UUID uuid = UUID.randomUUID();
        String personID = uuid.toString();
        User tempUser = new User(r.getUserName(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
        if ( r.getUserName().equals("") || r.getPassword().equals("") || r.getEmail().equals("") || r.getFirstName().equals("") || r.getLastName().equals("") || r.getGender().equals("")){
            throw new Exception("Request property missing or has invalid value error");
        }
        Database db = new Database();
        Connection conn = db.openConnection();
        UserDao uDao = new UserDao(conn);
        User checkUser = uDao.getUser(tempUser.getUserName());
        if (checkUser != null){
            db.closeConnection(false);
            throw new Exception("Username already taken by another user error");
        }
        uDao.addUser(tempUser);
        db.closeConnection(true);
        Fill tempFill = new Fill();
        tempFill.fill(tempUser.getUserName(), 4);
        Login tempLogin = new Login();
        LoginResponse logResp = (LoginResponse) tempLogin.login(new LoginRequest(tempUser.getUserName(), tempUser.getPassword()));
        RegisterResponse regResponse = new RegisterResponse(logResp.getAuthToken(), logResp.getUserName(), logResp.getPersonID());
        return regResponse;
    }

}