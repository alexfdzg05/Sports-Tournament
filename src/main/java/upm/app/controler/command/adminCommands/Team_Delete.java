package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.model.*;

public class Team_Delete extends Command {
    private TeamController teams;
    private TournamentController tournaments;
    private UserController users;

    public Team_Delete(TeamController teams, TournamentController tournaments, UserController users) {
        this.teams = teams;
        this.tournaments = tournaments;
        this.users = users;
    }

    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "team_delete", params.length, 2);
        if (result != null && result.isEmpty()){
            User user = users.getLoggedUser();
            if (user.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {
                Team team = teams.getTeamByName(params[1]);
                if (tournaments.getTeamTournaments(team).isEmpty()){
                    teams.removeTeam(team);
                    result = "Equipo con el nombre "+params[1]+" eliminado correctamente";

                } else {
                    throw new BadRequestException("El equipo ya esta en un torneo");
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "team_delete nameTeam";
    }
}
