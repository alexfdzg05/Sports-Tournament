package upm.app.controler.command.playerCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.MatchController;
import upm.app.controler.structures.UserController;
import upm.app.model.Player;
import upm.app.model.User;
import upm.app.model.UserRole;

import javax.management.relation.Role;

public class show_matchmake extends Command {

    private UserController userController;

    private MatchController matchController;

    public show_matchmake(UserController userController, MatchController matchController) {
        this.userController = userController;
        this.matchController = matchController;
    }

    @Override
    public String execute(String[] params) {
     String result = testparams(params[0], "show_matchmake", params.length - 1, 0);
     if (result != null && result.isEmpty()) {
         if (userController.getLoggedUser().getRole() != UserRole.PLAYER) {
             throw new RoleException("You are not a player");
         } else {
             Player player = (Player) userController.getLoggedUser();
             result = matchController.getMatchesParticipating(player);
         }
     }
     return result;
    }

    @Override
    public String toString() {
        return "show_matchmake";
    }
}
