package upm.app.controler.command;

import upm.app.model.User;
import upm.app.model.UserRole;

import java.util.LinkedList;
import java.util.List;

public enum ECommand {
    PLAYER_CREATE("player_create", List.of(UserRole.ADMIN)),
    TEAM_CREATE("team_create",List.of(UserRole.ADMIN)),
    PLAYER_DELETE("player_delete", List.of(UserRole.ADMIN)),
    TEAM_DELETE("team_delete",List.of(UserRole.ADMIN)),
    TEAM_ADD("team_add", List.of(UserRole.ADMIN)),
    TEAM_REMOVE("team_remove", List.of(UserRole.ADMIN)),
    TOURNAMENT_CREATE("tournament_create", List.of(UserRole.ADMIN)),
    TOURNAMENT_DELETE("tournament_delete", List.of(UserRole.ADMIN)),
    TOURNAMENT_MATCHMAKING("tournament_matchmaking", List.of(UserRole.ADMIN)),
    ESTABLISH_SCORE("establish_score", List.of(UserRole.ADMIN)),
    PLAYER_STATISTICS("player_statistics", List.of(UserRole.ADMIN)),
    MATCHMAKING("matchmaking", List.of(UserRole.ADMIN)),
    SHOW_MATCHMAKE("show_matchmake", List.of(UserRole.PLAYER)),
    TOURNAMENT_ADD("tournament_add", List.of(UserRole.PLAYER)),
    TOURNAMENT_REMOVE("tournament_remove", List.of(UserRole.PLAYER)),
    STATISTICS_SHOW("statistics_show", List.of(UserRole.PLAYER)),

    LOGIN("login", UserRole.role()),
    LOGOUT("logout", UserRole.role()),
    TOURNAMENT_LIST("tournament_list", UserRole.role());
    private String command;
    private List<UserRole> allowedRoles;

    ECommand(String command, List<UserRole> allowedRoles) {
        this.command = command;
        this.allowedRoles = allowedRoles;
    }

    public String getCommand() {
        return command;
    }

    public List<UserRole> getAllowedRoles() {
        return allowedRoles;
    }

    public static List<ECommand> getCommandsByRole(UserRole role){
        List<ECommand> commands = new LinkedList<>();
        for (ECommand command:ECommand.values()){
            if (command.getAllowedRoles().contains(role)){
                commands.add(command);
            }
        }
        return commands;
    }
}
