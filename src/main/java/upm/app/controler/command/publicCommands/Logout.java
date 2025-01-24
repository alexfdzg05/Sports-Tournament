package upm.app.controler.command.publicCommands;

import upm.app.controler.command.Command;
import upm.app.controler.structures.UserController;

public class Logout extends Command {
    private UserController userController;
    public Logout(UserController userController) {
        this.userController = userController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testparams(params[0], "logout", params.length - 1, 0);
        if (result != null && result.isEmpty()) {
            result = userController.logOut();
        }
        return result;
    }
    public String toString() {
        return "logout";
    }
}
