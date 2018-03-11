package st.malike.distributed.tracing.sleuth.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

/**
 * malike_st
 */
@SpringBootApplication
@EnableAutoConfiguration
public class SleuthSampleApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SleuthSampleApp.class, args);
    }

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }
}
