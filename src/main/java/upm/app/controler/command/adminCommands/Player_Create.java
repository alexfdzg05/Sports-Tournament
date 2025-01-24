package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.DuplicateException;
import upm.app.model.Admin;
import upm.app.model.Player;
import upm.app.model.User;
import upm.app.model.UserRole;
import upm.app.controler.structures.PlayerController;

/**
 * This class represents a command to create a new player in the system.
 * It extends the abstract Command class and implements the execute method.
 */
public class Player_Create extends Command {

    private final PlayerController playerController;
    private final UserController users;

    /**
     * Constructor for Player_Create class.
     *
     * @param playerController The list of players in the system.
     * @param users      The list of users in the system.
     */
    public Player_Create(PlayerController playerController, UserController users) {
        this.playerController = playerController;
        this.users = users;
    }

    /**
     * Executes the command to create a new player.
     * When registering a player in the system, it must be stored in the system the
     * administrator that has created it. It can only be created if the same player does not already exist in the system.
     *
     * @param params An array of strings containing the command parameters.
     * @return A string representing the result of the command execution.
     */
    @Override
    public String execute(String[] params) {
        String result = testparams(params[0], "player_create", params.length - 1, 5);
        if (result != null && result.isEmpty()) {
            User user = users.getLoggedUser();
            if (user.getRole() != UserRole.ADMIN) {
                throw new RoleException("No eres un administrador");
            } else {
                String playerName = params[1];
                String playerLastName = params[2];
                String dni = params[3];
                String email = params[4];
                String password = params[5];

                if (playerController.existsPlayerByDNI(dni)) {
                    throw new DuplicateException("Jugador con DNI " + dni + " ya existe");
                } else {
                    Player newPlayer = new Player(email, password, playerName, playerLastName, dni,(Admin) users.getLoggedUser());
                    users.addUser(newPlayer);
                    playerController.insertPlayer(newPlayer);
                    result = "Jugador creado correctamente: " + playerName + " " + playerLastName;
                }
            }
        }
        return result;
    }

    /**
     * Returns a string representation of the command.
     *
     * @return A string in the format "Player_Create [Name;LastName;dni;email;password]".
     */
    public String toString() {
        return "player_create name;lastName;dni;email;password";
    }
}
