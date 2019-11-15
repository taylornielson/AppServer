package Services;
import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.AuthToken;
import Model.User;
import Response.*;
import Request.LoginRequest;

import java.sql.Connection;
import java.util.UUID;
/**Logs in the user and returns an auth token.*/
public class Login {
    public Response login(LoginRequest lr) throws Exception {
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            User tempUser = uDao.getUser(lr.getUserName());
            if (tempUser == null) {
                db.closeConnection(false);
                throw new Exception("Request property missing or has invalid value error");
            }
            if (tempUser.getPassword().equals(lr.getPassword())) {
                //Make Authstring
                UUID uuid = UUID.randomUUID();
                String authString = uuid.toString();
                //Open Dao
                AuthTokenDao tokenDao = new AuthTokenDao(conn);
                //make auth token to add to the dao
                AuthToken aToken = new AuthToken(authString, lr.getUserName(), tempUser.getPersonID());
                tokenDao.addAuthToken(aToken);
                db.closeConnection(true);
                return new LoginResponse(authString, lr.getUserName(), tempUser.getPersonID());
            }
            db.closeConnection(false);
            throw new Exception("Other Problem error");
        } catch (DataAccessException e) {
            throw new Exception("Internal server error");
        }
    }
}
