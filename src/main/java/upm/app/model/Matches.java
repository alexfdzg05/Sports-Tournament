package upm.app.model;

import javax.persistence.*;

/**
 * Match is a class that will create all the attributes belonging to the class itself,
 * methods will appear to initialize the matches, check between objects (players), and
 * provide outputs in a specific format
 *
 * @author David Peláez de la Osa
 * @author Óscar Arranz Álvez
 * @author David Gérard Méndez
 * @author Alejandro Fernández Guerrero
 */

@Entity
@Table(name = "matches")
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "first_player_dni", nullable = false)
    private Player firstPlayer;
    @ManyToOne
    @JoinColumn(name = "second_player_dni", nullable = false)
    private Player secondPlayer;


    /**
     * Constructor of the class
     *
     * @param firstPlayer  player 1
     * @param secondPlayer player 2
     */
    public Matches(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
    protected Matches(){
        // Constructor vacío requerido por JPA
    }


    /**
     * Method that will check if any of the players match the ones passed as a parameter, it will
     * be used to determine if one of the has already been previously paired
     *
     * @param firstPlayer  player 1
     * @param secondPlayer player 2
     * @return True if they match and False if not
     */
    public boolean containsPlayer(Player firstPlayer, Player secondPlayer) {
        return (this.firstPlayer.equals(firstPlayer) || this.firstPlayer.equals(secondPlayer)) || (this.secondPlayer.equals(firstPlayer) || this.secondPlayer.equals(secondPlayer));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    /**
     * toString method that returns a specific format
     *
     * @return Example of format ----> Luis vs Manuel
     */
    public String toString() {
        return firstPlayer.getName() + " vs " + secondPlayer.getName();
    }
}
