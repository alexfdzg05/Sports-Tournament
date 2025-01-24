package upm.app.model.mySQL;

import upm.app.model.Matches;

public class MatchDAO extends GenericDAO<Matches>{
    public MatchDAO() {
        super(Matches.class);
    }
}
