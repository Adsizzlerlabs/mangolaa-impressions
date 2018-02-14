package com.adsizzler.mangolaa.impressions.service

import com.adsizzler.mangolaa.impressions.domain.Impression
import io.vertx.core.Future

/**
 * Created by ankushsharma on 14/02/18.
 */
interface ImpressionService {

    Future<Boolean> pushToKafka(Impression impression)

}
