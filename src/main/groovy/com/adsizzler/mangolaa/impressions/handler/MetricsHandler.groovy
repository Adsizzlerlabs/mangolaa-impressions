package com.adsizzler.mangolaa.impressions.handler

import com.adsizzler.mangolaa.impressions.util.Json
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.ext.dropwizard.MetricsService
import io.vertx.ext.web.RoutingContext
import org.springframework.stereotype.Component

import static com.adsizzler.mangolaa.impressions.constants.HttpHeaderValues.*
import static io.vertx.core.http.HttpHeaders.CONNECTION
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE

/**
 * Created by ankushsharma on 04/11/17.
 */
@Component
class MetricsHandler implements Handler<RoutingContext> {

    final MetricsService metricsService
    final Vertx vertx

    MetricsHandler(
            final Vertx vertx,
            final  MetricsService metricsService)
    {
        this.metricsService = metricsService
        this.vertx = vertx
    }

    @Override
     void handle(final RoutingContext rc) {
        def response = rc.response()
        def jsonMetrics = metricsService.getMetricsSnapshot(vertx)

        def json = Json.encodePretty(jsonMetrics)
        response.putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .putHeader(CONNECTION, KEEP_ALIVE)
                .end(json)
    }
}
