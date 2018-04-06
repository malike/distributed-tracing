package st.malike.distributed.tracing.opencensus;

import io.opencensus.exporter.trace.elasticsearch.ElasticsearchConfiguration;
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
    Thread.sleep(5000);
  }

  /**
   * Main method.
   *
   * @param args the main arguments.
   */
  public static void main(String[] args) throws Exception{
    ZipkinTraceExporter.createAndRegister(ZIPKIN_URL,MICROSERVICE);
    ElasticsearchConfiguration elasticsearchConfiguration
        = new ElasticsearchConfiguration(null, null,ELASTIC_SEARCH_URL,
        INDEX_FOR_TRACE,TYPE_FOR_TRACE);
    ElasticsearchTraceExporter.createAndRegister(elasticsearchConfiguration);
    LoggingTraceExporter.register();
    doWork();

  }



}
