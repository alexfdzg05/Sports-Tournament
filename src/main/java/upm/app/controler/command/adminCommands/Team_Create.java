package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.PlayerController;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.DuplicateException;
import upm.app.model.*;

import java.util.LinkedList;

/**
 * This class has all the commands for creating a new team.
 */
public class Team_Create extends Command {
    private final TeamController teams; // Debe ser una lista genérica con todos los equipos del sistema
    private final UserController users;

    /**
     * Constructor for the Team_Create class.
     *
     * @param teams List of all teams in the system.
     * @param users List of all users in the system.
     */
    public Team_Create(TeamController teams, UserController users) {
        this.teams = teams;
        this.users = users;
    }

    /**
     * register a team in the system, the admin that creates it must be
     * saved on it. The team can only be created if it doesn't already exist in the system.
     *
     * @param params Array of parameters for the command.
     * @return A string representing the result of the command execution.
     */
    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "team_create", params.length - 1, 1);
        if (result != null && result.isEmpty()) {
            User user = users.getLoggedUser();
            if (user.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {
                if (teams.existsTeam(params[1])) {
                    throw new DuplicateException("Equipo con nombre '" + params[1] + "' ya existe");
                } else {
                    teams.insertTeam(new Team(params[1], new LinkedList<>(), (Admin) user));
                    result = "Equipo con nombre '" + params[1] + "' creado correctamente";
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
    @Override
    public String toString() {
        return "team_create nameTeam";
    }
}
