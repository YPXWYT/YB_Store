package com.tna.yb_store.utils;

import com.tna.yb_store.entity.YbUser;

public class AppContext {
    /**
     * App的id或者叫做appkey
     */
    public static final String APPID = "053c484cc61d5608";

    /**
     * App的AppSecret
     */
    public static final String APPSECRET = "3901b691971fcc502524c9bb83500efd";

    /**
     * App的回调地址
     */
    public static final String BACKURL = "http://101.132.69.69:8088/v1/oauth/access_token";

    /**
     * App的访问地址
     */
    public static final String WEBSITEURL = "http://10.102.165.187:8080/v1/oauth/access_token";

    public static final String PAY_API = "https://openapi.yiban.cn/pay/trade_wx";

    public static final String OTHER_API = "https://openapi.yiban.cn/user/other";

    public static String ACCESS_TOKEN = "access_token";

    public static String CONVERSION_CODE = "conversion_code";

    public static String APPID_SML = "38416";

    public static String APPKEY_SML = "d5a19e577dc0b89a3047668516f7eab5";

    public static String PROJECT_SML = "jTnr13";

}
