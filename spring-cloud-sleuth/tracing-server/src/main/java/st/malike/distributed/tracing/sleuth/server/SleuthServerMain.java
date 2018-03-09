package st.malike.distributed.tracing.sleuth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * malike_st
 */
@SpringBootApplication
@EnableAutoConfiguration
public class SleuthServerMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SleuthServerMain.class, args);
    }


}
