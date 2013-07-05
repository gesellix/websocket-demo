package de.gesellix.websocket.demo.pubnub;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static de.gesellix.websocket.demo.pubnub.PubnubCredentials.PUBLISH_KEY;
import static de.gesellix.websocket.demo.pubnub.PubnubCredentials.SUBSCRIBE_KEY;
import static org.fest.assertions.Assertions.assertThat;

@Test
public class PubnubLearningTest {

  private static Logger log = LoggerFactory.getLogger(PubnubLearningTest.class);

  private Pubnub pubnub;
  private Callback callback;

  @BeforeClass
  public void setUpClass() {
//    pubnub = new Pubnub(publish_key, subscribe_key, secret_key, cipher_key, SSL);
    pubnub = new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY, false);

    callback = new Callback() {

      @Override
      public void connectCallback(String channel, Object message) {
        log.info("CONNECT on channel " + channel + ": " + message.getClass() + " : " + message.toString());
      }

      @Override
      public void disconnectCallback(String channel, Object message) {
        log.info("DISCONNECT on channel " + channel + ": " + message.getClass() + " : " + message.toString());
      }

      @Override
      public void reconnectCallback(String channel, Object message) {
        log.info("RECONNECT on channel " + channel + ": " + message.getClass() + " : " + message.toString());
      }

      @Override
      public void successCallback(String channel, Object message) {
        log.info("SUCCESS on channel " + channel + ": " + message.getClass() + " : " + message.toString());
      }

      @Override
      public void errorCallback(String channel, PubnubError error) {
        log.info("ERROR on channel " + channel + ": " + error.toString());
      }
    };
  }

  @AfterMethod
  public void tearDown() throws InterruptedException {
    Thread.sleep(1000);
  }

  @Test
  public void testSubscribe() throws Exception {
    pubnub.subscribe("test", callback);
    String subscriptions = PubnubUtil.joinString(pubnub.getSubscribedChannelsArray(), " : ");
    assertThat(subscriptions).isEqualTo("test");
  }

  @Test(dependsOnMethods = "testSubscribe")
  public void testPublish() throws Exception {
    pubnub.publish("test", "message", callback);
  }
}
