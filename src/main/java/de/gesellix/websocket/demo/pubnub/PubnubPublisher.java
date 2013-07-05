package de.gesellix.websocket.demo.pubnub;

import com.pubnub.api.Pubnub;

import static de.gesellix.websocket.demo.pubnub.PubnubCredentials.PUBLISH_KEY;
import static de.gesellix.websocket.demo.pubnub.PubnubCredentials.SUBSCRIBE_KEY;

public class PubnubPublisher {

  String channel;
  Pubnub pubnub;
  DefaultPubnubCallback callback = new DefaultPubnubCallback("publisher");

  public PubnubPublisher(String channel) {
    this.channel = channel;

    pubnub = new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY, false);
  }

  public void publish(String message) {
    pubnub.publish(channel, message, callback);
  }

  public static void main(String[] args) {
    try {
      PubnubPublisher pubnubSubscriber = new PubnubPublisher("test");
      String message = createMessage(1795);
      pubnubSubscriber.publish(message);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String createMessage(int messageSize) {
    String message = "";
    for (int i = 0; i < messageSize; i++) {
      message += "a";
    }
    return message;
  }
}
