package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class ClientHandler {


    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;
    private String str;

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

                            str = in.readUTF();

                                    System.out.println("Client " + str);


                                if (str.equals("/start")) {
                                    out.writeUTF("/ServerConnected");
                                } else if (str.equals("/kolvok")) {
                                    out.writeUTF("Количество клиентов на связи : " + server.clients.size());
                                } else if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");

                                    break;
                                } else

                                    server.broadcastMsg(str);


                        }

                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.out.println("Нет связи с клиентом");

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
        out.close();
        out.close();
    } catch (IOException e) {


        System.out.println("Нет связи с клиентом");

    } finally {
        server.deleteClient(ClientHandler.this);
    }


    Thread.interrupted();

}
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
            CloseClient();
        }
    }


}
