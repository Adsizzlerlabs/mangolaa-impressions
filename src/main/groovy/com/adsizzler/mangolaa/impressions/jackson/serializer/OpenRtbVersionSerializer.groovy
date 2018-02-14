package com.adsizzler.mangolaa.impressions.jackson.serializer

import com.adsizzler.mangolaa.impressions.domain.enums.OpenRtbVersion
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

/**
 * Created by ankushsharma on 14/02/18.
 */
class OpenRtbVersionSerializer extends JsonSerializer<OpenRtbVersion>{

    @Override
    void serialize(OpenRtbVersion value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value){
            gen.writeString(value.code)
        }
        else{
            gen.writeString(OpenRtbVersion.UNKNOWN.name())
        }
    }
}
