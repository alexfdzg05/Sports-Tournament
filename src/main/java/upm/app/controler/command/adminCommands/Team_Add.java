package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.PlayerController;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.DuplicateException;
import upm.app.model.*;

/**
 * This class represents a command to add a player to a team.
 * It extends the Command class and implements the execute and toString methods.
 */
public class Team_Add extends Command {
    private final UserController users;
    private final TeamController teams;
    private final PlayerController players; // Here will be the players of the system

    /**
     * Constructor for Team_Add class.
     *
     * @param users   The list of users in the system.
     * @param teams   The list of teams in the system.
     * @param players The list of players in the system.
     */
    public Team_Add(UserController users, TeamController teams, PlayerController players) {
        this.users = users;
        this.teams = teams;
        this.players = players;
    }

    /**
     * Executes the command to add a player to a team.
     * Add a player to a team, he can be added to a team,
     * even if his team is in a current tournament
     *
     * @param params The parameters for the command, first parameter is the team name, second parameter is the player name.
     * @return A string representing the result of the command execution.
     */
    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "team_add", params.length-1, 2);
        if (result != null && result.isEmpty()) {
            User user = users.getLoggedUser();
            if (user.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {
                String teamName = params[1];
                String playerDNI = params[2];

                Team team = teams.getTeamByName(teamName);
                Player player = players.getPlayerByDni(playerDNI);
                if (team == null) {
                    throw new RoleException("Equipo no encontrado");
                } else if (player == null) {
                    throw new RoleException("Jugador no encontrado");
                } else {
                    if (team.isPlayerInTeamByDni(playerDNI)){
                        throw new DuplicateException("El jugador ya esta en un equipo");
                    } else {
                        team.insertPlayer(player);
                        result = "Jugador añadido correctamente al equipo " + team.getTeamName();
                    }
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
        return "team_add teamName;playerDNI";
    }
}
