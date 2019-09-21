package Server;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.sql.SQLException;
import java.util.Vector;

public class Server {
    public static  Vector<ClientHandler> clients;

    public void subscribe(ClientHandler client) {
        // Сделать проверку поторно подключения клиента
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
    public  void deleteClient(ClientHandler o) {

            clients.remove(o);
        System.out.println("Клиент удален");
        }


    public Server() throws SQLException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;




        try {
            AuthService.connect();
//            String test = AuthService.getNickByLoginByPasswd("login2", "pass2");
//            System.out.println(test);
            server = new ServerSocket(50105);
            System.out.println("Сервер запущен!");


        while (true) {

            socket = server.accept();
            new ClientHandler(this, socket);



        }





        } catch (IOException e) {
            e.printStackTrace();
        } finally {


            try {

            socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }

    }

    public void broadcastMsg(String msg) {

        for (ClientHandler o: clients) {

            o.sendMsg(msg);
        }

    }
    public void sendMsgNick(String namenick, String msg) {

        for (ClientHandler o: clients) {
          if (o.getNick().equals(namenick))
            o.sendMsg(msg);


        }

    }


}
