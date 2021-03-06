import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author magnus
 *
 */

public class Server {
    public static void main(String[] args){
        int port = 1234;
        boolean run = true;
        ServerSocket serverSocket;
        Socket socket;
        System.out.println("Server started.");

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for connections!");
                socket = serverSocket.accept();
                // Go
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Client connected!");

                //Protocol
                String name = in.readLine();
                if (name.equals("Shutdown")) {
                    out.println("SERVER: Oh no, you terminated me...");
                    in.close();
                    out.close();
                    socket.close();
                    System.out.println("server shutting down...");
                    System.exit(0);
                } else if (name.equals("Spotify")) {
                    Runtime runTime = Runtime.getRuntime();
                    String executablePath = "spotify";
                    Process process = runTime.exec(executablePath);
                    out.println("Vad ska jag lyssna på då?");
                } else {
                    while (run) {
                        System.out.println("Client name is \"" + name + "\"");
                        System.out.println("Sending feedback");
                        out.println("SERVER: Welcome " + name + "! Keep up the good work");
                        name = in.readLine();
                    }
                }
                in.close();
                out.close();
                socket.close();

                System.out.println("Closing down " + name);
            }
        } catch (Exception e) {
            System.out.println("Server fail");
        }
    }
}