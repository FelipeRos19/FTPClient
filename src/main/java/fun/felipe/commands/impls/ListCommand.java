package fun.felipe.commands.impls;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.commands.CommandBase;

import java.io.*;
import java.net.Socket;

public class ListCommand extends CommandBase {

    public ListCommand() {
        super("list");
    }

    @Override
    public void execute(Socket socket, String[] args) {
        System.out.println("Executando o comando List!");

        JsonObject request = new JsonObject();
        request.addProperty("command", "list");

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            output.write(request.toString());
            output.newLine();
            output.flush();

            String response = input.readLine();
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            JsonArray files = jsonResponse.get("files").getAsJsonArray();
            for (JsonElement file : files) {
                String fileName = file.getAsJsonObject().get("name").getAsString();
                System.out.println(" - " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
