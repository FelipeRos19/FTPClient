package fun.felipe.controllers;

import fun.felipe.commands.CommandBase;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandController {
    private final List<CommandBase> registeredCommands;

    public CommandController() {
        this.registeredCommands = new ArrayList<>();
    }

    public void addCommand(CommandBase command) {
        registeredCommands.add(command);
    }

    public void commandProcess(String commandName, Socket sockets, String[] args) {
        for (CommandBase command : registeredCommands) {
            if (command.getCommandName().equals(commandName)) {
                command.execute(sockets, args);
                return;
            }
        }

        System.out.println("O comando " + commandName + " não foi Encontrado!");
    }
}
