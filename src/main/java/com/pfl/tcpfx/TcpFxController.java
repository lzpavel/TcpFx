package com.pfl.tcpfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TcpFxController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField textFieldIp, textFieldPort, textFieldTx;

    @FXML
    public TextArea textAreaRx;

    @FXML
    private Button buttonTest;
    @FXML
    private Button buttonServer;
    @FXML
    private Button buttonClient;
    @FXML
    private Button buttonStop;
    @FXML
    private Button buttonSend;
    @FXML
    private Button buttonUpdate;
    @FXML
    private VBox vBox;




    /*protected void handleWindowShown() {
        //textAreaRx.appendText("Window shown");

    }*/

    public TcpFxController() {
        System.out.println("Controller constructor");
        TcpClientServer.RxNotification.addListener(new TcpClientServer.RxListener() {
            @Override
            public void onRxMsg() {
                for (String s : TcpClientServer.queueRx) {
                    textAreaRx.appendText(s + "\n");
                }
                TcpClientServer.queueRx.clear();
            }
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onButtonTestClick() {
        //textAreaRx.setText("Hi!");
        //textFieldTx.setText("Hi!");
        //textAreaRx.addEventHandler();


        /*textAreaRx.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {

            }
        });*/
        /*TcpClientServerOld tcp = new TcpClientServerOld();
        tcp.mc = new TcpClientServerOld.MyStatic();
        TcpClientServerOld.mcs = new TcpClientServerOld.MyStatic();
        TcpClientServerOld.MyStatic.a = 5;
        tcp.mc.a = 7;

        TcpClientServerOld tcp2 = new TcpClientServerOld();
        TcpClientServerOld.MyDynamic md =  new TcpClientServerOld().new MyDynamic();*/


    }



    @FXML
    protected void onButtonServerClick() {
        TcpClientServer.startServer(Integer.parseInt(textFieldPort.getText()));
    }

    @FXML
    protected void onButtonClientClick() {
        TcpClientServer.startClient(textFieldIp.getText(), Integer.parseInt(textFieldPort.getText()));
    }

    @FXML
    protected void onButtonStopClick() {
        TcpClientServer.stop();
    }

    @FXML
    protected void onButtonUpdateClick() {
        for (String s : TcpClientServer.queueRx) {
            textAreaRx.appendText(s);
        }
        TcpClientServer.queueRx.clear();
    }

    @FXML
    protected void onButtonSendClick() {
        TcpClientServer.sendMessage(textFieldTx.getText());
    }
}