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
    private boolean coneckterr;
    private String coneri;
    public ClientHandler(Server server, Socket socket) {
        coneri = "coneri###" + socket.hashCode();
        coneckterr = false;

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
                            if (!socket.isClosed()) {
                            str = in.readUTF();

//                                if (str.equals(coneri)) {
//                                    coneckterr = true;
//                                } else {

                                    System.out.println("Client " + str);
                               // }

                                if (str.equals("/start")) {
                                    out.writeUTF("/ServerConnected");
                                }
                                if (str.equals("/kolvok")) {
                                    out.writeUTF("Количество клиентов на связи : " + server.clients.size());
                                }
                                if (str.equals("/coner")) {
                                    out.writeUTF("coner : " + coneri);
                                }

                                if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");
                                    CloseClient();
                                    server.deleteClient(ClientHandler.this);
                                    // System.out.println(" : " + server.clients.size());
                                    break;
                                } else
                                    server.broadcastMsg(str);

                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        CloseClient();
                    }
                }
            }).start();

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (true) {
//
//
//if (!socket.isClosed()) {
//    out.writeUTF(coneri);
//} else {server.deleteClient(ClientHandler.this);
//    CloseClient();
//}
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//if (!coneckterr){
//    server.deleteClient(ClientHandler.this);
//    CloseClient();
//}
//                            coneckterr = false;
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        CloseClient();
//                    }
//                }
//            }).start();

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
