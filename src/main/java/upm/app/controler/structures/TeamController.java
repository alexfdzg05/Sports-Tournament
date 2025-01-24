package upm.app.controler.structures;

import upm.app.model.Player;
import upm.app.model.Team;
import upm.app.model.mySQL.TeamDAO;

import java.util.LinkedList;

public class TeamController {
    private TeamDAO teamDAO;

    public TeamController() {
        this.teamDAO = new TeamDAO();
    }
    public LinkedList<Team> getTeams(){
        return (LinkedList<Team>) teamDAO.findAll();
    }
    public void insertTeam(Team team){
        teamDAO.save(team);
    }
    public void removeTeam(Team team){
        teamDAO.delete(team);
    }
    public boolean existsTeam(String name){
        return teamDAO.findByID(name) != null;
    }
    public Team getTeamByName(String name){
        return teamDAO.findByID(name);
    }
    public Team getTeamByDNI(String dni){
        Team result = null;
        for (Team team : teamDAO.findAll()){
            if (team.isPlayerInTeamByDni(dni)){
                result = team;
            }
        }
        return result;
    }
    public void teamUpdate(Player player){
        teamDAO.findByID(player.getDNI()).addPlayerUpdate(player);
    }
}
