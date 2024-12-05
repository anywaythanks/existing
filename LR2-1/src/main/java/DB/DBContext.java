package DB;

import DB.UserDAO;
import javax.sql.DataSource;

public class DBContext{

    public UserDAO userDAO;

    public DBContext(DataSource dataSource){
        userDAO = new UserDAO(dataSource);
    }
}
