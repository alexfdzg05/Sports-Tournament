package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.User;
import upm.app.model.UserRole;


/**
 * This class represents a command for creating a new tournament.
 * It extends the abstract Command class and implements the execute method.
 */
public class Tournament_Delete extends Command {

    /**
     * A list of tournaments to store the created tournaments.
     */
    private final TournamentController tournamentController;
    private final UserController users;
    /**
     * Constructor for Torunament_Create class.
     *
     * @param tournamentController A list of tournaments to store the created tournaments.
     * @param users A list of users to check who is the logged one.
     */
    public Tournament_Delete(TournamentController tournamentController, UserController users) {
        this.tournamentController = tournamentController;
        this.users = users;
    }

    /**
     * Executes the command to create a new tournament.
     *
     * @param params An array of strings containing the parameters for creating the tournament.
     *               The parameters are: name, startDate, endDate, league, sport, and state.
     * @return A string indicating the result of the command execution.
     */
    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "tournament_delete", params.length - 1, 1);
        if (result != null && result.isEmpty()) {
            String name = params[1];
            User user = users.getLoggedUser();
            if (user.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {
                if(tournamentController.getTournamentByName(name)!=null) {
                    tournamentController.removeTournament(tournamentController.getTournamentByName(name));
                    result = "Torneo "+name+" eliminado correctamente";
                }else{
                    throw new NotFoundException("Torneo con nombre " + name + " no existe");
                }
            }

        }
        return result;
    }

    /**
     * Returns a string representation of the command.
     *
     * @return A string in the format "tournament-create [name;startDate;endDate;league;sport;state]".
     */
    @Override
    public String toString() {
        return "tournament_delete tournamentName";
    }
}