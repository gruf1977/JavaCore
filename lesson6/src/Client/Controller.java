package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


public class Controller implements Initializable {

boolean isconserv = true;
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    Button btnexit;

    @FXML
    Button btnsvernut;

    Socket socket; //инициализируем сокет
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRES = "localhost";
    final int PORT = 50105;
void vnutrinit(){

    try {
        socket = new Socket(IP_ADRES, PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!socket.isClosed()) {
                            String str = in.readUTF();
//                            Pattern p = Pattern.compile("^coneri###\\d*");
//                            Matcher m = p.matcher(str);
//                            //System.out.println(m.matches());
//                            if (m.matches()){
//                               // System.out.println(m.matches());
//                                out.writeUTF(str);
//                            } else {
                                textArea.appendText(str + "\n");
                          //  }
                            if (str.equals("/serverClosed")) {
                                clientdisconect();
                            }

                        } else break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    clientdisconect();
                }
            }
        }).start();


    } catch (IOException e) {
        isconserv =false;
        textArea.appendText("Нет связи с сервером! \n");
    }
}
    public void SendMsg() {
if (textField.getText().equals("/end")){
    textArea.appendText("/end \n");
}
        if (textField.getText().equals("/start")){

            vnutrinit();

        }
        if (isconserv) {
            try {

                    out.writeUTF(textField.getText());

                textField.clear();
                textField.requestFocus();

            } catch (IOException e) {
                textArea.appendText("Нет связи (/start - для запуска) \n");
                //e.printStackTrace();
            }
           // JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
        } else textArea.appendText("Нет связи \n");
    }



    public void  sverprogram(){


            Stage stage = (Stage) btnsvernut.getScene().getWindow();
            stage.toBack();

        }


    public void closeprogram(){

    try {

        if (isconserv) {
            if (!socket.isClosed()) {
                out.writeUTF("/end");
            }
    }
    } catch (IOException e) {
         e.printStackTrace();
    } finally {
        Stage stage = (Stage) btnexit.getScene().getWindow();

        stage.close();
    }






    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vnutrinit();
    }

    public void clientdisconect(){
                        try

    {
        in.close();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }
                        try

    {
        out.close();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }
                        try

    {


        socket.close();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }
}

    }

