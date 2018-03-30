package st.malike.distributed.tracing.sleuth.app.malike.distributed.tracing.sleuth.app.http;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import st.malike.distributed.tracing.sleuth.app.malike.distributed.tracing.sleuth.app.service.DummyService;

/**
 * @autor malike_st
 */
@Controller
public class DummyController {

  private static final Logger logger = Logger.getLogger(DummyController.class.getName());
  @Autowired
  private DummyService dummyService;

  @RequestMapping(value = {
      "/api/dummy/{id}"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String getDummyApi(@PathVariable String id, HttpServletResponse response) {
    logger.info("getDummyApi(\"" + id + "\"");
    if (id.equalsIgnoreCase("3")) {
      return dummyService.getDummy();
    }
    return "{\"dummyApi\":\"" + id + "\"}";
  }


}
