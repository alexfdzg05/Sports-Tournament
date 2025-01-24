package upm.app.controler;

import upm.app.controler.command.Command;
import upm.app.controler.command.adminCommands.*;
import upm.app.controler.command.playerCommands.Statistics_Show;
import upm.app.controler.command.playerCommands.Tournament_Add;
import upm.app.controler.command.playerCommands.Tournament_Remove;
import upm.app.controler.command.playerCommands.show_matchmake;
import upm.app.controler.command.publicCommands.Login;
import upm.app.controler.command.publicCommands.Logout;
import upm.app.controler.command.publicCommands.Tournament_List;
import upm.app.controler.structures.*;
import upm.app.model.*;

import java.util.Date;
import java.util.LinkedList;

public class Seeder {
    private CommandController commandController;
    private PlayerController playerController;
    private TeamController teamController;
    private TournamentController tournamentController;
    private UserController userController;
    private MatchController matchController;


    public Seeder(CommandController commandController, PlayerController playerController, TeamController teamController, TournamentController tournamentController, UserController userController, MatchController matchController) {
        this.commandController = commandController;
        this.playerController = playerController;
        this.teamController = teamController;
        this.tournamentController = tournamentController;
        this.userController = userController;
        this.matchController = matchController;
    }

    public void seed(){
        Command[] command = {
                new Login(userController),
                new Logout(userController),
                new Tournament_List(userController, tournamentController),
                new Statistics_Show(userController),
                new Tournament_Add(tournamentController, teamController, userController),
                new Tournament_Remove(userController, tournamentController, teamController),
                new Player_Create(playerController, userController),
                new Player_Delete(playerController, teamController, tournamentController, userController),
                new Team_Add(userController, teamController, playerController),
                new Team_Create(teamController, userController),
                new Team_Delete(teamController, tournamentController, userController),
                new Team_Remove(userController, teamController),
                new Tournament_Create(tournamentController, userController),
                new Tournament_Delete(tournamentController, userController),
                new Tournament_Matchmaking(tournamentController, userController),
                new Establish_Score(userController, playerController, teamController),
                new Matchmaking(userController,matchController, playerController),
                new show_matchmake(userController,matchController)
        };

        for (Command aux: command){
            commandController.insertCommand(aux);
        }
        Admin admin1 = new Admin("oscar.arranz@alumnos.upm.es","012345");
        userController.addUser(admin1);

        User[] users = {
                new Player("manuel@alumnos.upm.es","1234","Manuel","Sanchez","24T",admin1),
                new Player("kurt@alumnos.upm.es","1234","Kurt","Cobain","65J",admin1),
                new Player("sofia@alumnos.upm.es","1234","Sofia","Vergara","98P",admin1),
                new Player("robert@alumnos.upm.es","1234","Robert","Downey","12J",admin1),

                /*
                Pruebas del seeder 1
                 */

                new Player("fernando@alumnos.upm.es","1234","Fernando","Fernandez","13J",admin1),
                new Player("arturo@alumnos.upm.es","1234","Arturo","Perez","14J",admin1),
                new Player("sam@alumnos.upm.es","1234","Sam","Key","15J",admin1),
                new Player("Daniel@alumnos.upm.es","1234","Daniel","Montreal","16J",admin1),


        };

        for (User aux: users){
            userController.addUser(aux);
        }

        /*
        Pruebas del seeder 2
         */
        Team teamA = new Team("EquipoA",new LinkedList<>(),admin1);
        teamA.insertPlayer(playerController.getPlayerByEmail("robert@alumnos.upm.es"));

        Team teamB = new Team("EquipoB",new LinkedList<>(),admin1);
        teamB.insertPlayer(playerController.getPlayerByEmail("fernando@alumnos.upm.es"));
        teamB.insertPlayer(playerController.getPlayerByEmail("arturo@alumnos.upm.es"));

        Team teamC = new Team("EquipoC",new LinkedList<>(),admin1);
        teamC.insertPlayer(playerController.getPlayerByEmail("sam@alumnos.upm.es"));
        teamC.insertPlayer(playerController.getPlayerByEmail("Daniel@alumnos.upm.es"));

        Team teamD = new Team("EquipoD",new LinkedList<>(),admin1);

        teamController.insertTeam(teamA);
        teamController.insertTeam(teamB);
        teamController.insertTeam(teamC);

        teamController.insertTeam(teamD);

        Tournament tournament = new Tournament("Torneo1",new Date(2024,11,11),new Date(2024,12,12),"Amateur", "tenis", TournamentState.STARTED);
        tournament.addTeam(teamA);
        tournament.addTeam(teamB);
        tournament.addTeam(teamC);

        tournamentController.insertTournament(tournament);

        Player player = playerController.getPlayerByEmail("manuel@alumnos.upm.es");
        Player player1 = playerController.getPlayerByEmail("kurt@alumnos.upm.es");
        matchController.insertMatch(new Matches(player1,player));

    }
}
