package de.gesellix.websocket.demo.atmosphere;

import org.apache.commons.io.IOUtils;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping
public class EntriesController {

  @ResponseBody
  @RequestMapping(value = "/entries", method = RequestMethod.GET)
  public String getEntries() throws IOException {
    return IOUtils.toString(getClass().getResource("testdata-initial.json"));
  }

  @ResponseBody
  @RequestMapping(value = "/entries/triggerAtmospherePush/{uuid}", method = RequestMethod.GET)
  public void triggerAtmospherePush(@PathVariable(value = "uuid") String uuid,
                                    @RequestParam("message") String message) throws IOException {
    String testdata = IOUtils.toString(getClass().getResource("testdata-partial.json"));
    testdata = testdata.replace("@@MESSAGE@@", message);

    AtmosphereResource resource = AtmosphereResourceFactory.getDefault().find(uuid);
    resource.getBroadcaster().broadcast(testdata, resource);

//    Future<List<Broadcaster>> future = MetaBroadcaster.getDefault().broadcastTo(uuid, testdata);
//    BroadcasterFactory.getDefault().lookup(uuid, true).broadcast(testdata);
  }
}
