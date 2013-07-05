package de.gesellix.websocket.demo.pubnub;

import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

import static de.gesellix.websocket.demo.pubnub.PubnubCredentials.PUBLISH_KEY;
import static de.gesellix.websocket.demo.pubnub.PubnubCredentials.SUBSCRIBE_KEY;

public class PubnubSubscriber {

  String channel;
  Pubnub pubnub;
  DefaultPubnubCallback callback = new DefaultPubnubCallback("subscriber");

  public PubnubSubscriber(String channel) {
    this.channel = channel;

    pubnub = new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY, false);
  }

  public void subscribe() throws PubnubException {
    pubnub.subscribe(channel, callback);
  }

  public static void main(String[] args) {
    try {
      PubnubSubscriber pubnubSubscriber = new PubnubSubscriber("test");
      pubnubSubscriber.subscribe();

      Thread.sleep(5000);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
