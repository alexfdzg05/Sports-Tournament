package upm.app.controler.command.publicCommands;

import upm.app.controler.command.Command;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.NotFoundException;

public class Tournament_List extends Command {
    private UserController userController;
    private TournamentController tournamentController;

    public Tournament_List(UserController userController, TournamentController tournamentController) {
        this.userController = userController;
        this.tournamentController = tournamentController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testparams(params[0], "tournament_list", params.length - 1, 0);
        if (result != null && result.isEmpty()) {
            if (!tournamentController.getTournamentList().isEmpty()) {
                if (userController.getLoggedUser() == null) {
                    result = tournamentController.listTournaments();
                } else {
                    result = tournamentController.listTournamentsRanked(userController.getLoggedUser());
                }
            } else {
                throw new NotFoundException("No hay torneos");
            }
        }
        return result;
    }

    public String toString() {
        return "tournament_list";
    }
}
