package st.malike.distributed.tracing.opencensus;

import io.opencensus.common.Scope;
import io.opencensus.exporter.trace.elasticsearch.exception.InvalidElasticsearchConfigException;
import io.opencensus.exporter.trace.zipkin.ZipkinTraceExporter;
import io.opencensus.stats.Aggregation;
import io.opencensus.stats.BucketBoundaries;
import io.opencensus.stats.Measure.MeasureLong;
import io.opencensus.stats.Stats;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.stats.View;
import io.opencensus.stats.View.AggregationWindow.Cumulative;
import io.opencensus.stats.ViewData;
import io.opencensus.stats.ViewManager;
import io.opencensus.tags.TagContextBuilder;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagValue;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.Tags;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.Status;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.samplers.Samplers;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

/**
 * malike_st
 */
public class SampleAppOpencensus {

  private static final Logger logger = Logger.getLogger(SampleAppOpencensus.class.getName());

  private final static String ELASTIC_SEARCH_URL= "http://localhost:9200";
  private final static String INDEX_FOR_TRACE= "opencensus";
  private final static String TYPE_FOR_TRACE= "trace";
  private final static String MICROSERVICE= "sample-opencensus";



  private static final Tagger tagger = Tags.getTagger();
  private static final ViewManager viewManager = Stats.getViewManager();
  private static final StatsRecorder statsRecorder = Stats.getStatsRecorder();
  private static final Tracer tracer = Tracing.getTracer();

  // frontendKey allows us to break down the recorded data.
  private static final TagKey FRONTEND_KEY = TagKey.create("my.org/keys/frontend");

  // videoSize will measure the size of processed videos.
  private static final MeasureLong VIDEO_SIZE =
      MeasureLong.create("my.org/measure/video_size", "size of processed videos", "By");

  private static final long MiB = 1 << 20;

  // Create view to see the processed video size distribution broken down by frontend.
  // The view has bucket boundaries (0, 16 * MiB, 65536 * MiB) that will group measure
  // values into histogram buckets.
  private static final View.Name VIDEO_SIZE_VIEW_NAME = View.Name.create("my.org/views/video_size");
  private static final View VIDEO_SIZE_VIEW =
      View.create(
          VIDEO_SIZE_VIEW_NAME,
          "processed video size over time",
          VIDEO_SIZE,
          Aggregation.Distribution.create(
              BucketBoundaries.create(Arrays.asList(0.0, 16.0 * MiB, 256.0 * MiB))),
          Collections.singletonList(FRONTEND_KEY),
          Cumulative.create());

  /** Main launcher for the QuickStart example. */
  public static void main(String[] args)
      throws InterruptedException, InvalidElasticsearchConfigException {
    TagContextBuilder tagContextBuilder =
        tagger.currentBuilder().put(FRONTEND_KEY, TagValue.create("mobile-ios9.3.5"));
    SpanBuilder spanBuilder =
        tracer
            .spanBuilder("my.org/ProcessVideo")
            .setRecordEvents(true)
            .setSampler(Samplers.alwaysSample());
    viewManager.registerView(VIDEO_SIZE_VIEW);
//    LoggingTraceExporter.register();
    ZipkinTraceExporter.createAndRegister("http://localhost:9411/api/v2/spans"
        ,MICROSERVICE);
//    ElasticsearchConfiguration elasticsearchConfiguration
//        = new ElasticsearchConfiguration(null, null,ELASTIC_SEARCH_URL,
//        INDEX_FOR_TRACE,TYPE_FOR_TRACE);
//    ElasticsearchTraceExporter.createAndRegister(elasticsearchConfiguration);
//
    // Process video.
    // Record the processed video size.
    try {
      Scope scopedTags = tagContextBuilder.buildScoped();
        Scope scopedSpan = spanBuilder.startScopedSpan();
      tracer.getCurrentSpan().addAnnotation("Start processing video.");
      // Sleep for [0,10] milliseconds to fake work.
      Thread.sleep(new Random().nextInt(10) + 1);
      statsRecorder.newMeasureMap().put(VIDEO_SIZE, 25 * MiB).record();
      tracer.getCurrentSpan().addAnnotation("Finished processing video.");

    } catch (Exception e) {
      tracer.getCurrentSpan().addAnnotation("Exception thrown when processing video.");
      tracer.getCurrentSpan().setStatus(Status.UNKNOWN);
      logger.severe(e.getMessage());
    }

//    logger.info("Wait longer than the reporting duration...");
    // Wait for a duration longer than reporting duration (5s) to ensure spans are exported.
    // TODO(songya): remove the gap once we add a shutdown hook for exporting unflushed spans.
    Thread.sleep(5100);
    ViewData viewData = viewManager.getView(VIDEO_SIZE_VIEW_NAME);
//    logger.info(
//        String.format("Recorded stats for %s:\n %s", VIDEO_SIZE_VIEW_NAME.asString(), viewData));
  }

}
