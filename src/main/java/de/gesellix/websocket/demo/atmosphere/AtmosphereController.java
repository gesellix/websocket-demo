package de.gesellix.websocket.demo.atmosphere;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.LONG_POLLING;

@Controller
@RequestMapping
public class AtmosphereController {

  @ResponseBody
  @RequestMapping(value = "/atmosphere", method = RequestMethod.GET)
  public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
    AtmosphereRequest atmosphereRequest = atmosphereResource.getRequest();
    //uuid = (String) atmosphereRequest.getAttribute(SUSPENDED_ATMOSPHERE_RESOURCE_UUID);

    if (atmosphereRequest.getHeader("negotiating") == null) {
      atmosphereResource.resumeOnBroadcast(atmosphereResource.transport() == LONG_POLLING).suspend();
    }
    else {
      atmosphereResource.getResponse().getWriter().write("OK");
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
