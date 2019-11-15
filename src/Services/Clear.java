package Services;

import DAO.DataAccessException;
import DAO.Database;
import Response.*;

/**Deletes ALL data from the database, including user accounts, auth tokens, and
* generated person and event data.
*/
 public class Clear {
        public Response clear() {
            try {
                Database db = new Database();
                db.openConnection();
                Boolean commit = db.clearTables();
                db.closeConnection(commit);
                return new ClearResponse("Clear succeeded");
            } catch (DataAccessException e) {
                return new ClearResponse("Internal server error");
            }
        }
}
