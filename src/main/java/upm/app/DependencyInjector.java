package upm.app;

import upm.app.console.CLI;
import upm.app.console.ErrorHandler;
import upm.app.controler.Seeder;
import upm.app.controler.structures.*;

public class DependencyInjector {
    private final CLI cli;
    /*
    private final CommandControllerMySQL commandControllerMySQL;
    private final MatchControllerMySQL matchControllerMySQL;
    private final PlayerControllerMySQL playerControllerMySQL;
    private final TeamControllerMySQL teamControllerMySQL;
    private final TournamentControllerMySQL tournamentControllerMySQL;
    private final UserControllerMySQL userControllerMySQL;
    */
    private final CommandController commandController ;
    private final MatchController matchController;
    private final PlayerController playerController;
    private final TeamController teamController;
    private final TournamentController tournamentController;
    private final UserController userController;
    private final Seeder seeder;
    private final ErrorHandler errorHandler;
    private static final DependencyInjector dependencyInjector = new DependencyInjector();
    public DependencyInjector() {
        this.cli = new CLI();

        this.matchController = new MatchController();
        this.playerController = new PlayerController();
        this.commandController = new CommandController();
        this.teamController = new TeamController();
        this.tournamentController = new TournamentController();
        this.userController = new UserController();
        this.seeder = new Seeder(commandController, playerController, teamController, tournamentController,
                                 userController, matchController);
        this.errorHandler = new ErrorHandler(cli, userController, commandController);
        seeder.seed();

        /*this.commandControllerMySQL = new CommandControllerMySQL();
        this.matchControllerMySQL = new MatchControllerMySQL();
        this.playerControllerMySQL = new PlayerControllerMySQL();
        this.teamControllerMySQL = new TeamControllerMySQL();
        this.tournamentControllerMySQL = new TournamentControllerMySQL();
        this.userControllerMySQL = new UserControllerMySQL();
        this.seeder = new Seeder(commandControllerMySQL, playerControllerMySQL, teamControllerMySQL, tournamentControllerMySQL,
                                 userControllerMySQL, matchControllerMySQL);
        this.errorHandler = new ErrorHandler(cli, userControllerMySQL, commandControllerMySQL);
        seeder.seed()*/
    }
    public void run(){
        errorHandler.start();
    }

    public static DependencyInjector getDependencyInjector(){
        return dependencyInjector;
    }
}
