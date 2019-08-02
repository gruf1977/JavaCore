package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    Socket socket; //инициализируем сокет
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRES = "localhost";
    final int PORT = 50105;

    public void SendMsg() {
        if (socket.isConnected()) {
            try {

                out.writeUTF(textField.getText());

                textField.clear();
                textField.requestFocus();

            } catch (IOException e) {
                textArea.appendText("Нет связи : Перезапустите программу \n");
                //e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
        } else textArea.appendText("Нет связи : Перезапустите программу \n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                                textArea.appendText(str + "\n");
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
            textArea.appendText("Нет связи с сервером! \n");
        }
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

