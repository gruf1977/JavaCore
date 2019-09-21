package com.company;

import com.sun.corba.se.spi.activation.Server;

import javax.xml.soap.SOAPConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerFirst {

    public static void main(String[] args) {
        ServerSocket server = null; // создаем объект серверсокет и инициализируем с чистого листа
        Socket socket = null; // создаем объект сокет и инициализируем ка


        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен.");
            socket = server.accept(); // сервер ждет подключение клиента
            System.out.println("Клиент подключился.");
            Scanner sc = new Scanner(socket.getInputStream()); //- реализуем новый сканер для считывания данных из сокета
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // принтврайтер то что формирует отправку сообщений в сокет.
            //PrintWriter  - на каливает данные пока н скажем ему что, конец данных и можно отправить
            while (true){
                String str=sc.nextLine(); // записываем каждую новую строку
                System.out.println("Client : " + str);
                if (str.equals("/end")) break; // - если приходит /end то разрываем и закрываем
              //  out.flush(); - если не используем в инициализации true
                out.println("Эхо : " + str); // отправка от сервера клиенту
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // при завершении закрываем сокет
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();  //закрываем сервер
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
