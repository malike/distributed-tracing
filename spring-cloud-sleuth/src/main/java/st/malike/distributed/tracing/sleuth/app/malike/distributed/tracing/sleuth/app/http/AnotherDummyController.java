package st.malike.distributed.tracing.sleuth.app.malike.distributed.tracing.sleuth.app.http;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @autor malike_st
 */
@Controller
public class AnotherDummyController {

  private static final Logger logger = Logger.getLogger(AnotherDummyController.class.getName());

  @RequestMapping(value = {
      "/api/another/dummy/{id}"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String dummyApiWithDummyService(@PathVariable String id, HttpServletResponse response) {
    logger.info("dummyApiWithDummyService(\"" + id + "\"");
    return "{\"dummyApiWithDummyService \":\"3\"}";
  }


}
