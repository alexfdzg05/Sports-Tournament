package upm.app.controler.structures;

import upm.app.controler.command.Command;

import java.util.LinkedList;

public class CommandController {
    private LinkedList<Command> commandList;

    public CommandController() {
        commandList = new LinkedList<>();
    }
    public void insertCommand(Command command){
        commandList.add(command);
    }

    public LinkedList<Command> getCommandList() {
        return commandList;
    }
}
