package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.PlayerController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Player;
import upm.app.model.UserRole;

import javax.management.relation.Role;

public class Player_Statistics extends Command {
    private UserController userController;
    private PlayerController playerController;

    public Player_Statistics(UserController userController, PlayerController playerController) {
        this.userController = userController;
        this.playerController = playerController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testparams(params[0], "player_statistics", params.length-1, 1);
        if (userController.getLoggedUser().getRole() != UserRole.ADMIN){
            throw new RoleException("No eres un administrador");
        }
        Player player = playerController.getPlayerByDni(params[1]);
        if (player == null){
            throw new NotFoundException("Jugador no encontrado");
        }
        result = "Puntuacion: " + player.getScore() + "\n" +
                "Faltas: " + player.getFault() + "\n" +
                "Partidos ganados: " + player.getWinMatches() + "\n" +
                "Assistencias: " + player.getAssistancePoints() + "\n" +
                "Torneos ganados: " + player.getWinTournamentInThePast() + "\n" +
                "Dinero generado: " + player.getRaisedMoneyByTournaments();
        return result;
    }

    @Override
    public String toString() {
        return "player_statistics playerDNI";
    }
}
