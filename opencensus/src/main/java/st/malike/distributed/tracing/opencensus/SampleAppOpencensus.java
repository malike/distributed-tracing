package st.malike.distributed.tracing.opencensus;

import io.opencensus.exporter.trace.elasticsearch.ElasticsearchTraceConfiguration;
import io.opencensus.exporter.trace.elasticsearch.ElasticsearchTraceExporter;
import io.opencensus.exporter.trace.logging.LoggingTraceExporter;
import io.opencensus.exporter.trace.zipkin.ZipkinTraceExporter;
import io.opencensus.trace.Sampler;
import io.opencensus.trace.Span;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.samplers.Samplers;
import java.util.logging.Logger;

/**
 * malike_st
 */
public class SampleAppOpencensus {

  private static final Logger logger = Logger.getLogger(SampleAppOpencensus.class.getName());

  private final static String ZIPKIN_URL= "http://localhost:9411/api/v2/spans";
  private final static String ELASTIC_SEARCH_URL= "http://localhost:9200";
  private final static String INDEX_FOR_TRACE= "opencensus";
  private final static String TYPE_FOR_TRACE= "trace";
  private final static String MICROSERVICE= "sample-opencensus";

  // Per class Tracer.
  private static final Tracer tracer = Tracing.getTracer();
  private static final Sampler sampler = Samplers.probabilitySampler(1.0);


  private static void doWork() throws Exception{
    Span rootSpan = tracer.spanBuilderWithExplicitParent("RootSpanDummyService", null).setSampler(sampler).startSpan();
    rootSpan.addAnnotation("Annotation to the root Span before child is created.");
    Span childSpan = tracer.spanBuilderWithExplicitParent("MyChildSpanDummyService", rootSpan).setSampler(sampler).startSpan();
    childSpan.addAnnotation("Annotation to the child Span");
    childSpan.end();
    rootSpan.addAnnotation("Annotation to the root Span after child is ended.");
    rootSpan.end();
    Thread.sleep(500);
  }

  /**
   * Main method.
   *
   * @param args the main arguments.
   */
  public static void main(String[] args) throws Exception{
    //exports to zipkin
    ZipkinTraceExporter.createAndRegister(ZIPKIN_URL,MICROSERVICE);

    //exports to elasticsearch
    ElasticsearchTraceConfiguration elasticsearchTraceConfiguration
        = ElasticsearchTraceConfiguration.builder().setAppName(MICROSERVICE)
        .setElasticsearchUrl(ELASTIC_SEARCH_URL)
        .setElasticsearchIndex(INDEX_FOR_TRACE).setElasticsearchType(TYPE_FOR_TRACE).build();
    ElasticsearchTraceExporter.createAndRegister(elasticsearchTraceConfiguration);

    //exports to logs
    LoggingTraceExporter.register();
    int i =0;
    while(i < 200) { //generate enough data to visualize
      doWork();
      i++;
    }

  }



}
