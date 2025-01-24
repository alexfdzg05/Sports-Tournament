package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Player;
import upm.app.model.User;
import upm.app.model.UserRole;
import upm.app.controler.structures.PlayerController;
import upm.app.model.Team;

/**
 * This class represents a command to delete a player from the system.
 * It extends the Command class and implements the execute method.
 */
public class Player_Delete extends Command {

    private PlayerController playerController;
    private TeamController teams;
    private TournamentController tournaments;
    private UserController users;

    /**
     * Constructor for Player_Delete class.
     *
     * @param playerController The list of players in the system.
     * @param tournaments The list of tournaments in the system.
     * @param users      The list of users in the system.
     */
    public Player_Delete(PlayerController playerController, TeamController teams, TournamentController tournaments, UserController users) {
        this.playerController = playerController;
        this.teams = teams;
        this.tournaments = tournaments;
        this.users = users;
    }

    /**
     * Executes the command to delete a player from the system.
     * Deletes the player from the system, it cannot be deleted if this player participates
     * in an ongoing tournament or if he belongs to a team participating in an ongoing tournament.
     *
     * @param params The parameters for the command. The first parameter is the DNI of the player to delete.
     * @return A string indicating the result of the command execution.
     */
    @Override
    public String execute(String[] params) {
        String resultado = testparams(params[0], "player_delete", params.length - 1, 1);
        if (resultado != null && resultado.isEmpty()) {
            User loggedUser = users.getLoggedUser();
            if (loggedUser.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {

                String dniToDelete = params[1];

                Player playerToDelete = playerController.getPlayerByDni(dniToDelete);  // Busca por DNI al jugador
                Team team = teams.getTeamByDNI(dniToDelete); // Busca por DNI al equipo del jugador

                if (playerToDelete == null) {
                    throw new NotFoundException("Jugador con DNI " + dniToDelete + " no existe");
                }else {
                    if (tournaments.isPlayerParticipating(playerToDelete)) {
                        throw new BadRequestException("Jugador " + playerToDelete.getDNI() + " no puede ser eliminado porque está participando en un torneo");
                    } else if (team != null && tournaments.isTeamParticipating(team)) {
                        throw new BadRequestException("Jugador " + playerToDelete.getDNI() + " no puede ser eliminado porque su equipo está participando en un torneo");
                    } else {
                        playerController.deletePlayer(playerToDelete);
                        users.deleteUser(playerToDelete);
                        resultado = "Jugador eliminado correctamente: " + playerToDelete.getName() + " " + playerToDelete.getSurname();

                    }
                }
            }
        }
        return resultado;
    }

    /**
     * Returns a string representation of the command.
     *
     * @return A string in the format "player_delete [DNI_Player]".
     */
    @Override
    public String toString() {
        return "player_delete playerDNI";
    }
}
