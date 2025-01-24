package upm.app.model;

import java.util.*;
import javax.persistence.*;

//TODO darle la cosa esa al ID
/**
 * Class that represents the tournament.
 */
@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Column(name = "league", nullable = false)
    private String league;
    @Column(name = "sport", nullable = false)
    private String sport;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private TournamentState state;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament_name")
    private List<Player> playerList;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tournament_name")
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tournament_name")
    private List<Matches> matchesList;

    /**
     * Constructor for Tournament class.
     *
     * @param name      Name of the tournament.
     * @param startDate Starting date of the tournament.
     * @param endDate   Ending date of the tournament.
     * @param league    The league that the tournament belongs to.
     * @param sport     Sports played in the tournament.
     * @param state     Current state of the tournament.
     */

    public Tournament(String name, Date startDate, Date endDate, String league, String sport,
                      TournamentState state) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        this.sport = sport;
        this.state = state;
        this.playerList = new LinkedList<>();
        this.teamList = new LinkedList<>();
        this.matchesList = new LinkedList<>();
    }
    protected Tournament(){
        // Constructor requerido por JPA
    }

    /**
     * Method to add a team to the tournament.
     *
     * @param team that will be added to the tournament.
     */
    public void addTeam(Team team) {
        this.playerList.addAll(team.getPlayers());
        this.teamList.add(team);
    }

    public void removeTeam(Team team) {
        this.playerList.removeAll(team.getPlayers());
        this.teamList.remove(team);
    }

    /**
     * Checks if a given player is participating in the tournament.
     * <p>
     * //(se busca por el dni en la lista de jugadores del torneo al objecto jugador pasado)
     *
     * @param player The object player we want to know.
     * @return True if the player is found in the tournament's player list, false if not.
     */
    public boolean isPlayerParticipating(Player player) {
        return playerList.contains(player);
    }

    /**
     * Checks if a given team is participating in the tournament.
     *
     * @param team The team object to be checked.
     * @return True if the team is found in the tournament's team list, false if not.
     */
    public boolean isTeamParticipating(Team team) {
        return teamList.contains(team);
    }

    /**
     * Checks if the tournament has started.
     *
     * @return True if the tournament's state is STARTED, false otherwise.
     */
    public boolean hasStarted() {
        return state == TournamentState.STARTED;
    }


    // Aqui todos loss Get & Set metodos para la clase Player

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public TournamentState getState() {
        return state;
    }

    public LinkedList<Player> getPlayerList() {
        return new LinkedList<>(playerList);
    }

    public void setPlayerList(LinkedList<Player> playerList) {
        this.playerList = playerList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<Matches> getMatchList() {
        return matchesList;
    }

    public void setMatchList(List<Matches> matchesList) {
        this.matchesList = matchesList;
    }
}
