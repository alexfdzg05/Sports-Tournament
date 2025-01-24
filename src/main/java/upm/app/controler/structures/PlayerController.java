package upm.app.controler.structures;

import upm.app.model.Player;
import upm.app.model.mySQL.PlayerDAO;

/**
 * Class responsible for managing the list of players by searching for them, adding them, removing them
 * and selecting them
 *
 * @author Óscar Arranz Álvez
 * @author David Gérard Méndez
 * @author Alejandro Fernández Guerrero
 * @author David Pelaez de la Osa
 */
public class PlayerController {
    private final PlayerDAO playerDAO;

    public PlayerController() {
        this.playerDAO = new PlayerDAO();
    }
    public Player getPlayerByEmail(String email){
        return playerDAO.findByID(email);
    }

    public boolean existsPlayerByEmail(String email){
        return playerDAO.findByID(email) != null;
    }
    public void insertPlayer(Player player){
        playerDAO.save(player);
    }
    public void deletePlayer(Player player){
        playerDAO.delete(player);
    }
}
