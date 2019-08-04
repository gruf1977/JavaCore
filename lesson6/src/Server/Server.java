package Server;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Vector;

public class Server {
    public static  Vector<ClientHandler> clients;

    public  void deleteClient(ClientHandler o) {

            clients.remove(o);
        System.out.println("Клиент удален");
        }


    public Server() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;




        try {
            server = new ServerSocket(50105);
            System.out.println("Сервер запущен!");


        while (true) {

            socket = server.accept();
            clients.add(new ClientHandler(this, socket));



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
        }
    }

    public void broadcastMsg(String msg) {

        for (ClientHandler o: clients) {

            o.sendMsg(msg);
        }

    }


}
