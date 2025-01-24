package upm.app.model.mySQL;

import org.hibernate.Session;
import upm.app.model.User;

public class UserDAO extends GenericDAO<User> {

    public UserDAO(){
        super(User.class);
    }
}
