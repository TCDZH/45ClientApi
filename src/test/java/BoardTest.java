import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.TCDZH.api.client.domain.SuitEnum;
import com.TCDZH.client.models.Board;
import com.TCDZH.client.models.Card;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

  //This set of tests is for the card playable function

  private Board board = new Board();

  private Card trumpCard;

  private Card ledCard;

  private Card normalCard;

  private ArrayList<Card> hand = new ArrayList<>();

  @BeforeEach
  void boardSetup(){
    board.setTrump(SuitEnum.CLUB);
    board.setLedCard(SuitEnum.DIAMOND);

    trumpCard = new Card(SuitEnum.CLUB,10);
    ledCard = new Card(SuitEnum.DIAMOND,9);
    normalCard = new Card(SuitEnum.SPADE,8);

    board.setGameTurn(1);

    hand.clear();
  }


  @Test
  void haveTrumpPlayTrump(){


    hand.add(trumpCard);

    assertTrue(board.cardPlayable(trumpCard,hand,1));
  }

  @Test
  void haveTrumpDontPlay(){
    hand.add(trumpCard);

    assertTrue(board.cardPlayable(normalCard,hand,1));
  }

  @Test
  void haveLedPlayTrump(){
    hand.add(ledCard);

    assertTrue(board.cardPlayable(trumpCard,hand,1));

  }

  @Test
  void haveLedPlayNormal(){
    hand.add(ledCard);

    assertFalse(board.cardPlayable(normalCard,hand,1));
  }


}
