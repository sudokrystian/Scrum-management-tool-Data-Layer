package ApplicationServer.Model.RegisterSocketProtocol;

import ApplicationServer.JPA.UserRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RegisterSocketServer implements Runnable{
    private int port;
    private UserRepository userRepository;

    public RegisterSocketServer(int port, UserRepository userRepository) {
        this.port = port;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Running socket on port " + port);
            while (true){
                Socket socket = serverSocket.accept();
                Thread clientHandlerThread = new Thread(new RegisterClientHandler(socket, userRepository));
                clientHandlerThread.setDaemon(true);
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
