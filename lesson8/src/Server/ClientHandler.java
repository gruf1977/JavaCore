package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import static Server.AuthService.isNickInBd;
import static Server.Server.clients;


public class ClientHandler {


    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;
    private String str;
    private ArrayList blacklist;

    public String getNick() {
        return this.nick;
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
    catch (ArrayIndexOutOfBoundsException e) {
       // e.printStackTrace();
    }
    if (isInChat(newNick)) {
        newNick= null;
    }

    if (newNick != null) {

        nick = newNick;

        sendMsg("/authok " + nick);

        server.subscribe(ClientHandler.this);
        sendMsg("Welcome " + nick);
        sendMsg("/info - справочник команд");
        blacklist = new ArrayList();
        setBlacklist();
        System.out.println("Клиент " + nick + " подключился");

        break;
    } else {
        sendMsg("Err: Неверный логин/пароль");
    }

}
                        }
                     while (true) {

                            str = in.readUTF();

                                    System.out.println("Client " + str);


                         if (str.equals("/info")) {

                             info();


                         } else if (str.equals("/bl")) {

                               readBlackList();


                            } else if (str.startsWith("/delbl")) {
                                String[] strmes = str.split(" ", 2);
                                if (strmes.length<2) {
                                    sendMsg("Err: неверное значение /delbl ");

                                }
                                else {

                                    delBlackList(strmes[1]);


                                }

                            } else if (str.startsWith("/addbl")) {
                                String[] strmes = str.split(" ", 2);
                                if (strmes.length<2) {
                                    sendMsg("Err: неверное значение /addbl ");

                                }
                                else {

                                        addBlackList(strmes[1]);


                                }


                            }
                         else  if (str.equals("/who")) {
                             sendMsg("On-line : ");

                                int f=0;
                             for (ClientHandler o: clients) {
                                 sendMsg(++f + ". " + o.getNick());
                     }


                         } else  if (str.equals("/kolvok")) {
                                    out.writeUTF("Количество клиентов на связи : " + clients.size());



                                } else if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");

                                    break;// /w nick3 Привет
                                } else if (str.startsWith("/w")) {
                                personalMsg(str);
                            }  else

                                    server.broadcastMsg(nick + ": " + str);


                        }

                    } catch (IOException | SQLException e) {
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

    private void info() {
        sendMsg("\nСписок команд: \n");
        sendMsg("/end - звершение сеанса \n");
                sendMsg(" /clear - очистить экран \n");
                        sendMsg("/w nick - личное сообщение nick \n");
                                sendMsg("/bl - черный список \n");
                                        sendMsg("/addbl nick - добавить nick в черный список \n");
                                                sendMsg("/delbl nick - удалить nick из черного списка \n");
                                                        sendMsg("/kolvok - количество онлайн \n");
        sendMsg("/who - кто онлайн \n");
                                                                sendMsg("/info - справочник команд\n");
    }


    //Чтение черного списка
    private void readBlackList(){
        sendMsg("Черный список: " + blacklist.toString());

    }

    /*Удаление из черного списка*/
    private void delBlackList(String blackuser) throws SQLException {
        if (isNickInBd(blackuser)){
if (blacklist.contains(blackuser)) {
    boolean res = AuthService.delBlist(this.nick, blackuser);
    if (res) {
        sendMsg(" " + blackuser + " удален из черного списка");
        setBlacklist();
    } else {
        sendMsg("Err: Запись из черного списка не удалена");
    }
} else {
    sendMsg("Err: В черном списке нет " + blackuser);
    readBlackList();
}

        }else {
            sendMsg("Err: Нет такого пользователя : " + blackuser);
        }

    }

    /*Добавление в черный список*/
    private void addBlackList(String blackuser) throws SQLException {
        if (!this.nick.equals(blackuser)) {
            if (isNickInBd(blackuser)) {
                if (!blacklist.contains(blackuser)) {
                    boolean res = AuthService.addBList(this.nick, blackuser);
                    if (res) {
                        sendMsg("Добавлен в черный список : " + blackuser);
                        setBlacklist();
                    } else {
                        sendMsg("Err: Запись в черный список не добавлена");
                    }
                } else {
                    sendMsg("Err: " + blackuser + " уже в черном списке");
                    readBlackList();
                }
            } else {
                sendMsg("Err: Нет такого пользователя : " + blackuser);
            }
        } else {
            sendMsg("Err: Запись в черный список не добавлена");
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


    public void setBlacklist() throws SQLException {
        this.blacklist = AuthService.readBlackList(nick);

    }

    private void personalMsg(String str){


        String[] strmes = str.split(" ", 3);

        if (strmes.length<3) {
            sendMsg("Err: неверное значение /w ");

        }
        else {
            String usernick = strmes[1];

            if (isInChat(usernick)) {

                try {
                    if (!AuthService.isAutorInBLUser(nick, usernick)){

                        sendMsg(nick + ": (personal for " + usernick + "): " + strmes[2]);
                        // отправляем сообщение strmes[2] клиенту с ником strmes[1]
                        server.sendMsgNick(strmes[1], nick + " (personal) : " + strmes[2] + "\n");

                    } else {
                        sendMsg("Err: Сообщение не отправлено");
                        sendMsg("Err: Вы в черном списке у " + strmes[1]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sendMsg("Err: (" + strmes[1] + ") не подключен.");
            }

        }


    }
}
