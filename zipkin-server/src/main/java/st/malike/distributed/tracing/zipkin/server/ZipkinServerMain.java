package st.malike.distributed.tracing.zipkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
 * malike_st
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerMain {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(ZipkinServerMain.class, args);
  }


}
