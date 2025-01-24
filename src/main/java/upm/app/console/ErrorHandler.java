package upm.app.console;

import upm.app.controler.command.Command;
import upm.app.controler.structures.CommandController;
import upm.app.controler.structures.UserController;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.UserRole;

public class ErrorHandler {
    private final CLI cli;
    private final UserController userController;
    private final CommandController commandController;

    public ErrorHandler(CLI cli, UserController userController, CommandController commandController) {
        this.cli = cli;
        this.userController = userController;
        this.commandController = commandController;
    }

    public void start(){
        boolean exit = false;
        CLI.printInput("Welcome to the system managment sports of the UPM");
        CLI.printInput("List of avaiable commands: ");
        do {
            try {
                if (userController.getLoggedUser() == null) {
                    String command = cli.getCommand(UserRole.USER);
                    if (command.equals("exit")) {
                        exit = true;
                    } else {
                        StringBuffer output = new StringBuffer();

                        String[] strings;
                        if (command.contains(" ")) {
                            command = command.replace(" ", ";");
                            strings = command.split(";");
                        } else {
                            strings = new String[1];
                            strings[0] = command;
                        }

                        for (Command actual : commandController.getCommandList()) {
                            String aux = actual.execute(strings);
                            if (aux != null) {
                                output.append(aux).append("\n");
                            }
                        }

                        if (!output.isEmpty()) {
                            CLI.printInput(output.toString());
                        } else {
                            throw new NotFoundException("Comando no encontrado");
                        }
                    }
                } else {
                    String command = cli.getCommand(userController.getLoggedUser().getRole());
                    if (command.equals("exit")) {
                        exit = true;
                    } else {
                        StringBuffer output = new StringBuffer();

                        String[] strings;
                        if (command.contains(" ")) {
                            command = command.replace(" ", ";");
                            strings = command.split(";");
                        } else {
                            strings = new String[1];
                            strings[0] = command;
                        }

                        for (Command actual : commandController.getCommandList()) {
                            String aux = actual.execute(strings);
                            if (aux != null) {
                                output.append(aux).append("\n");
                            }
                        }

                        if (!output.isEmpty()) {
                            CLI.printInput(output.toString());
                        } else {
                            throw new NotFoundException("Comando no encontrado");
                        }
                    }
                }
            } catch (Exception e){
                CLI.printInput("\u001B[31m" + "SE HA PRODUCIDO UN ERROR -> " + e.getClass().getSimpleName() + " -> " + e.getMessage() + "\n" + "\u001B[0m");
            }
        }while (!exit);
    }
}
