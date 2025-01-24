package upm.app.model.mySQL;

import upm.app.model.Tournament;

public class TournamentDAO extends GenericDAO<Tournament>{
    public TournamentDAO() {
        super(Tournament.class);
    }
}
