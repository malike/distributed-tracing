# distributed-tracing

Sample distributed tracing project that uses Opencensus.

This project shows how to export trace data to Zipkin and Elasticsearch.


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

