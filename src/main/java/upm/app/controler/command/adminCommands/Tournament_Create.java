package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.model.Tournament;
import upm.app.model.TournamentState;
import upm.app.model.UserRole;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class represents a command for creating a new tournament.
 * It extends the abstract Command class and implements the execute method.
 */
public class Tournament_Create extends Command {

    /**
     * A list of tournaments to store the created tournaments.
     */
    private final TournamentController tournamentController;
    private final UserController userController;

    /**
     * Constructor for Torunament_Create class.
     *
     * @param tournamentController A list of tournaments to store the created tournaments.
     */
    public Tournament_Create(TournamentController tournamentController, UserController userController) {
        this.tournamentController = tournamentController;
        this.userController = userController;
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
        String result = testparams(params[0], "tournament_create", params.length - 1, 6);
        if (result != null && result.isEmpty()) {
            if (userController.getLoggedUser().getRole() == UserRole.ADMIN) {
                String name = params[1];
                String startDateStr = params[2];
                String endDateStr = params[3];
                String league = params[4];
                String sport = params[5];
                String stateStr = params[6];
                try {
                    // Convert the start and end dates from String to Date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date startDate = dateFormat.parse(startDateStr);
                    Date endDate = dateFormat.parse(endDateStr);

                    // Convert the state from String to TournamentState
                    TournamentState state = TournamentState.valueOf(stateStr.toUpperCase());

                    Tournament tournament = new Tournament(name, startDate, endDate, league, sport, state);
                    tournamentController.insertTournament(tournament);
                    result = "Torneo creado correctamente.";
                } catch (Exception e) {
                    result = "Error al intentar crear el torneo: " + e.getMessage();
                }
            } else {
                throw new RoleException("No eres un administrador");
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
        return "tournament_create tournamentName;startDate;endDate;league;sport;state";
    }
}