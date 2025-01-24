package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.MatchController;
import upm.app.controler.structures.PlayerController;
import upm.app.controler.structures.UserController;
import upm.app.model.Player;
import upm.app.model.UserRole;

public class Matchmaking extends Command {
    private UserController userController;
    private MatchController matchController;
    private PlayerController playerController;

    public Matchmaking(UserController userController, MatchController matchController, PlayerController playerController) {
        this.userController = userController;
        this.matchController = matchController;
        this.playerController = playerController;
    }

    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "matchmaking", params.length -1,2 );
        if (result != null && result.isEmpty()){
            if (userController.getLoggedUser().getRole() != UserRole.ADMIN) {
                throw new RoleException("You are not an admin");
            }
            String dni_player_one = params[1];
            String dni_player_two = params[2];
            Player playerOne = playerController.getPlayerByDni(dni_player_one);
            Player playerTwo = playerController.getPlayerByDni(dni_player_two);
            if (!matchController.matchmake(playerOne,playerTwo)){
                result = "Ha ocurrido un error";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "matchmaking dni_player_one;dni_player_two";
    }
}
