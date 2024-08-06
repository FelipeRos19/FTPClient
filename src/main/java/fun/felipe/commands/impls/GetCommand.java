package fun.felipe.commands.impls;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.commands.CommandBase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetCommand extends CommandBase {

    public GetCommand() {
        super("get");
    }

    @Override
    public void execute(Socket socket, String[] args) {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            if (args.length == 0) {
                System.out.println("Utilize: get <Nome do Arquivo>.");
                return;
            }

            JsonObject request = new JsonObject();
            request.addProperty("command", "get");
            request.addProperty("file", args[0]);

            System.out.println(request.toString());
            output.writeUTF(request.toString());

            String response = input.readUTF();
            System.out.println(response);
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            String fileHash = jsonResponse.get("hash").getAsString();
            System.out.println("Hash do Arquivo: " + fileHash);

            Path outputPath = Paths.get("./");

            try (OutputStream fileOutput = Files.newOutputStream(outputPath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    fileOutput.write(buffer, 0, bytesRead);
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
