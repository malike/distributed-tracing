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



### Discovering Trace on Zipkin


### ELK Configuration

      <dependency>
            <groupId>io.opencensus</groupId>
            <artifactId>opencensus-exporter-trace-elasticsearch</artifactId>
            <version>${opencensus.elasticsearch.exporter.version}</version>
          </dependency>

#### Discovering trace on kibana

