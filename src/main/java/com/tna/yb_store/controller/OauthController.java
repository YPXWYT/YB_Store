package com.tna.yb_store.controller;

import cn.yiban.open.Authorize;
import com.tna.yb_store.utils.AppContext;
import com.tna.yb_store.utils.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 授权API
 * /v1/oauth/authorize     授权
 * /v1/oauth/reset_token   重置授权
 */
@Controller
@RequestMapping("/v1/oauth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OauthController {

    private Logger logger = LogManager.getLogger(OauthController.class.getName());

    @Resource
    private final RedisUtil redisUtil;

    public OauthController(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/authorize")
    public String getCode() {
        logger.info("开始授权！");
        Authorize authorize = new Authorize(AppContext.APPID, AppContext.APPSECRET);
        String url = authorize.forwardurl(AppContext.BACKURL, "QUERY", Authorize.DISPLAY_TAG_T.WEB);
        return "redirect:" + url;
    }

    @RequestMapping("/access_token")
    public String getToken1(String code, String state) {
        String url = "http://129.204.4.165/#/?code=";
        if (code == null || code.isEmpty()) {
            logger.error("获取code出错");
            return "error";
        } else {
            Authorize auth = new Authorize(AppContext.APPID, AppContext.APPSECRET);
            String queryTokenRS = auth.querytoken(code, AppContext.BACKURL);
            if (queryTokenRS == null || queryTokenRS.isEmpty()) {
                logger.error("获取querytoken出错");
                return "error";
            } else {
                JSONObject json = JSONObject.fromObject(queryTokenRS);
                if (json == null) {
                    logger.error("获取json转换出错");
                    return "error";
                } else {
                    if (Objects.requireNonNull(json).has("access_token")) {
                        String accessToken = json.getString("access_token");
                        String userid = json.getString("userid");
                        //授权查询
                        JSONObject jsonObject = JSONObject.fromObject(auth.getManInstance(accessToken).query());
                        long expire_in = jsonObject.getLong("expire_in");
                        redisUtil.set(userid, accessToken, expire_in);
                        url = url + userid;
                        logger.info("用户"+userid+"授权成功！");
                    } else {
                        logger.error("获取access_token出错:" + json);
                        return "error";
                    }
                }
            }
        }
        return "redirect:" + url;
    }

}
