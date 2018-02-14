package com.adsizzler.mangolaa.impressions.service.impl

import com.adsizzler.mangolaa.impressions.constants.KafkaTopics
import com.adsizzler.mangolaa.impressions.domain.Impression
import com.adsizzler.mangolaa.impressions.service.ImpressionService
import com.adsizzler.mangolaa.impressions.util.Assert
import com.adsizzler.mangolaa.impressions.util.Gzip
import groovy.util.logging.Slf4j
import io.vertx.core.Future
import io.vertx.core.json.Json
import io.vertx.kafka.client.producer.KafkaProducer
import io.vertx.kafka.client.producer.impl.KafkaProducerRecordImpl
import org.springframework.stereotype.Service

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * Created by ankushsharma on 14/02/18.
 */
@Service
@Slf4j
class ImpressionServiceImpl implements ImpressionService {

    private static final ExecutorService serializationExecutor

    private final KafkaProducer<String,byte[]> kafkaProducer

    ImpressionServiceImpl(KafkaProducer<String,byte[]> kafkaProducer){
        this.kafkaProducer = kafkaProducer
    }

    static{
        final int cores = Runtime.getRuntime().availableProcessors()
        final int threads = cores * 2
        log.info 'Initializing {} thread pool with {} threads', this.name, threads
        serializationExecutor = Executors.newFixedThreadPool(threads)
    }

    @Override
    Future<Boolean> pushToKafka(Impression impression) {
        Assert.notNull(impression, "impression cannot be null")

        def future = Future.future()

        //Keeping serialization out of EventLoop's path
        serializationExecutor.execute {
            def payload = Gzip.compress(Json.encode(impression))
            def write = new KafkaProducerRecordImpl<String,byte[]>(KafkaTopics.IMPRESSIONS, payload)
            kafkaProducer.write(write, { done ->
                def pushed = false
                if(done.succeeded()) {
                    pushed = true
                }
                future.complete(pushed)
            })
           .exceptionHandler{ Throwable ex ->
                // Don't use future.fail(ex). Rather, look into Kafka retry policies.
                log.error '', ex
            }
        }

        future
    }
}
