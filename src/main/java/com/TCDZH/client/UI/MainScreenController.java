package com.TCDZH.client.UI;

import com.TCDZH.api.client.domain.NewHand;
import com.TCDZH.api.client.domain.SuitEnum;
import com.TCDZH.client.models.Card;
import com.TCDZH.client.service.ClientService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainScreenController {

  //TODO: Next need to figure out how to switch between starting, waiting and end screen s
  //Then pretty much done??

  private ClientService service;
  private ApplicationContext context;

  @Autowired
  public MainScreenController(ClientService service, ApplicationContext context) {
    this.service = service;
    this.context = context;
  }

  public MainScreenController(){}

  @FXML
  public HBox HandBox;

  @FXML
  public HBox PileBox;

  @FXML
  public Label playableLabel;

  @FXML
  public VBox ScoreBoard;

  @FXML
  public void refreshScoreBoard() {
    HashMap<String, Integer> score = service.getBoard().getScoreBoard();
    for (int i = 0; i < score.size(); i++) {
      Label label = (Label) ScoreBoard.getChildren().get(i);
      label.setText("Player " + (i + 1) + ":" + score.get(Integer.toString(i)));
    }
  }

  @FXML
  public void refreshCardDisplay() {
    ArrayList<Card> hand = service.getPlayer().getHand();
    ArrayList<Card> pile = service.getBoard().getPile();
    Platform.runLater(() -> {
      clearHbox(HandBox);
      refreshScoreBoard();
      cardsInBox(hand, HandBox);
      cardsInBox(pile, PileBox);
    });
  }

  public void cardsInBox(ArrayList<Card> cards, HBox box) {
    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);
      ImageView imageView = (ImageView) box.getChildren().get(i);
      String imageName = getImageNameFromCard(card);
      Image image = new Image(getClass().getResourceAsStream(imageName));
      imageView.setImage(image);
    }
  }

  public void clearHbox(HBox box) {
    for (Node node : box.getChildren()) {
      ImageView imageView = (ImageView) node;
      imageView.setImage(null);
    }
  }

  //TODO: figure out how this translates to playing the right card
  //Working solution is to have 5 different functions that correspond to the 5 different cards
  //this number is passed to the service class which can figure out what card it is from its place in the hand and go from there
  //A working solution but a bit dumb
  public void cardClicked0() {
    playCard(0);
  }

  public void cardClicked1() {
    playCard(1);
  }

  public void cardClicked2() {
    playCard(2);
  }

  public void cardClicked3() {
    playCard(3);
  }

  public void cardClicked4() {
    playCard(4);
  }

  public void tempDisplayBottomLabel(String message) {
    playableLabel.setText(message);
    PauseTransition visiblePause = new PauseTransition(
        Duration.seconds(3));
    visiblePause.setOnFinished(
        event -> playableLabel.setVisible(false));
    visiblePause.play();
    playableLabel.setVisible(true);
  }

  public void playCard(int cardIndex) {
    if (!(service.getPlayer().getHand().size() < cardIndex)) {
      if (!service.playCard(service.getPlayer().getHand().get(cardIndex))) {
        Card card = service.getPlayer().getHand().get(cardIndex);
        tempDisplayBottomLabel("Card " + (card.getNumber() + 1) + " of " +
            card.getSuit().toString() + "s not playable.");
      }
    }
  }

  //functions needed that will be called from controller, then call the relevant service class method
    /*
    Add card - calls the service which will update the board, then refresh the board
    end Game - Changes the screen to the end screen with the scoreboard displayed
    end Hand - Call the service, place indicator next to winning card, refresh the board
    end Round - Call service, refresh board, noticing a pattern here
    join Game - Transition to waiting screen is success, if not stay on opening screen
    start Game - Transition to main screen, refresh display
     */

  public void addCard(Card card) {
    service.receiveCard(card);
    refreshCardDisplay();
  }


  //TODO: need to see if the the last will update before the endgame message is sent, not sure if need winner
  public void endGame(Integer winner) {
    try{
      switchToEnd();
    }catch (IOException e){
      throw new RuntimeException(e.getMessage() + " FXML not found?");
    }
  }

  public void endHand(Integer handWinner) {
    service.endHand(handWinner);
    String winningCardMessage = "Player " + (handWinner + 1) + " has won with the "
        + (service.getBoard().getPile().get(0).getNumber() + 1) + " of " +
        service.getBoard().getPile().get(0).getSuit().toString() + "s ";
    tempDisplayBottomLabel(winningCardMessage);
    refreshCardDisplay();
  }

  public void endRound(SuitEnum trump, NewHand newHand) {
    service.endRound(trump, newHand);
    refreshCardDisplay();
  }


  //End screen can just pull from the scoreboard
  @FXML
  public void switchToEnd() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("templates/EndScreen.fxml"));
    fxmlLoader.setControllerFactory(context::getBean);
    Parent root = fxmlLoader.load();
    //Just gets the current stage from the Handbox, could have used any of the FXML objects
    Stage stage = (Stage)  HandBox.getScene().getWindow();
    Scene scene = new Scene(root, 640, 400);
    stage.setScene(scene);
    stage.show();
  }

  //Jack is 10, Queen is 11, King is 12, Ace is 0
  public String getImageNameFromCard(Card card) {

    String imageName = "/cards/";
    if (card.getNumber() > 0 && card.getNumber() < 10) {
      imageName += (card.getNumber() + 1);
    } else if (card.getNumber() == 0) {
      imageName += "ace";
    } else if (card.getNumber() == 10) {
      imageName += "jack";
    } else if (card.getNumber() == 11) {
      imageName += "queen";
    } else if (card.getNumber() == 12) {
      imageName += "king";
    }
    imageName += "_of_" + card.getSuit().toString().toLowerCase() + "s.png";

    return imageName;
  }
}
