package de.gesellix.websocket.demo.pubnub;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPubnubCallback extends Callback {

  private static Logger log = LoggerFactory.getLogger(DefaultPubnubCallback.class);

  private String entity;

  public DefaultPubnubCallback(String entity) {
    this.entity = entity;
  }

  @Override
  public void connectCallback(String channel, Object message) {
    log.info(String.format("%s: CONNECT on channel %s: %s : %s", entity, channel, message.getClass(), message.toString()));
  }

  @Override
  public void disconnectCallback(String channel, Object message) {
    log.info(String.format("%s: DISCONNECT on channel %s: %s : %s", entity, channel, message.getClass(), message.toString()));
  }

  @Override
  public void reconnectCallback(String channel, Object message) {
    log.info(String.format("%s: RECONNECT on channel %s: %s : %s", entity, channel, message.getClass(), message.toString()));
  }

  @Override
  public void successCallback(String channel, Object message) {
    log.info(String.format("%s: SUCCESS on channel %s: %s : %s", entity, channel, message.getClass(), message.toString()));
  }

  @Override
  public void errorCallback(String channel, PubnubError error) {
    log.info(String.format("%s: ERROR on channel %s: %s", entity, channel, error.toString()));
  }
}
