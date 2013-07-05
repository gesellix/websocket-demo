package de.gesellix.websocket.demo.pubnub;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Test
public class PubnubSubscriptionTest {

  @AfterMethod
  public void tearDown() throws Exception {
    Thread.sleep(1000);
  }

  @Test
  public void test() throws Exception {
    new PubnubSubscriber("test").subscribe();
  }
}
