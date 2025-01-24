package upm.app.model;

import upm.app.model.exceptions.InvalidAtributeException;

import javax.persistence.*;
//TODO Quitar cosas innecesarias de los metodos
/**
 * Class responsible for registering or deregistering a player.
 *
 * @author Óscar Arranz Álvez-
 * @author David Gérard Méndez
 * @author Alejandro Fernández Guerrero
 * @author David Pelaez de la Osa
 */
@Entity
@Table(name = "players")
@PrimaryKeyJoinColumn(name = "email")
public class Player extends User {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "dni", unique = true, nullable = false)
    private String DNI;
    @Column(name = "score", nullable = false)
    private double score;
    @Column(name = "winMatches", nullable = false)
    private int winMatches;
    @Column(name = "fault", nullable = false)
    private int fault;
    @Column(name = "assistancePoints", nullable = false)
    private double assistancePoints;
    @Column(name = "winTournamentsInThePast", nullable = false)
    private double winTournamentInThePast;
    @Column(name = "raisedMoneyByTournaments", nullable = false)
    private double raisedMoneyByTournaments;
    @ManyToOne
    @JoinColumn(name = "creator_email", nullable = false)
    private Admin creator;

    /**
     * Constructor for the Player class.
     *
     * @param email    The email of the player.
     * @param password The password of the player.
     * @param name     The name of the player.
     * @param surname  The surname of the player.
     * @param DNI      The DNI of the player.
     * @param creator    The admin who is registering the player.
     */
    public Player(String email, String password, String name, String surname, String DNI, Admin creator) {
        super(email, password, UserRole.PLAYER);
        this.name = name;
        this.surname = surname;
        this.DNI = DNI;
        this.score = 0.0;
        this.winMatches = 0;
        this.fault = 0;
        this.assistancePoints = 0.0;
        this.winTournamentInThePast = 0.0;
        this.raisedMoneyByTournaments = 0.0;
        this.creator = creator;
    }
    protected Player() {
        // Constructor vacío requerido por JPA
    }

    // Aqui todos loss Get & Set metodos para la clase Player

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getWinMatches() {
        return winMatches;
    }

    public void setWinMatches(int winMatches) {
        if (winMatches < 0){
            throw new InvalidAtributeException("No puede ser un numero negativo");
        }
        this.winMatches = winMatches;
    }

    public int getFault() {
        return fault;
    }

    public void setFault(int fault) {
        if (fault < 0){
            throw new InvalidAtributeException("No puede ser un numero negativo");
        }
        this.fault = fault;
    }

    public double getAssistancePoints() {
        return assistancePoints;
    }

    public void setAssistancePoints(double assistancePoints) {
        if (assistancePoints < 0){
            throw new InvalidAtributeException("No puede ser un numero negativo");
        }
        this.assistancePoints = assistancePoints;
    }

    public double getWinTournamentInThePast() {
        return winTournamentInThePast;
    }

    public void setWinTournamentInThePast(double winTournamentInThePast) {
        if (winTournamentInThePast < 0){
            throw new InvalidAtributeException("No puede ser un numero negativo");
        }
        this.winTournamentInThePast = winTournamentInThePast;
    }

    public double getRaisedMoneyByTournaments() {
        return raisedMoneyByTournaments;
    }

    public void setRaisedMoneyByTournaments(double raisedMoneyByTournaments) {
        this.raisedMoneyByTournaments = raisedMoneyByTournaments;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return A string in the format "name surname, DNI: DNI".
     */
    public String toString() {
        return name + " " + surname + ", DNI: " + DNI + ".";
    }
}
