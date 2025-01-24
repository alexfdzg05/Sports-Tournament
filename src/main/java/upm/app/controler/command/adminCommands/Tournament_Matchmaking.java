package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.TournamentController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.*;

public class Tournament_Matchmaking extends Command {
    private TournamentController tournamentController;
    private UserController userController;

    public Tournament_Matchmaking(TournamentController tournamentController, UserController userController) {
        this.tournamentController = tournamentController;
        this.userController = userController;
    }

    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "tournament_matchmaking", params.length - 1, 2);
        if (result != null && result.isEmpty()) {
            if (userController.getLoggedUser().getRole() == UserRole.ADMIN) {
                String tournamentName = params[1];
                String matchmakingType = params[2];

                Tournament tournament = tournamentController.getTournamentByName(tournamentName);
                if (tournament == null) {
                    throw new NotFoundException("Torneo no encontrado");
                }

                if (tournament.getState() != TournamentState.STARTED) {
                    throw new BadRequestException("El torneo todavia no esta en curso. El emparejamiento no se puede crear");
                }

                switch (matchmakingType.toLowerCase()) {
                    case "-m":
                        result = tournamentController.manualMatchmaking(tournament);
                        break;
                    case "-a":
                        result = tournamentController.automaticRandomMatchmaking(tournament);
                        break;
                    default:
                        throw new BadRequestException("Tipo de emparejamiento no reconocido. Utilice -m para manual o -a para automatico");
                }
            } else {
                throw new RoleException("No eres un administrador");
            }
        }
        return result;
    }

    public String toString() {
        return "tournament_matchmaking tournamentName;[-m/-a]";
    }
}
