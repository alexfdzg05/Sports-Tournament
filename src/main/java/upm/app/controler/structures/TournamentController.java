package upm.app.controler.structures;

import upm.app.console.CLI;
import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.*;
import upm.app.model.mySQL.TournamentDAO;

import java.util.*;

public class TournamentController {
    private TournamentDAO tournamentDAO;
    public TournamentController(){
        this.tournamentDAO = new TournamentDAO();
    }
    public String listTournaments(){
        StringBuffer sb = new StringBuffer();
        for (Tournament tournament : tournamentDAO.findAll()){
            sb.append("Tournament: ").append(tournament.getName()).append("\n");
            sb.append("Participants: ").append("\n");
            List<Player> participants = new LinkedList<>(tournament.getPlayerList());
            Collections.shuffle(participants, new Random());
            for (Player player : participants){
                sb.append("- ").append(player.toString()).append("\n");
            }
        }
        return sb.toString();
    }
    public String listTournamentsRanked(User actualUser){
        StringBuffer sb = new StringBuffer();
        if (actualUser.getRole().equals(UserRole.ADMIN)){
            LinkedList<Tournament> rankedTournaments = rankTournaments();
            for (Tournament tournament : rankedTournaments){
                if (!tournament.getState().equals(TournamentState.FINISHED)){
                    sb.append("Tournament: ").append(tournament.getName()).append("\n");
                    sb.append("Participants: ").append("\n");
                    List<Player> participants = new LinkedList<>(tournament.getPlayerList());
                    participants.sort(Comparator.comparingDouble(Player::getScore));
                    for (Player player : participants) {
                        sb.append("- ").append(player.toString()).append("\n");
                    }
                } else {
                    tournamentDAO.delete(tournament);
                }
            }
        } else {
            LinkedList<Tournament> rankedTournaments = rankTournaments();
            for (Tournament tournament : rankedTournaments) {
                sb.append("Tournament: ").append(tournament.getName()).append("\n");
                sb.append("Participants: ").append("\n");
                List<Player> participants = new LinkedList<>(tournament.getPlayerList());
                participants.sort(Comparator.comparingDouble(Player::getScore));
                for (Player player : participants) {
                    sb.append("- ").append(player.toString()).append("\n");
                }
            }
        }
        return sb.toString();
    }
    public LinkedList<Tournament> rankTournaments(){
        List<Tournament> tournaments = tournamentDAO.findAll();
        LinkedList<Tournament> inOrder = new LinkedList<>(tournaments);
        inOrder.sort(Comparator.comparing(Tournament::getStartDate));
        return inOrder;
    }
    public LinkedList<Tournament> getTournamentList(){
        List<Tournament> tournaments = tournamentDAO.findAll();
        return new LinkedList<>(tournaments);
    }
    public Tournament getTournamentByName(String name){
        return tournamentDAO.findByID(name);
    }
    public void insertTournament(Tournament tournament){
        tournamentDAO.save(tournament);
    }
    public void removeTournament(Tournament tournament){
        tournamentDAO.delete(tournament);
    }
    public LinkedList<Tournament> getTeamTournaments(Team team){
        LinkedList<Tournament> tournaments = new LinkedList<>();
        for (Tournament tournament : tournamentDAO.findAll()){
            if (tournament.isTeamParticipating(team)){
                tournaments.add(tournament);}
        }
        return tournaments;
    }
    public boolean isPlayerParticipating(Player player){
        boolean result = false;
        for (Tournament tournament : tournamentDAO.findAll()){
            if (tournament.hasStarted()){
                if (tournament.isPlayerParticipating(player)){
                    result = true;
                }
            }
        }
        return result;
    }
    public boolean isTeamParticipating(Team team){
        boolean result = false;
        for (Tournament tournament : tournamentDAO.findAll()){
            if (tournament.hasStarted()){
                if (tournament.isTeamParticipating(team)){
                    result = true;
                }
            }
        }
        return result;
    }
    private Player getValidPlayer(LinkedList<Player> list, int x) { //ESTO ES PROVISIONAL, NO SABEMOS SI SE PUEDE HACER ASI
        boolean exit = false;
        Player player = null;
        String dni;
        String queJugador = "";
        if (!list.isEmpty()) {
            while (player == null && !exit) {
                if (x == 1) {
                    queJugador = "Primer";
                } else if (x == 2) {
                    queJugador = "Segundo";
                }

                CLI.printInput("Lista de jugadores disponibles");
                CLI.showPlayers(list);
                dni = CLI.stringInput("Ingrese DNI del " + queJugador + " jugador ('exit' para salir) :");
                if (dni.equals("exit")) {
                    exit = true;
                } else {
                    for (Player player1: list){
                        if (player1.getDNI().equals(dni)){
                            player = player1;
                        }
                    }
                    if (player == null) {
                        throw new NotFoundException("Jugador no encontrado");
                    }
                }
            }
        }
        return player;
    }
    public String manualMatchmaking(Tournament tournament) {  //ESTO ES PROVISIONAL, NO SABEMOS SI SE PUEDE HACER ASI
        if (tournament.getState() != TournamentState.STARTED) {
            throw new BadRequestException("El torneo todavia no ha empezado");
        }

        LinkedList<Player> listaParticipantesAux = tournament.getPlayerList();
        LinkedList<Player> listaParticipantes = tournament.getPlayerList();
        List<Matches> matches = tournament.getMatchList();
        Matches match;
        Player player1, player2;

        if (listaParticipantes.size() >= 2) {
            throw new BadRequestException("No hay suficientes jugadores en el torneo para realizar el emparejamiento manual");
        }

        if (listaParticipantes.size() % 2 == 0) {
            throw new BadRequestException("El numero de jugadores en el torneo es impar");
        }
        while (!listaParticipantes.isEmpty()) {

            player1 = getValidPlayer(listaParticipantes, 1);
            if (player1 == null) {
                return "El matchmaking manual ha sido cancelado.";
            }
            listaParticipantes.remove(player1.getDNI());

            player2 = getValidPlayer(listaParticipantes, 2);
            if (player2 == null) {
                return "El matchmaking manual ha sido cancelado.";
            }
            listaParticipantes.remove(player2.getDNI());

            match = new Matches(player1, player2);
            matches.add(match);
        }
        tournament.setMatchList(matches);//LISTAPARTi
        tournament.setPlayerList(listaParticipantesAux);

        return "Emparejamiento manual realizado con éxito.";
    }
    public String automaticRandomMatchmaking(Tournament tournament) {
        if (tournament.getState() == TournamentState.STARTED) {
            List<Player> listaParticipantes = new LinkedList<>(tournament.getPlayerList());
            List<Matches> matches = tournament.getMatchList();
            Matches match;
            Player player1, player2;

            Random random = new Random();
            if (listaParticipantes.size() >= 2) {
                throw new BadRequestException("No hay suficientes jugadores en el torneo para realizar el emparejamiento automatico");
            }
            if (listaParticipantes.size() % 2 == 0) {
                throw new BadRequestException("El numero de jugadores en el torneo es impar");

            }
            while (!listaParticipantes.isEmpty()) {
                int index1 = random.nextInt(0,listaParticipantes.size());
                player1 = listaParticipantes.remove(index1);
                int index2;
                do {
                    index2 = random.nextInt(0,listaParticipantes.size());
                } while (index2 == index1);
                player2 = listaParticipantes.remove(index2);

                match = new Matches(player1, player2);
                matches.add(match);

            }
            tournament.setMatchList(matches);

            return "Emparejamiento automático realizado con éxito.";
        } else {
            throw new BadRequestException("El torneo no ha empezado"); // AUN NO SE EN QUE ESTADO SE HACE EL MATCHMAKE
        }
    }
}
