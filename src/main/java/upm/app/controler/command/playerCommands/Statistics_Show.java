package upm.app.controler.command.playerCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Player;
import upm.app.model.UserRole;

public class Statistics_Show extends Command {
    private UserController userController;

    public Statistics_Show(UserController userController) {
        this.userController = userController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testparams(params[0], "statistics_show", params.length - 1, 1);
        if (result != null && result.isEmpty()) {
            if (userController.getLoggedUser().getRole() != UserRole.PLAYER) {
                throw new RoleException("No eres un jugador");
            }
            Player player = (Player) userController.getLoggedUser();
            if (player == null) {
                throw new NotFoundException("Jugador no encontrado");

            }
            if (params[1].equals("-csv")) {
                result = "score  faults  wins\n" +
                        player.getScore() + "\t\t" + player.getFault() + "\t\t" + player.getWinMatches();
            } else if (params[1].equals("-json")) {
                result = "score-" + player.getScore() + "\n" +
                        "faults-" + player.getFault() + "\n" +
                        "Wins-" + player.getWinMatches() + "\n";
            } else {
                throw new BadRequestException("Opcion invalida " + "\nFormato correcto: " + toString());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "statistics_Show [-csv/-json]";
    }
}
