package upm.app.model.mySQL;

import org.hibernate.Session;
import upm.app.model.Player;
import upm.app.model.User;

import java.sql.Connection;

public class PlayerDAO extends GenericDAO<Player> {
    public PlayerDAO() {
        super(Player.class);
    }
}
