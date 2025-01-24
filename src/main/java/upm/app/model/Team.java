package upm.app.model;

import upm.app.model.exceptions.InvalidAtributeException;

import javax.persistence.*;
import java.util.*;
//TODO darle la cosa esa al id

/**
 * Class for representing a team in the sports organization.
 */
@Entity
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "team_name", unique = true, nullable = false)
    private final String TeamName;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_name")
    private List<Player> players;
    @Column(name = "score", nullable = false)
    private double score;
    @Column(name = "winMatches", nullable = false)
    private int winMatches;
    @Column(name = "fault", nullable = false)
    private int faults;
    @Column(name = "assistancePoints", nullable = false)
    private double assistancePoints;
    @Column(name = "winTournamentsInThePast", nullable = false)
    private double winTournamentInThePast;
    @Column(name = "raisedMoneyByTournaments", nullable = false)
    private double raisedMoneyByTournaments;
    @ManyToOne
    @JoinColumn(name = "creator_email", nullable = false)
    private final Admin admin; // Se debe guardar qué administrador lo ha creado

    /**
     * Constructs a new Team object with the given params name, players, and admin.
     * It initializes the team's statistics by accumulating up the player's statistics.
     *
     * @param name    The name of the team.
     * @param players The list of players in the team.
     * @param admin   The admin who created the team.
     */
    public Team(String name, LinkedList<Player> players, Admin admin) {
        this.TeamName = name;
        this.admin = admin;
        this.players = new LinkedList<>();
        this.players.addAll(players);
        double score = 0;
        int winMatches = 0;
        int faults = 0;
        double assistancePoints = 0.0;
        double winTournamentInThePast = 0.0;
        double raisedMoneyByTournaments = 0.0;
        for (Player player : players) {
            score += player.getScore();
            winMatches += player.getWinMatches();
            faults += player.getFault();
            assistancePoints += player.getAssistancePoints();
            winTournamentInThePast += player.getWinTournamentInThePast();
            raisedMoneyByTournaments += player.getRaisedMoneyByTournaments();

        }
        int size = players.size();
        if (size != 0) {
            this.score = score / size;
            this.winMatches = winMatches / size;
            this.faults = faults / size;
            this.assistancePoints = assistancePoints / size;
            this.winTournamentInThePast = winTournamentInThePast / size;
            this.raisedMoneyByTournaments = raisedMoneyByTournaments / size;
        }

    }
    public Team(String name, Admin admin) {
        this.TeamName = name;
        this.admin = admin;
        this.players = new LinkedList<>();
        this.score = 0;
        this.winMatches = 0;
        this.faults = 0;
        this.assistancePoints = 0;
        this.winTournamentInThePast = 0;
        this.raisedMoneyByTournaments = 0;

    }
    protected Team(){
        this.TeamName = null;
        this.admin = null;
        // Constructor requerido por JPA
    }

    /**
     * Checks if a player is in the team by his Dni.
     *
     * @param dni The DNI of the player to check.
     * @return True if the player is in the team (by dni), false if not.
     */
    public boolean isPlayerInTeamByDni(String dni) {
        boolean result = false;
        for (Player player : players){
            if (Objects.equals(player.getDNI(), dni)){
                return true;
            }
        }
        return result;
    }

    /**
     * Inserts player from the team and updates the team's statistics.
     *
     * @param player The Object player to be inserted.
     */
    public void insertPlayer(Player player) {
        addPlayerUpdate(player);
        players.add(player);
    }

    /**
     * Deletes a player from the team and updates the team's statistics.
     *
     * @param dni The name of the player to be deleted.
     */
    public void deletePlayer(String dni) {
        for (Player player : players){
            if (player.getDNI().equalsIgnoreCase(dni)){
                deletePlayerUpdate(player);
                players.remove(player);
            }
        }
    }

    public void addPlayerUpdate(Player player){
        double score = player.getScore();
        int winMatches = player.getWinMatches();
        int faults = player.getFault();
        double assistancePoints = player.getAssistancePoints();
        double winTournamentsInThePast = player.getWinTournamentInThePast();
        double raisedMoneyByTournaments = player.getRaisedMoneyByTournaments();
        for (Player player1 : this.getPlayers()){
            score +=  player1.getScore();
            winMatches += player1.getWinMatches();
            faults += player1.getFault();
            assistancePoints += player1.getAssistancePoints();
            winTournamentsInThePast += player1.getWinTournamentInThePast();
            raisedMoneyByTournaments += player1.getRaisedMoneyByTournaments();
        }
        int size = this.getPlayers().size();
        if (size != 0) {
            this.setScore(score / size);
            this.setWinMatches(winMatches / size);
            this.setFaults(faults / size);
            this.setAssistancePoints(assistancePoints / size);
            this.setWinTournamentInThePast(winTournamentsInThePast / size);
            this.setRaisedMoneyByTournaments(raisedMoneyByTournaments / size);
        }
    }
    public void deletePlayerUpdate(Player player){
        double score = 0;
        int winMatches = 0;
        int faults = 0;
        double assistancePoints = 0;
        double winTournamentsInThePast = 0;
        double raisedMoneyByTournaments = 0;
        for (Player player1 : this.getPlayers()){
            if (player1 != player){
                score +=  player1.getScore();
                winMatches += player1.getWinMatches();
                faults += player1.getFault();
                assistancePoints += player1.getAssistancePoints();
                winTournamentsInThePast += player1.getWinTournamentInThePast();
                raisedMoneyByTournaments += player1.getRaisedMoneyByTournaments();
            }
        }
        double finalScore = score - player.getScore() ;
        int finalWinMatches = winMatches - player.getWinMatches();
        int finalFaults = faults - player.getFault();
        double finalAssistancePoints = assistancePoints - player.getAssistancePoints();
        double finalWinTournamentsInThePast = winTournamentsInThePast - player.getWinTournamentInThePast();
        double finalRaisedMoneyByTournaments = raisedMoneyByTournaments - player.getRaisedMoneyByTournaments();
        int size = getPlayers().size();
        if (size > 1) {
            this.setScore(finalScore / size - 1);
            this.setWinMatches(finalWinMatches / size);
            this.setFaults(finalFaults / size);
            this.setAssistancePoints(finalAssistancePoints / size);
            this.setWinTournamentInThePast(finalWinTournamentsInThePast / size);
            this.setRaisedMoneyByTournaments(finalRaisedMoneyByTournaments / size);
        }
    }


    // Aqui todos loss Get & Set metodos para la clase Player


    public String getTeamName() {
        return TeamName;
    }

    public LinkedList<Player> getPlayers() { //No operar sobre esta Estructura de datos, es solamente de vista
        return new LinkedList<>(players);
    }

    public double getScore() {
        return score;
    }

    public int getWinMatches() {
        return winMatches;
    }

    public int getFaults() {
        return faults;
    }

    public double getAssistancePoints() {
        return assistancePoints;
    }

    public double getWinTournamentInThePast() {
        return winTournamentInThePast;
    }

    public double getRaisedMoneyByTournaments() {
        return raisedMoneyByTournaments;
    }


    public void setScore(double score) {
        this.score = score;
    }

    public void setWinMatches(int winMatches) {
        this.winMatches = winMatches;
    }

    public void setFaults(int faults) {
        this.faults = faults;
    }

    public void setAssistancePoints(double assistancePoints) {
        this.assistancePoints = assistancePoints;
    }

    public void setWinTournamentInThePast(double winTournamentInThePast) {
        this.winTournamentInThePast = winTournamentInThePast;
    }

    public void setRaisedMoneyByTournaments(double raisedMoneyByTournaments) {
        this.raisedMoneyByTournaments = raisedMoneyByTournaments;
    }

}
