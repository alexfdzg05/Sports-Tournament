package upm.app.controler.command.publicCommands;

import upm.app.controler.command.Command;
import upm.app.controler.structures.UserController;

public class Login extends Command {
    private UserController userController;

    public Login(UserController userController) {
        this.userController = userController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testparams(params[0], "login", params.length - 1, 2);

        if (result != null && result.isEmpty()){
            result = userController.login(params[1], params[2]);
        }
        return result;
    }
    public String toString() {
        return "login email;password";
    }
}
