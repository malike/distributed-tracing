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

`curl http://localhost:8080/api/dummy/1` would generate trace for dummy service
 Unfortunately since this is not a communication between microservices you'll only see one
 span per trace.

 If you look at the codes you'll notice there's this funny part of it where I make
 another rest call to this api `http://localhost:8080/api/another/dummy/3`. This is to simulate
 a trace having two spans with the first one being `http://localhost:8080/api/dummy/1`
 and the second `http://localhost:8080/api/another/dummy/3`

![One Trace, One Microservice,Two Spans](distributed_tracing_1.png "distributed-trace-zipkin")

 Zipkin would be running on this link [http://localhost:9411/](http://localhost:9411/)



##### ELK Configuration


