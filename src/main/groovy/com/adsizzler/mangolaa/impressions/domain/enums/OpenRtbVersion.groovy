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
            switch(code){
                case '2.1' : result = VER_2_1; break
                case '2.2' : result = VER_2_2; break
                case '2.3' : result = VER_2_3; break
                case '2.4' : result = VER_2_4; break
                case '2.5' : result = VER_2_5; break
                default : result = UNKNOWN
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
