package fun.felipe;

import fun.felipe.commands.impls.ExitCommand;
import fun.felipe.commands.impls.GetCommand;
import fun.felipe.commands.impls.HelpCommand;
import fun.felipe.commands.impls.ListCommand;
import fun.felipe.controllers.CommandController;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Client instance;
    private String address;
    private int port;
    private final CommandController commandController;

    public Client() {
        this.commandController = new CommandController();
        this.register();
    }

    public static void main(String[] args) {
        instance = new Client();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o Endereço de Conexão: ");
        instance.address = scanner.nextLine();
        System.out.println("Digite a Porta de Conexão: ");
        instance.port = Integer.parseInt(scanner.nextLine());

        System.out.println("O terminal está preparado para receber Comandos!");

        while (true) {
            String[] input = scanner.nextLine().split(" ");
            if (input.length != 0) {
                String command = input[0];
                String[] commandArgs = new String[input.length - 1];
                System.arraycopy(input, 1, commandArgs, 0, commandArgs.length);

                try (Socket socket = new Socket(instance.getAddress(), instance.getPort())) {
                    instance.getCommandController().commandProcess(command, socket, commandArgs);
                } catch (IOException exception) {
                    System.out.println("Erro ao estabelecer conexão com o Servidor!");
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    private void register() {
        this.commandController.addCommand(new HelpCommand());
        this.commandController.addCommand(new ExitCommand());
        this.commandController.addCommand(new ListCommand());
        this.commandController.addCommand(new GetCommand());
    }

    public static Client getInstance() {
        return instance;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public CommandController getCommandController() {
        return commandController;
    }
}
