package com.TCDZH.client.UI;


import com.TCDZH.client.service.ClientService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StartScreenController {

  ClientService service;

  final private ApplicationContext context;

  @FXML
  public TextField GameSizeText;

  @FXML
  public TextField GameIdText;

  @Autowired
  public StartScreenController(ClientService service, ApplicationContext context){
    this.service = service;
    this.context = context;
  }

  private boolean checkIfValidGameSize(String gameSize){
    boolean valid = true;
    try{
      int size = Integer.parseInt(gameSize);
      if (size > 6 || size < 2){
        valid = false;
      }
    }catch (NumberFormatException e){
      valid = false;
    }
    return valid;
  }

  public void createGame(ActionEvent event) throws IOException {
    //Triggered from a button press, move to waiting screen (all waiting screen has gameId)
    String gameSize = GameSizeText.getText();
    if (!checkIfValidGameSize(gameSize)){
      GameSizeText.clear();
      GameSizeText.setPromptText("Ensure game size is num between 2 and 6");
      return;
    }
    service.setMaxGameSize(gameSize);
    service.createGame(gameSize);
    switchToWait(event, true);
  }

  //TODO: thinking of changing
  @FXML
  public void switchToWait(ActionEvent event, boolean createGame) throws IOException {
    if (createGame){

      String gameSize = GameSizeText.getText();
      service.setMaxGameSize(gameSize);
    }

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("templates/WaitingScreen.fxml"));
    fxmlLoader.setControllerFactory(context::getBean);
    Parent root = fxmlLoader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root, 640, 400);
    stage.setScene(scene);
    stage.show();
  }

  public void joinGame(ActionEvent event) throws IOException {
    String gameId = GameIdText.getText();
    service.joinGame(gameId);

    System.out.println("Should not get here ");
    switchToWait(event, false);
    //Might have to add a separate endpoint that is called when a new player joins the game to indicate how many players have joined
    //This one is triggered by a button press
    //Will need to have a text box that allows the user to enter the game Id
    //Transition to waiting screen on success, if not stay on opening screen
  }

  public void showGameIdNotValid(){
    GameIdText.clear();
    GameIdText.setPromptText("GameID not found or invalid");
  }


}
