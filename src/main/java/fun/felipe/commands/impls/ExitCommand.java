package fun.felipe.commands.impls;

import fun.felipe.commands.CommandBase;

import java.io.IOException;
import java.net.Socket;

public class ExitCommand extends CommandBase {

    public ExitCommand() {
        super("exit");
    }

    @Override
    public void execute(Socket socket, String[] args) {
        try {
            System.out.println("Desligando o Cliente!");
            socket.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        System.exit(0);
    }
}
