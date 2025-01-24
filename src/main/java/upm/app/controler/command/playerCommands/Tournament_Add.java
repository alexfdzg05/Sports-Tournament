package upm.app.controler.command.playerCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.DuplicateException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Player;
import upm.app.model.Team;
import upm.app.model.Tournament;
import upm.app.model.UserRole;

public class Tournament_Add extends Command {
    private TournamentController tournamentController;
    private TeamController teamController;
    private UserController userController;

    public Tournament_Add(TournamentController tournamentController, TeamController teamController, UserController userController) {
        this.tournamentController = tournamentController;
        this.teamController = teamController;
        this.userController = userController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testParamsMin(params[0], "tournament_add", params.length - 1, 1);
        if (result != null && result.isEmpty()) {
            if (userController.getLoggedUser().getRole() != UserRole.PLAYER) {
                throw new RoleException("No eres un jugador");
            }
            if (tournamentController.getTournamentList().isEmpty()) {
                throw new NotFoundException("No hay torneos");
            }
            Player player = (Player) userController.getLoggedUser();
            Tournament tournament = tournamentController.getTournamentByName(params[1]);
            if (tournament == null) {
                throw new NotFoundException("Torneo con nombre " + params[1] + " no encontrado");
            }
            if (params.length - 1 == 2) {
                Team team = teamController.getTeamByName(params[2]);
                if (team == null) {
                    throw new NotFoundException("Equipo con nombre " + params[2] + " no encontrado");
                }
                if (!team.isPlayerInTeamByDni(player.getDNI())) {
                    throw new NotFoundException("Jugador no encontrado en el equipo");

                }
                if (!tournament.isTeamParticipating(team)) {
                    tournament.addTeam(team);
                    result = "Equipo añadido correctamente";
                } else {
                    throw new DuplicateException("Equipo ya registrado");
                }

            } else {
                if (!tournament.isPlayerParticipating(player)) {
                    tournament.getPlayerList().add(player);
                    result = "Jugador añadido correctamente";
                } else {
                    throw new DuplicateException("Jugador ya registrado");
                }
            }
        }
        return result;
    }

    public String toString() {
        return "tournament_add tournamentName;(teamName)";
    }// Si se coloca el teamName --> Añade el equipo
}
