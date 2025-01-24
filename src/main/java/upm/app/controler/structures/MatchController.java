package upm.app.controler.structures;

import upm.app.controler.structures.exceptions.DuplicateException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Matches;
import upm.app.model.Player;
import upm.app.model.mySQL.MatchDAO;

import java.util.*;

/**
 * Class responsible for managing the list of matches, pairing them, removing them and displaying them
 *
 * @author David Peláez de la Osa
 * @author Óscar Arranz Álvez
 * @author David Gérard Méndez
 * @author Alejandro Fernández Guerrero
 */
public class MatchController {
    private MatchDAO matchDAO;

    public MatchController() {
        this.matchDAO = new MatchDAO();
    }


    public boolean summonedPlayer(Player player){
        boolean exit = false;
        List<Matches> matchesList = matchDAO.findAll();
        for (Matches matches : matchesList){
            exit = matches.containsPlayer(player, player);
        }
        return exit;
    }
    public void insertMatch(Matches matches){
        matchDAO.save(matches);
    }
    public boolean matchmake(Player firstPlayer, Player secondPlayer) {
        boolean result;

        if (firstPlayer == null || secondPlayer == null || firstPlayer.equals(secondPlayer)) {
            result = false;
            if (firstPlayer == null && secondPlayer == null) {
                throw new NotFoundException("Jugadores no existentes");
            }else if (firstPlayer == null) {
                throw new NotFoundException("Jugador 1 no existente");
            } else if (secondPlayer == null) {
                throw new NotFoundException("Jugador 2 no existente");
            } else if (firstPlayer.equals(secondPlayer) ) {
                throw  new DuplicateException("Mismo jugador");
            }


        } else if (summonedPlayer(firstPlayer)) {
            result = false;
            throw new DuplicateException("Jugador 1 ya convocado");
        } else if (summonedPlayer(secondPlayer)) {
            result = false;
            throw new DuplicateException("Jugador 2 ya convocado");
        } else {
            insertMatch(new Matches(firstPlayer, secondPlayer));
            result = true;
        }

        return result;
    }
    public String getMatchesParticipating(Player player){
        StringBuffer sb = new StringBuffer();
        sb.append("Matches participating: ");
        for (Matches matches : matchDAO.findAll()) {
            if (matches.containsPlayer(player,player)) {
                sb.append(matches.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}








