# distributed-tracing

A sample distributed tracing project that uses Spring Cloud Sleuth
that uses Logstash to export trace data to Elasticsearch and can also
be configured to send to Zipkin.

## Note

##### Zipkin Configuration

`spring.application.name=sample-app` add this to your application for
 Zipkin to identify the microservice. With out this it would tag the
 microservices as _"Unknown"_. You can change _"sample-app"_ to the identity
 of your microservice.

 `spring.zipkin.baseUrl=http://localhost:9411` This is responsible for pointing
 the microservice to Zipkin to successfully send trace events.


##### Discovering Trace on Zipkin



