package upm.app.model.mySQL;

import upm.app.model.Team;

public class TeamDAO extends GenericDAO<Team>{
    public TeamDAO() {
        super(Team.class);
    }
}
