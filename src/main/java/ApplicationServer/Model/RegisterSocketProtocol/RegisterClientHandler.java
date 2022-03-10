package ApplicationServer.Model.RegisterSocketProtocol;

import ApplicationServer.JPA.UserRepository;
import ApplicationServer.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterClientHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private UserRepository userRepository;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public RegisterClientHandler(Socket socket, UserRepository userRepository) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.userRepository = userRepository;
    }


    @Override
    public void run() {
        try {
            String request = in.readLine();
            if (request.equalsIgnoreCase("Register request")){
                out.println("User JSON?");
            } else {
                out.println("Unexpected input, closing connection");
                socket.close();
            }

            request = in.readLine();
            User receivedUser = jsonMapper.readValue(request, User.class);

            if (userRepository.findByUsername(receivedUser.getUsername()) != null)
                out.println("Username already exists");
            else {
                userRepository.save(receivedUser);
                out.println("Account created");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
