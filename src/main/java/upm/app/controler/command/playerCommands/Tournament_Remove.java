package upm.app.controler.command.playerCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Player;
import upm.app.model.Team;
import upm.app.model.Tournament;
import upm.app.model.UserRole;

public class Tournament_Remove extends Command {
    private UserController userController;
    private TournamentController tournamentController;
    private TeamController teamController;

    public Tournament_Remove(UserController userController, TournamentController tournamentController, TeamController teamController) {
        this.userController = userController;
        this.tournamentController = tournamentController;
        this.teamController = teamController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testParamsMin(params[0], "tournament_remove", params.length - 1, 1);
        if (result != null && result.isEmpty()) {
            if (userController.getLoggedUser().getRole() != UserRole.PLAYER) {
                throw new RoleException("No eres un jugador");
            }
            Player player = (Player) userController.getLoggedUser();
            if (tournamentController.getTournamentList().isEmpty()) {
                throw new NotFoundException("No hay torneos");
            }
            Tournament tournament = tournamentController.getTournamentByName(params[1]);
            if (tournament == null) {
                throw new NotFoundException("Torneo con nombre " + params[1] + " no encontrado");
            }
            if (params.length - 1 == 2) {
                Team team = teamController.getTeamByName(params[2]);
                if (team == null) {
                    throw new NotFoundException("Equipo con nombre " + params[2] + " no encontrado");
                }
                if (team.isPlayerInTeamByDni(player.getDNI())) {
                    tournament.removeTeam(team);
                    result = "Equipo eliminado correctamente";
                } else {
                    throw new NotFoundException("No estas en el equipo");
                }
            } else {
                tournament.getPlayerList().remove(player);
                result = "Jugador eliminado correctamente";
            }

        }
        return result;
    }

    public String toString() {
        return "tournament_remove tournamentName;(teamName)";
    } // Si se coloca el teamName --> Elimina el equipo
}
