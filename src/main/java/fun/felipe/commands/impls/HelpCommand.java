package fun.felipe.commands.impls;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.commands.CommandBase;

import java.io.*;
import java.net.Socket;

public class HelpCommand extends CommandBase {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(Socket socket, String[] args) {
        System.out.println("Executando o comando Help!");

        JsonObject request = new JsonObject();
        request.addProperty("command", "help");

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            output.write(request.toString());
            output.newLine();
            output.flush();

            String response = input.readLine();
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            System.out.println("Os comandos do Servidor são: ");
            JsonArray commands = jsonResponse.get("commands").getAsJsonArray();
            for (JsonElement command : commands) {
                String commandName = command.getAsJsonObject().get("name").getAsString();
                String commandDescription = command.getAsJsonObject().get("description").getAsString();
                System.out.println(" - " + commandName + " - " + commandDescription);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
