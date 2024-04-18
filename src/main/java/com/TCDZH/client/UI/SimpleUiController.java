package com.TCDZH.client.UI;

import com.TCDZH.client.service.ClientService;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleUiController {

    private final HostServices hostServices;

    @Autowired
    ClientService clientService;

    @FXML
    public Label label;

    @FXML
    public Button button;


    public SimpleUiController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public void initialize () {
        this.button.setOnAction(actionEvent -> this.clientService.initialCallServer());//Sets the action for pressing the button
    }

    @FXML
    public void setButtonText(String text,String col){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(text);
                label.setTextFill(Color.valueOf(col));

            }
        });

    }
}
