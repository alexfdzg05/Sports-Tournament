package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Team;
import upm.app.model.User;
import upm.app.model.UserRole;


/**
 * This class represents a command for removing a player from a team.
 * It extends the Command class and implements the execute method.
 *
 *
 */
public class Team_Remove extends Command {
    /**
     * A list of all users in the system.
     */
    private final UserController users;

    /**
     * A list of all teams in the system.
     */
    private final TeamController teams;

    /**
     * Constructs a new Team_Remove command with the given user and team lists.
     *
     * @param users A list of all users in the system.
     * @param teams A list of all teams in the system.
     */
    public Team_Remove(UserController users, TeamController teams) {
        this.users = users;
        this.teams = teams;
    }

    /**
     * Executes the command with the given parameters.
     * removes a team from the system, it cannot be deleted if the
     * team participates in a tournament already underway.
     *
     * @param params An array of parameters for the command.
     * @return A string representing the result of the command execution.
     * If the command is successful, an empty string is returned.
     * Otherwise, an error message is returned.
     */
    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "team_remove", params.length - 1, 2);
        if (result != null && result.isEmpty()) {
            User user = users.getLoggedUser();
            if (user.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {
                Team team = teams.getTeamByName(params[1]);
                if (team == null) {
                    throw new NotFoundException("Equipo no encontrado");
                } else {
                    team.deletePlayer(params[2]);
                    result = "Jugador del equipo "+ params[1] + " eliminado correctamente";
                }
            }
        }
        return result;
    }

    /**
     * Returns a string representation of the command.
     *
     * @return A string representing the command.
     */
    public String toString() {
        return "team_remove nameTeam;playerDNI";
    }
}