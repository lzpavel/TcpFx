package com.pfl.tcpfx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;

public class TcpFxApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TcpFxApplication.class.getResource("tcp_fx_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 950, 700);

        stage.setTitle("TcpFx");
        stage.setScene(scene);

        /*stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //controller.handleWindowShown();
                System.out.println("Window shown");

            }
        });*/
        //TextArea textArea = (TextArea)scene.lookup("#textAreaRx");
        //textArea.appendText("Added text");


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}