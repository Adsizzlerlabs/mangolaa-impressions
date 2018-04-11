package com.adsizzler.mangolaa.impressions.domain

import com.adsizzler.mangolaa.impressions.domain.enums.OpenRtbVersion
import com.adsizzler.mangolaa.impressions.jackson.serializer.OpenRtbVersionSerializer
import com.adsizzler.mangolaa.impressions.jackson.serializer.UUIDSerializer
import com.adsizzler.mangolaa.impressions.jackson.serializer.ZonedDateTimeSerializer
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.ToString

import java.time.ZonedDateTime

/**
 * Created by ankushsharma on 13/02/18.
 */
@ToString(includePackage = false)
class Impression {

    @JsonProperty(value = "uuid", required = true)
    @JsonSerialize(using = UUIDSerializer)
    final UUID uuid

    @JsonProperty(value = "timestamp", required = true)
    @JsonSerialize(using = ZonedDateTimeSerializer)
    final ZonedDateTime timestamp

    @JsonProperty(value = 'openRtbVer', required = true)
    @JsonSerialize(using = OpenRtbVersionSerializer)
    final OpenRtbVersion openRtbVer

    @JsonProperty(value = "campId", required = true)
    final Integer campaignId

    @JsonProperty(value = "creativeId", required = true)
    final Integer creativeId

    @JsonProperty(value = "advId", required = true)
    final Integer advId

    @JsonProperty(value = "clientId", required = true)
    final Integer clientId

    @JsonProperty(value = "srcId", required = true)
    final Integer sourceId

    @JsonProperty(value = "userId", required = true)
    final String userId

    @JsonProperty(value = 'bidReqId')
    final String bidReqId

    @JsonProperty(value = 'winUuid')
    final String winUuid

    @JsonProperty(value = 'bidRespUuid')
    final String bidRespUuid

    @JsonProperty(value = 'impId')
    final String impId

    @JsonProperty(value = 'seatId')
    final String seatId

    @JsonProperty(value = 'adId')
    final String adId

    @JsonProperty(value = 'cur')
    final String cur

    @JsonProperty(value = 'price')
    final Double price

    @JsonProperty(value = 'mbr')
    final Double mbr

    @JsonProperty(value = 'lossCode')
    final Integer lossCode


    Impression(Map fields){
        this.uuid = fields['uuid'] as UUID
        this.timestamp = fields['timestamp'] as ZonedDateTime
        this.openRtbVer = fields['openRtbVer'] as OpenRtbVersion
        this.creativeId = fields['creativeId'] as Integer
        this.campaignId = fields['campaignId'] as Integer
        this.clientId = fields['clientId'] as Integer
        this.sourceId = fields['sourceId'] as Integer
        this.bidReqId = fields['bidReqId'] as String
        this.userId = fields['userId'] as String
        this.bidRespUuid = fields['bidRespUuid'] as String
        this.winUuid = fields['winUuid'] as String
        this.impId = fields['impId'] as String
        this.seatId = fields['seatId'] as String
        this.adId = fields['adId'] as String
        this.cur = fields['cur'] as String
        this.price = fields['price'] as Double
        this.mbr = fields['mbr'] as Double
        this.lossCode = fields['lossCode'] as Integer
        this.advId = fields['advId'] as Integer
    }

}
