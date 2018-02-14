package com.adsizzler.mangolaa.impressions.verticle

import com.adsizzler.mangolaa.impressions.handler.GlobalFailureHandler
import com.adsizzler.mangolaa.impressions.handler.ImpressionAndBillingHandler
import com.adsizzler.mangolaa.impressions.handler.MetricsHandler
import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE

/**
 * Created by ankushsharma on 14/02/18.
 */
@Component
@Slf4j
//So that more than one verticle could be deployed
@Scope(SCOPE_PROTOTYPE)
class ImpressionAndBillingVerticle extends AbstractVerticle {

    private final Router router
    private final ImpressionAndBillingHandler handler
    private final GlobalFailureHandler globalFailureHandler
    private final MetricsHandler metricsHandler

    ImpressionAndBillingVerticle(
            Router router,
            ImpressionAndBillingHandler handler,
            GlobalFailureHandler globalFailureHandler,
            MetricsHandler metricsHandler
    )
    {
        this.router = router
        this.handler = handler
        this.globalFailureHandler = globalFailureHandler
        this.metricsHandler = metricsHandler
    }

    @Override
    void start(){

        router.get('/')
              .handler(handler)
              .failureHandler(globalFailureHandler)

        router.get('/metrics')
              .handler(metricsHandler)
              .failureHandler(globalFailureHandler)

    }


}
