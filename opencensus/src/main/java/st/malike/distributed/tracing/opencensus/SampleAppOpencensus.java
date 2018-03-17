package st.malike.distributed.tracing.opencensus;

import io.opencensus.exporter.trace.elasticsearch.ElasticsearchConfiguration;
import io.opencensus.exporter.trace.elasticsearch.ElasticsearchTraceExporter;

/**
 * malike_st
 */
public class SampleAppOpencensus {

  private final static String ELASTIC_SEARCH_URL= "http://localhost:9200";
  private final static String INDEX_FOR_TRACE= "opencensus";
  private final static String TYPE_FOR_TRACE= "trace";


  public static void main(String[] args) throws Exception {

    ElasticsearchConfiguration elasticsearchConfiguration
        = new ElasticsearchConfiguration(null, null,ELASTIC_SEARCH_URL,
        INDEX_FOR_TRACE,TYPE_FOR_TRACE);
    ElasticsearchTraceExporter.createAndRegister(elasticsearchConfiguration);

  }
}
