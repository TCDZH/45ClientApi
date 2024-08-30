import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.TCDZH.client.UI.StartScreenController;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@WireMockTest
@ExtendWith(MockitoExtension.class)
public class StartScreenControllerTest {

  private WireMock wireMock;

  @InjectMocks
  private StartScreenController startScreenController;

  @Mock
  private TextField GameIdText;

  @BeforeEach
  public void WiremockSetup(WireMockRuntimeInfo wireMockRuntimeInfo){
    wireMock = wireMockRuntimeInfo.getWireMock();
  }


  //TODO: Not sure how to test alot of these, might just have to be a manual jobbie
//  @Test
//  void testInvalidGameId() throws IOException {
//    when(GameIdText.getText()).thenReturn("123");
//    ActionEvent actionEvent = mock(ActionEvent.class);
//    startScreenController.joinGame(actionEvent);
//    wireMock.register(WireMock.post("/join-game/123")
//        .withHeader("port",equalTo("123"))
//        .willReturn(badRequest()));
//
//  }

}
