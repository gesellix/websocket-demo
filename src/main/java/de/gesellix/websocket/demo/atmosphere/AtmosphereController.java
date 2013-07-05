package de.gesellix.websocket.demo.atmosphere;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collection;

import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.LONG_POLLING;

@Controller
@RequestMapping
public class AtmosphereController {

  private final static Logger log = LoggerFactory.getLogger(AtmosphereController.class);

  @ResponseBody
  @RequestMapping(value = "/atmosphere", method = RequestMethod.GET)
  public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
    AtmosphereRequest atmosphereRequest = atmosphereResource.getRequest();
    //uuid = (String) atmosphereRequest.getAttribute(SUSPENDED_ATMOSPHERE_RESOURCE_UUID);

    log.info("negotiating: " + atmosphereRequest.getHeader("negotiating"));
    if (atmosphereRequest.getHeader("negotiating") == null) {
      log.info("transport: " + atmosphereResource.transport());
      atmosphereResource.resumeOnBroadcast(atmosphereResource.transport() == LONG_POLLING).suspend();

      logResponseHeaders(atmosphereResource);
    }
    else {
      AtmosphereResponse response = atmosphereResource.getResponse();
      response.getWriter().write("OK");
      logResponseHeaders(atmosphereResource);
    }
  }

  private void logResponseHeaders(AtmosphereResource atmosphereResource) {
    AtmosphereResponse response = atmosphereResource.getResponse();
    Collection<String> headerNames = response.getHeaderNames();
    log.info("response headers:");
    for (String headerName : headerNames) {
      log.info("- {}: {}", headerName, response.getHeader(headerName));
    }
  }

/*
  @ResponseBody
  @RequestMapping(value = "/atmosphere", method = RequestMethod.POST)
  public void post(AtmosphereResource atmosphereResource) throws IOException {
//    AtmosphereRequest atmosphereRequest = atmosphereResource.getRequest();
//    String body = atmosphereRequest.getReader().readLine().trim();
//    String author = body.substring(body.indexOf(":") + 2, body.indexOf(",") - 1);
//    String message = body.substring(body.lastIndexOf(":") + 2, body.length() - 2);
//    atmosphereResource.getBroadcaster().broadcast("\"text\":\"" + body + "\"");

    String testdata = IOUtils.toString(getClass().getResource("testdata-initial.json"));
    atmosphereResource.getBroadcaster().broadcast(testdata);
  }
*/
}
