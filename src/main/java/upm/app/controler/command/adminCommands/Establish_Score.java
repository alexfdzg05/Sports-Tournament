package upm.app.controler.command.adminCommands;

import upm.app.controler.command.Command;
import upm.app.controler.command.exceptions.RoleException;
import upm.app.controler.structures.PlayerController;
import upm.app.controler.structures.TeamController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Player;
import upm.app.model.Team;
import upm.app.model.UserRole;
import upm.app.model.exceptions.InvalidAtributeException;

public class Establish_Score extends Command {
    private UserController userController;
    private PlayerController playerController;
    private TeamController teamController;

    public Establish_Score(UserController userController, PlayerController playerController, TeamController teamController) {
        this.userController = userController;
        this.playerController = playerController;
        this.teamController = teamController;
    }

    @Override
    public String execute(String[] params) {
        String result = super.testparams(params[0], "establish_score", params.length - 1, 3);
        if (result != null && result.isEmpty()){
            if (userController.getLoggedUser().getRole() != UserRole.ADMIN){
                throw new RoleException("No eres un administrador");
            }
            Player player = playerController.getPlayerByDni(params[1]);
            if (player == null){
                throw new NotFoundException("Jugador no encontrado");
            }
            if (Double.parseDouble(params[2]) < 0){
                throw new InvalidAtributeException("No puede ser un valor negativo");
            }

            switch (params[3].toLowerCase()){
                case "-s":
                    player.setScore(Double.parseDouble(params[2]) + player.getScore());
                    result = "Puntuacion cambiada con exite";
                    break;
                case "-w":
                    player.setWinMatches(Integer.parseInt(params[2]) + player.getWinMatches());
                    result = "El numero de partidos ganados cambiado con exito";
                    break;
                case "-f":
                    player.setFault(Integer.parseInt(params[2]) + player.getFault());
                    result = "El numero de faltas cambiado con exito";
                    break;
                case "-a":
                    player.setAssistancePoints(Double.parseDouble(params[2]) + player.getAssistancePoints());
                    result = "El numero de asistencias cambiado con exito";
                    break;
                case "-wt":
                    player.setWinTournamentInThePast(Double.parseDouble(params[2]) + player.getWinTournamentInThePast());
                    result = "El numero de torneos ganados cambiado con exito";
                    break;
                case "-m":
                    player.setRaisedMoneyByTournaments(Double.parseDouble(params[2]) + player.getRaisedMoneyByTournaments());
                    result = "La cantidad de dinero generada con los torneos cambiada con exito";
                    break;
                default:
                    throw new BadRequestException("""
                            Tipo de puntuacion no encontrado.Utilice:
                            -s para aumentar la puntuacion del jugador
                            -w para aumentar el numero de partidos ganados del jugador
                            -f para aumentar la cantidad de faltas cometidas por el jugador
                            -a para aumentar la cantidad de asistencias del jugador
                            -wt para aumentar la cantidad de torneos ganados por el jugador
                            -m para aumentar la cantidad de dinero generado en torneos por el jugador""");
            }

            if (teamController.getTeamByDNI(player.getDNI()) != null){
                teamController.teamUpdate(player);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "establish_score player_DNI;score;[-s/-w/-f/-a/-wt/-m]";
    }
}
