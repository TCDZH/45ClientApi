package com.TCDZH.client.UI;

import com.TCDZH.api.client.domain.NewHand;
import com.TCDZH.api.client.domain.SuitEnum;
import com.TCDZH.client.service.ClientService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class WaitScreenController {

  private ClientService service;

  private ApplicationContext context;

  @FXML
  public Label WaitBox;

  @Autowired
  public WaitScreenController(ClientService service) {
    this.service = service;
    //For below get the gameSize from the service, this will be set from the joined game or the start game
    WaitBox.setText(String.format("GameId - %s \n GameSize %s",service.getConnectedGameId(),service.getMaxGameSize()));

  }

  public void startGame(SuitEnum trump, Integer players, NewHand newHand) {
    //Transition to main screen
    service.startGame(players, trump, newHand);

  }

  //TODO: might need to have some changes for
  @FXML
  public void switchToMain(ActionEvent event) throws IOException {
    service.earlyStartGame();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("templates/MainScreen.fxml"));
    fxmlLoader.setControllerFactory(context::getBean);
    Parent root = fxmlLoader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root, 640, 400);
    stage.setScene(scene);
    stage.show();
  }


}
