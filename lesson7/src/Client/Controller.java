package Client;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {
private String logonick="";
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
    @FXML
    HBox but1;
    @FXML
    HBox apperpannel;
    @FXML
    Button btn2;
    @FXML
    TextField loginfield;
    @FXML
    PasswordField passwordfield;

    @FXML
    Label logo;



    private boolean isAuthorised;

    Socket socket; //инициализируем сокет
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRES = "localhost";
    final int PORT = 50105;

public void setAuthorised(boolean isAuthorised){
    this.isAuthorised = isAuthorised;

    if(!isAuthorised){
        apperpannel.setVisible(true);
        apperpannel.setManaged(true);
        but1.setVisible(false);
        but1.setManaged(false);
      // logo.setText("");


} else {
        apperpannel.setVisible(false);
        apperpannel.setManaged(false);
        but1.setVisible(true);
        but1.setManaged(true);

    // logo.setText("MyChat - ");
    }
}

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
                        if (str.startsWith("/authok")){
//
//                            String[] strlogo = str.split(" ");
//
//                            logonick = strlogo[1];
//                            System.out.println(logonick);
                            setAuthorised(true);

                            break;
                        } else {
                            textArea.appendText(str + "\n");
                        }
                        } else break;
                    }
                    while (true) {
                        if (!socket.isClosed()) {
                            String str = in.readUTF();

                                textArea.appendText(str + "\n");

                            if (str.equals("/serverClosed")) {
                                setAuthorised(false);
                                clientdisconect();

                            }

                        } else break;
                    }


                } catch (IOException e) {
                    //e.printStackTrace();
                    textArea.appendText("Связь с сервером потеряна" + "\n");
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
        if (isconserv) {
            try {

                    out.writeUTF(textField.getText());

                textField.clear();
                textField.requestFocus();

            } catch (IOException e) {
                textArea.appendText("Нет связи (/start - для запуска) \n");

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
            if (isAuthorised) {


                        out.writeUTF("/end");

                }

    }
    } catch (IOException e) {
        // e.printStackTrace();
    } finally {
        Stage stage = (Stage) btnexit.getScene().getWindow();

        stage.close();
    }

    }


    public void connect() {
        vnutrinit();
    }

    public void clientdisconect(){
    try

    {
        in.close();
        out.close();
        socket.close();
    } catch(IOException e)

    {
       // e.printStackTrace();
        textArea.appendText("Нет связи с сервером! \n");
    }

}

    public void tryToAuth() {
if (socket == null || socket.isClosed()) {
connect();
}
        try {
            if (isconserv) {
                out.writeUTF("/auth " + loginfield.getText() + " " + passwordfield.getText());
            }
        loginfield.clear();
        passwordfield.clear();
        } catch (IOException e) {
            //e.printStackTrace();
            textArea.appendText("Нет связи с сервером! \n");
        }

    }
}

