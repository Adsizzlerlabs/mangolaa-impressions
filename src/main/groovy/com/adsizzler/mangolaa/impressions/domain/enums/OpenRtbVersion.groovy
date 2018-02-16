package com.adsizzler.mangolaa.impressions.domain.enums

import com.adsizzler.mangolaa.impressions.util.Strings

/**
 * Created by ankushsharma on 14/02/18.
 */
enum OpenRtbVersion {

    UNKNOWN('Unknown'),
    VER_2_1('2.1'),
    VER_2_2('2.2'),
    VER_2_3('2.3'),
    VER_2_4('2.4'),
    VER_2_5('2.5')

    final String code

    OpenRtbVersion(String code){
        this.code = code
    }

    static OpenRtbVersion from(String code){
        def result = UNKNOWN
        if(Strings.hasText(code)){
            for(val in values()){
                if(val.code.equalsIgnoreCase(code)){
                    result = val
                    break
                }
            }
        }
        result
    }

    /**
     *
     * @return true if open rtb version is 2.5, false otherwise
     */
    boolean is2_5(){
        this == VER_2_5
    }
}
