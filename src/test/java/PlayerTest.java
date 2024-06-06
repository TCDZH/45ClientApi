import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.TCDZH.api.client.domain.NewHand;
import com.TCDZH.api.client.domain.ServerCard;
import com.TCDZH.api.client.domain.SuitEnum;
import com.TCDZH.client.models.Player;
import org.junit.jupiter.api.Test;

public class PlayerTest {

  Player testPlayer = new Player();


  @Test
  void testNewHand(){
    ServerCard card1 = new ServerCard();
    ServerCard card2 = new ServerCard();
    card1.setNumber(10);
    card2.setNumber(8);
    card1.setPower(12);
    card2.setPower(21);
    card1.setSuit(SuitEnum.HEART);
    card2.setSuit(SuitEnum.DIAMOND);
    NewHand newHand = new NewHand();
    newHand.add(card1);
    newHand.add(card2);

    testPlayer.setNewHand(newHand);

    assertEquals(2, testPlayer.getHand().size());

  }

}
