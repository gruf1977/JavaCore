package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import static Server.Server.clients;


public class ClientHandler {


    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;
    private String str;

    public String getNick() {
        return nick;
    }

    private String nick;

    private boolean isInChat(String str){
        Boolean res = false;
        for (ClientHandler o: clients) {

            if (o.nick.equals(str)){

                res = true;
                break;
            }
        }
    return res;
    }


    public ClientHandler(Server server, Socket socket){

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
if (str.startsWith("/auth")) {
    String[] tokens = str.split(" ");
    String newNick = null;
    try {
        newNick = AuthService.getNickByLoginByPasswd(tokens[1], tokens[2]);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (isInChat(newNick)) {
        newNick= null;
    }

    if (newNick != null) {

        nick = newNick;

        sendMsg("/authok " + nick);

        server.subscribe(ClientHandler.this);
        sendMsg("Welcome " + nick);
        System.out.println("Клиент " + nick + " подключился");
        break;
    } else {
        sendMsg("Неверный логин/пароль");
    }

}
                        }





                        while (true) {

                            str = in.readUTF();

                                    System.out.println("Client " + str);


                               if (str.equals("/kolvok")) {
                                    out.writeUTF("Количество клиентов на связи : " + clients.size());
                                } else if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");

                                    break;// /w nick3 Привет
                                } else if (str.startsWith("/w")) {
                                    out.writeUTF(str);
                                    String[] strmes = str.split(" ", 3);
                                    if (isInChat(strmes[1])) {
                                        // отправляем сообщение strmes[2] клиенту с ником strmes[1]
                                        server.sendMsgNick(strmes[1], nick + " (personal) : " + strmes[2]+ "\n");
                                    } else {
                                        out.writeUTF("Err " + strmes[1] + " не подключен" );
                                    }


                                }  else

                                    server.broadcastMsg(nick + ": " + str);


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
