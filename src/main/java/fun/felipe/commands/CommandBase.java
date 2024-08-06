package fun.felipe.commands;

import java.net.Socket;

public abstract class CommandBase {
    private final String commandName;

    public CommandBase(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public abstract void execute(Socket socket, String[] args);
}
