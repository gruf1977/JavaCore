package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class ClientHandler {

    public boolean getNoSocket() {
        boolean res = false;
        if (socket.isClosed()){
            res = true;
        }

        return res;
    }

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.socket = socket;

            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            System.out.println("Client " + str);

                            if (str.equals("/end")) {
                                out.writeUTF("/serverClosed");
                                CloseClient();

                              Server.deleteClient(this);

                                break;
                            } else
                                server.broadcastMsg(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        CloseClient();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void CloseClient(){
    try {
        in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    try {
        out.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    try {

        socket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    Thread.interrupted();

}
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
