package com.adsizzler.mangolaa.impressions.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by ankushsharma on 28/03/18.
 */
class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime>{

    @Override
    void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value){
            gen.writeString(
                    DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value)
            )
        }
    }
}
