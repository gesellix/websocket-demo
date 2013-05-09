package de.gesellix.websocket.demo.atmosphere;

import org.apache.commons.io.IOUtils;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping
public class AtmosphereController {

  @ResponseBody
  @RequestMapping(value = "/atmosphere", method = RequestMethod.GET)
  public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
    AtmosphereRequest atmosphereRequest = atmosphereResource.getRequest();
    //uuid = (String) atmosphereRequest.getAttribute(SUSPENDED_ATMOSPHERE_RESOURCE_UUID);

    if (atmosphereRequest.getHeader("negotiating") == null) {
      atmosphereResource.resumeOnBroadcast(atmosphereResource.transport() == TRANSPORT.LONG_POLLING).suspend();
    }
    else {
      atmosphereResource.getResponse().getWriter().write("OK");
    }
  }

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

  @ResponseBody
  @RequestMapping(value = "/broadcast/{uuid}", method = RequestMethod.GET)
  public void broadcast(@PathVariable(value = "uuid") String uuid) throws IOException {
    String testdata = IOUtils.toString(getClass().getResource("testdata-partial.json"));

    AtmosphereResource resource = AtmosphereResourceFactory.getDefault().find(uuid);
    resource.getBroadcaster().broadcast(testdata);

//    String broadcasterID = "/*";
//    Future<List<Broadcaster>> deferredBroadcast = MetaBroadcaster.getDefault().broadcastTo(broadcasterID, "ein broadcast");
//    try {
//      List<Broadcaster> broadcasters = deferredBroadcast.get();
//      System.out.println(broadcasters);
//    }
//    catch (Exception e) {
//      throw new RuntimeException(e);
//    }
  }
}
