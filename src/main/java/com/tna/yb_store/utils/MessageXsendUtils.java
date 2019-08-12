package com.tna.yb_store.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class MessageXsendUtils {
    /**
     * 时间戳接口配置
     */
    private static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";

    private static Logger logger = LogManager.getLogger(MessageXsendUtils.class);
    /**
     * API 请求接口配置
     */
    private static final String URL = "https://api.mysubmail.com/message/xsend";

    public static boolean send(String appid, String appkey, String to, String project, JSONObject vars) {
        HttpPost httpPost = new HttpPost(URL);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("appid", appid);
        jsonParam.put("signature", appkey);
        jsonParam.put("project", project);
        jsonParam.put("to", to);
        jsonParam.put("vars", vars);
        StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResponse resp;

        try {
            resp = HttpClientBuilder.create().build().execute(httpPost);
            HttpEntity he = resp.getEntity();
            logger.info(EntityUtils.toString(he, "UTF-8"));
            return resp.getStatusLine().getStatusCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 获取时间戳
     *
     * @return
     */
    private static String getTimestamp() {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(TIMESTAMP);
        String timestamp = null;
        try {
            HttpResponse response = closeableHttpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
            if (jsonStr != null) {
                JSONObject json = JSONObject.fromObject(jsonStr);
                timestamp = json.getString("timestamp");
            }
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
