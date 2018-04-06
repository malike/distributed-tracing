# distributed-tracing

Sample distributed tracing project that uses Opencensus.

This project shows how to export trace data to Zipkin and Elasticsearch.
It is based on the _'helloword'_  example from
[Opencensus repo](https://github.com/census-instrumentation/opencensus-java/tree/master/examples/src/main/java/io/opencensus/examples/helloworld).


## Note

### Zipkin Configuration

    <dependency>
          <groupId>io.opencensus</groupId>
          <artifactId>opencensus-exporter-trace-zipkin</artifactId>
          <version>0.12.2</version>
        </dependency>


With a simple configuration like :

    ZipkinTraceExporter.createAndRegister(ZIPKIN_URL,MICROSERVICE);

Traces can be sent to Zipkin. The current project supports Zipkin V2 API.

Read more on using Opencensus with Zipkin [here](https://github.com/census-instrumentation/opencensus-java/tree/master/exporters/trace/zipkin)

### Discovering Trace on Zipkin

![Trace Opencensus to Zipkin](distributed_tracing_opencensus.png "distributed-trace-zipkin")

### ELK Configuration

To use Opencensus to export to Elasticsearch requires

      <dependency>
            <groupId>io.opencensus</groupId>
            <artifactId>opencensus-exporter-trace-elasticsearch</artifactId>
            <version>${opencensus.elasticsearch.exporter.version}</version>
          </dependency>

This is an Elasticsearch exporter I wrote for Opencensus. Specify your Elasticsearch configuration

    ElasticsearchConfiguration elasticsearchConfiguration
            = new ElasticsearchConfiguration(null, null,ELASTIC_SEARCH_URL,
            INDEX_FOR_TRACE,TYPE_FOR_TRACE);
    ElasticsearchTraceExporter.createAndRegister(elasticsearchConfiguration);

 ..and Trace


#### Discovering trace on kibana

To visualize test trace data in Elasticsearch with Kibana, I've created a sample dashboard as well as a screenshot
to see all trace event from Kibana dsicovery page.

