package com.adsizzler.mangolaa.impressions.handler

import com.adsizzler.mangolaa.impressions.constants.HttpHeaderValues
import com.adsizzler.mangolaa.impressions.domain.Impression
import com.adsizzler.mangolaa.impressions.domain.enums.OpenRtbVersion
import com.adsizzler.mangolaa.impressions.exception.MandatoryMacroMissingException
import com.adsizzler.mangolaa.impressions.service.ImpressionService
import com.adsizzler.mangolaa.impressions.service.PixelImageService
import groovy.util.logging.Slf4j
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.RoutingContext
import org.springframework.stereotype.Component

import java.time.ZoneId
import java.time.ZonedDateTime

import static com.adsizzler.mangolaa.impressions.constants.HttpHeaderValues.*
import static io.vertx.core.http.HttpHeaders.*

/**
 * Created by ankushsharma on 14/02/18.
 */
@Component
@Slf4j
class ImpressionAndBillingHandler implements Handler<RoutingContext> {

    private final ImpressionService impressionService
    private final PixelImageService pixelImageService

    ImpressionAndBillingHandler(
            ImpressionService impressionService,
            PixelImageService pixelImageService
    )
    {
        this.impressionService = impressionService
        this.pixelImageService = pixelImageService
    }

    @Override
    void handle(RoutingContext rc) {

        def req = rc.request()
        def resp = rc.response()

        //From path params
        def creativeId = req.getParam('cr_id') as Integer
        def sourceId = req.getParam('src_id') as Integer
        def campaignId = req.getParam('camp_id') as Integer
        def advId = req.getParam('adv_id') as Integer
        def clientId = req.getParam('cl_id') as Integer

        if(anyNull(creativeId, campaignId, sourceId, advId, clientId)){
            throw new MandatoryMacroMissingException('One or more mandatory macros are missing')
        }

        log.debug 'CreativeId {}', creativeId
        log.debug 'Source Id {}', sourceId
        log.debug 'CampaignId {}', campaignId
        log.debug 'Adv Id {}', advId
        log.debug 'Client Id {}', clientId

        def queryParams = req.params()

        //Macros from Query param
        def openRtbVer = OpenRtbVersion.from(queryParams.get('open_rtb'))
        def bidReqId = queryParams.get('bid_req_id')
        def bidRespId = queryParams.get('bid_resp_id')
        def impId = queryParams.get('imp_id')
        def seatId = queryParams.get('seat_id')
        def adId = queryParams.get('ad_id')
        def cur = queryParams.get('cur')
        def price = queryParams.get('price')
        def mbr = queryParams.get('mbr')
        def lossCode = queryParams.get('loss')
        def winUuid = queryParams.get('win_uuid')

        //If the open rtb version is 2.5, return success message with Http status 200
        if(openRtbVer.is2_5()){
            resp.putHeader(CONTENT_TYPE, TEXT_HTML)
                .end('Ok')
        }
        //Else, return 1x1 pixel image
        else{
            resp.putHeader(CONTENT_TYPE, IMAGE_PNG)
                .end(Buffer.buffer(pixelImageService.get1X1Pixel()))
        }
        //Then push the Impression object to Kafka
        def uuid = UUID.randomUUID()
        def timestamp = ZonedDateTime.now(ZoneId.of('UTC'))
        def impression = new Impression (
                uuid : uuid,
                timestamp : timestamp,
                creativeId : creativeId,
                campaignId : campaignId,
                advId : advId,
                clientId : clientId,
                sourceId : sourceId,
                openRtbVer : openRtbVer,
                bidReqId : bidReqId,
                bidRespId : bidRespId,
                impId : impId,
                seatId : seatId,
                adId : adId,
                cur : cur,
                price : price,
                mbr : mbr,
                lossCode : lossCode,
                winUuid : winUuid
        )

        log.debug 'Impression {}', impression
        impressionService.pushToKafka(impression)
    }

    //If any object passed is null
    private static <T> boolean anyNull(T... array){
        def result = false
        if(array){
            for(t in array){
                if(Objects.isNull(t)){
                    result = true
                    break
                }
            }
        }
        else{
            result = true
        }
        result
    }
}
