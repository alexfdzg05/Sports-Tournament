package upm.app.console;

import upm.app.controler.command.ECommand;
import upm.app.model.Player;
import upm.app.model.UserRole;

import java.util.*;

public class CLI {
    private static Scanner scanner;

    public CLI() {
        scanner = new Scanner(System.in);
    }
    public String getCommand(UserRole role){
        StringBuffer sb = new StringBuffer();
        List<ECommand> allowedCommands = ECommand.getCommandsByRole(role);
        for (ECommand command : allowedCommands){
            sb.append(command.getCommand()).append("\n");
        }

        sb.append("Insert command: ");
        System.out.println(sb.toString());
        return scanner.nextLine();
    }
    public static void printInput(String input){
        System.out.println(input);
    }
    public static String stringInput(String msg){
        if(!msg.isEmpty()) {
            System.out.println(msg);
        }
        return scanner.nextLine();
    }
    public static void showPlayers(LinkedList<Player> players){
        for (Player player : players) {
            System.out.println(player);
        }
    }
}
