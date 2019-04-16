package com.tna.yb_store.controller;

import cn.yiban.open.Authorize;
import cn.yiban.open.common.User;
import com.tna.yb_store.utils.AppContext;
import com.tna.yb_store.utils.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/v1")
public class YbApiController {

    @Resource
    private RedisUtil redisUtil;
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping("/authorize")
    public void getCode(HttpServletRequest request, HttpServletResponse response)throws Exception{
        Authorize authorize = new Authorize(AppContext.APPID, AppContext.APPSECRET);
        String url = authorize.forwardurl(AppContext.BACKURL, "QUERY", Authorize.DISPLAY_TAG_T.WEB);
        //request.getRequestDispatcher(url).forward(request,response);//此方法无法访问
        response.sendRedirect(url);
    }

    @RequestMapping("/access_token")
    @Cacheable("productCache")
    public String getToken(HttpServletRequest request, HttpServletResponse response)throws Exception{

        String code = request.getParameter("code");
        if (code == null || code.isEmpty())
        {
            response.sendRedirect("index.html");
        }

        Authorize auth = new Authorize(AppContext.APPID,AppContext.APPSECRET);
        String queryTokenRS = auth.querytoken(code,AppContext.BACKURL);


        if (queryTokenRS == null || queryTokenRS.isEmpty())
        {
            response.sendRedirect("index.html");
        }
        JSONObject json = JSONObject.fromObject(queryTokenRS);

        if (json == null)
        {
            response.sendRedirect("index.html");
        }

        if (Objects.requireNonNull(json).has("access_token"))
        {


            String accessToken = json.getString("access_token");
            redisUtil.set("access_token",accessToken);

            System.out.println(accessToken);

//            授权查询
            String text1 = auth.getManInstance(accessToken).query();
            System.out.println(text1);

            User user = new User(accessToken);
            JSONObject userInfo = JSONObject.fromObject(user.me()).getJSONObject("info");
            JSONObject otherUserInfo = JSONObject.fromObject(user.other(2));

//            Friend friend = new Friend(accessToken);
//            JSONObject me_list = JSONObject.fromObject(friend.list(1,15));
//
//            System.out.println(otherUserInfo);
//            System.out.println(userInfo);
//            System.out.println(me_list);

        }
        else
        {
            System.out.println("failed");
        }
        return "ok";
    }
}
