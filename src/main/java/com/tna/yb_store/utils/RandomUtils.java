package com.tna.yb_store.utils;

import java.util.Random;

public class RandomUtils {
    private static Random ran = new Random();
    private RandomUtils(){};
    public static String getConversionCode(){
        String conversion_code = "";
        for(int i=0;i<6;i++){
            conversion_code = conversion_code+ran.nextInt(10);
        }
        return conversion_code;
    }
}
